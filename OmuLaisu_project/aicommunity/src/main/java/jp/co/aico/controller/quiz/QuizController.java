package jp.co.aico.controller.quiz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
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
	public String quiz_list(HttpSession session) {
		//解きなおし判定
		session.setAttribute("review", 0);
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

		//現在の問題のIDをセット
		QuizEntity quizEntity = quizRepository.getReferenceById(quizForm.getQueId());

		/* 正解判定 */
		int queId = quizForm.getQueId();
		//正解の番号をanswerに入れる
		int answer = quizRepository.getReferenceById(queId).getAnswer();

		//ユーザーIDを保存
		if (usersForm.getUsersId() != null) {
			UsersEntity usersEntity = new UsersEntity();
			usersEntity.setUsersId(usersForm.getUsersId());
			//クイズの進捗を保存するEntity
			ProgressEntity progressEntity = new ProgressEntity();
			//カテゴリーを検索
			int categoryId = quizEntity.getCategoriesEntity().getCategoryId();
			CategoriesEntity categoriesEntity = new CategoriesEntity();
			categoriesEntity.setCategoryId(categoryId);
			//カテゴリーをセット
			progressEntity.setCategoriesEntity(categoriesEntity);
			progressEntity.setUsersEntity(usersEntity);
			//問題IDを保存
			progressEntity.setQuizEntity(quizEntity);

			//正解or不正解を保存
			if (quizForm.getAnswer() == answer) {
				//間違いフラグに正解(0)を入れる
				progressEntity.setMissFlag(0);
			} else {
				//間違いフラグに不正解(1)を入れる
				progressEntity.setMissFlag(1);
			}
			//問題の情報をテーブルに保存
			progressRepository.save(progressEntity);


		}
		//quiz/questionと同じ処理、htmlで解説を表示する

		model.addAttribute("question", quizEntity);
		//正答に応じて解答を表示する
		switch (answer) {
		case 1:
			model.addAttribute("answer", quizEntity.getChoice1());
			break;
		case 2:
			model.addAttribute("answer", quizEntity.getChoice2());
			break;
		case 3:
			model.addAttribute("answer", quizEntity.getChoice3());
			break;
		case 4:
			model.addAttribute("answer", quizEntity.getChoice4());
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
			Double collectAnswerRate = (double) collect / total * 100;
			collectAnswerRate = (double) Math.round(collectAnswerRate * 10) / 10;
			if (total == 0) {
				model.addAttribute("collectAnswerRate", 0);
			} else {
				model.addAttribute("collectAnswerRate", collectAnswerRate);
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
				//出題画面に遷移
				return "quiz/question";
			}
		}
		//問題がないときカテゴリー選択画面に遷移する
		return "redirect:/quiz/list";

	}

	@RequestMapping(path = "/quiz/review", method = RequestMethod.POST)
	public String quiz_review(Model model, QuizForm quizForm, HttpSession session) {
		//解きなおし判定
		session.setAttribute("review", 1);

		//オブジェクト生成
		CategoriesEntity categoriesEntity = new CategoriesEntity();
		//categoryName(漢字[級]またはビジネスマナー
		categoriesEntity.setCategoryName(quizForm.getCategoryName());
		//ユーザー毎の進捗を検索する為のエンティティ
		UsersEntity usersEntity = new UsersEntity();
		//セッションに保存されているユーザーIDをセット
		Integer usersId = (Integer) session.getAttribute("usersId");
		usersEntity.setUsersId(usersId);

		//オブジェクト生成 
		QuizEntity quizEntity = new QuizEntity();
		//IDを取得
		int queId = quizForm.getQueId();
		//問題数をsizeに入れる
		int size = quizRepository.findAllCount();
		//問題が入る/問題がなくなるまで処理を続ける
		//問題をquizEntityに入れる
		while (queId < size) {
			//次の問題
			queId += 1;
			quizEntity.setQueId(queId);
			List<ProgressEntity> progressList = progressRepository
					.findByQuizEntityAndUsersEntityAndCategoriesEntityContaining(quizEntity, usersEntity,
							quizForm.getCategoryName());

			//不正解の割合が多い問題を出す
			int collect = 0;
			int inCollect = 0;
			//正解、不正解の数を数える
			for (int i = 0; i < progressList.size(); i++) {
				//正解フラグを取得
				int missFlag = progressList.get(i).getMissFlag();
				if (missFlag == 0) {
					collect += 1;
				} else if (missFlag == 1) {
					inCollect += 1;
				}
			}
			//不正解が多い、かつ問題があるとき問題をスコープに格納
			if (collect <= inCollect && progressList.size() != 0) {
				model.addAttribute("question", quizRepository.getReferenceById(queId));
				//出題画面に遷移
				return "quiz/question";
			}

		}
		//問題がないとき会員詳細表示画面(マイページ)に遷移する
		return "redirect:/user/info/" + usersId;

	}

}
