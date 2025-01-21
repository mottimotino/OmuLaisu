package jp.co.aico.controller.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.aico.entity.AccuracyEntity;
import jp.co.aico.entity.CategoriesEntity;
import jp.co.aico.entity.ProgressEntity;
import jp.co.aico.entity.QuizEntity;
import jp.co.aico.entity.UsersEntity;
import jp.co.aico.form.QuizForm;
import jp.co.aico.form.UsersForm;
import jp.co.aico.repository.AccuracyRepository;
import jp.co.aico.repository.CategoriesRepository;
import jp.co.aico.repository.ProgressRepository;
import jp.co.aico.repository.QuizRepository;

@Controller
public class QuizController {

	@Autowired
	QuizRepository quizRepository;
	@Autowired
	ProgressRepository progressRepository;
	@Autowired
	AccuracyRepository accuracyRepository;
	@Autowired
	CategoriesRepository categoriesRepository;

	/**
	 * クイズのカテゴリーを選択する画面
	 * @return quiz/list.html
	 */
	@RequestMapping(path = "/quiz/list", method = RequestMethod.GET)
	public String quiz_list() {
		return "quiz/list";
	}

	/**
	 * クイズ解説を表示する
	 * @param queId questionテーブルのque_id(主キー)
	 * @param model 問題(解答、解説等)
	 * @return quiz_explain.html
	 */
	@RequestMapping(path = "/quiz/explain", method = RequestMethod.POST)
	public String quiz_explain(Model model, QuizForm quizForm, UsersForm usersForm) {

		//現在の問題のカテゴリーを取得
		QuizEntity quizEntity = quizRepository.getReferenceById(quizForm.getQueId());
		int categoryId = quizEntity.getCategoriesEntity().getCategoryId();
		CategoriesEntity categoriesEntity = new CategoriesEntity();
		categoriesEntity.setCategoryId(categoryId);

		//正答率を保存するEntity
		ProgressEntity progressEntity = new ProgressEntity();

		progressEntity.setCategoriesEntity(categoriesEntity);

		/* 正解判定 */
		int queId = quizForm.getQueId();
		//正解の番号をanswerに入れる
		int answer = quizRepository.getReferenceById(queId).getAnswer();

		//ユーザーIDを保存
		if(usersForm.getUsersId() != null) {
			UsersEntity usersEntity = new UsersEntity();
			usersEntity.setUsersId(usersForm.getUsersId());
			progressEntity.setUsersEntity(usersEntity);
			//問題IDを保存
	//		quizEntity = new QuizEntity();
	//		quizEntity.setQueId(quizForm.getQueId());
			progressEntity.setQuizEntity(quizEntity);
			
			//正解or不正解を保存(書き換え予定)
			if (quizForm.getAnswer() == answer) {
				//間違いフラグに正解(0)を入れる
				progressEntity.setMissFlag(0);
			} else {
				//間違いフラグに不正解(1)を入れる
				progressEntity.setMissFlag(1);
			}
			//問題の情報をテーブルに保存
			progressRepository.save(progressEntity);
	
			/** 正解率を更新 */
			//カテゴリーとユーザーで正答率を取得
			AccuracyEntity accuracyEntity = accuracyRepository.findByCategoriesEntityAndUsersEntity(categoriesEntity,
					usersEntity);
			//正答率が存在しない(初回の)場合ユーザーIDとカテゴリーで登録する
			if (accuracyEntity == null) {
				accuracyEntity = new AccuracyEntity();
				accuracyEntity.setCategoriesEntity(categoriesEntity);
				accuracyEntity.setUsersEntity(usersEntity);
			}
			//正答率をエンティティに保存
			//ユーザーの問題を解いた数を数える
			int total = progressRepository.findByUsersEntityAndCategoriesEntity(usersEntity, categoriesEntity);
			//正解数を数える
			int collect = progressRepository
					.findByUsersEntityAndMissFlagAndCategoriesEntity(usersEntity, 0, categoriesEntity);
			//正答率をスコープに格納,正答率テーブルに保存
			double collectAnswerRate = 0;
			if (total != 0) {
				collectAnswerRate = (double) collect / total * 100;
			}
			accuracyEntity.setProgress(collectAnswerRate);
			accuracyRepository.save(accuracyEntity);
		}
		//quiz/questionと同じ処理、htmlで解説を表示する
		
		model.addAttribute("question", quizEntity);
		//正答率に応じて解答を表示する
		switch(answer) {
		case 1:
			model.addAttribute("answer",quizEntity.getChoise1());
			break;
		case 2:
			model.addAttribute("answer",quizEntity.getChoise2());
			break;
		case 3:
			model.addAttribute("answer",quizEntity.getChoise3());
			break;
		case 4:
			model.addAttribute("answer",quizEntity.getChoise4());
			break;
		}
		
		return "quiz/explain";
	}

	/**
	 * 問題を表示する
	 * @param model 正答率
	 * @param quizForm 問題ID(ジャンル選択時は0)
	 * @param usersForm ユーザーID
	 * @return quiz/question.html
	 */
	@RequestMapping(path = "/quiz/question", method = RequestMethod.POST)
	public String quiz_select(Model model, QuizForm quizForm, UsersForm usersForm) {
		//オブジェクト生成
		CategoriesEntity categoriesEntity = new CategoriesEntity();
		//categoryName(htmlのnameの値)をセットする
		String categoryName = quizForm.getCategoryName();
		String categoryType = quizForm.getCategoryType();
		//問題のジャンルを連結(例:「初級」+「読み」)
		//条件:カテゴリー名が存在しない(「初級」など登録されているデータに「読み/書き」とついているもの)
		if (categoriesRepository.findByCategoryName(categoryName) == null) {
			categoryName += categoryType;
		}

		//入力されたカテゴリーで検索
		if (categoryName != null) {
			categoriesEntity = categoriesRepository.findByCategoryName(categoryName);
			//正しくカテゴリーが選択されていない場合の処理
		} else {
			return "redirect:/quiz/list";
		}

		//ログイン中の処理
		if (usersForm.getUsersId() != null) {
			UsersEntity usersEntity = new UsersEntity();
			//ユーザーIDを登録
			usersEntity.setUsersId(usersForm.getUsersId());

			/** 正答率を表示する処理 */
			//ユーザーの問題を解いた数を数える
			int total = progressRepository.findByUsersEntityAndCategoriesEntity(usersEntity, categoriesEntity);
			model.addAttribute("total", total);
			//正解数を数える
			int collect = progressRepository
					.findByUsersEntityAndMissFlagAndCategoriesEntity(usersEntity, 0, categoriesEntity);
			model.addAttribute("collect", collect);
			//現在の正答率情報(カテゴリー検索)を変数に入れる
			AccuracyEntity accuracyEntity = accuracyRepository.findByCategoriesEntityAndUsersEntity(categoriesEntity,
					usersEntity);
			if (accuracyEntity == null) {
				model.addAttribute("collectAnswerRate", 0);
			} else {
				model.addAttribute("collectAnswerRate", accuracyEntity.getProgress());
			}
		}
		//オブジェクト生成 
		QuizEntity quizEntity = new QuizEntity();

		//IDを取得
		int queId = quizForm.getQueId();
		//問題数をsizeに入れる
		int size = quizRepository.findAllCount();
		//問題が入るまで処理を続ける
		//問題をquizEntityに入れる
		while (queId < size) {
			//次の問題
			queId += 1;
			//IDとカテゴリーが一致する問題を変数に入れる
			quizEntity = quizRepository.findByQueIdAndCategoriesEntity(queId, categoriesEntity);
			//問題があるとき問題をスコープに格納
			if (quizEntity != null) {
				model.addAttribute("question", quizEntity);
				return "quiz/question";
			}
		}
		//問題がないときカテゴリー選択画面に遷移する
		return "redirect:/quiz/list";

	}

}
