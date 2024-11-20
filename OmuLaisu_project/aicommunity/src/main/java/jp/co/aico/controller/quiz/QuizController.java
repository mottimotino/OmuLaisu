package jp.co.aico.controller.quiz;

import java.util.List;

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

	/**
	 * クイズのカテゴリーを選択する画面
	 * @return quiz/list.html
	 */
	@RequestMapping(path = "/quiz/list", method = RequestMethod.POST)
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
	public String quiz_explain(Model model, QuizForm quizForm,UsersForm usersForm) {
		//解いた問題数を1増やす
		
		//正解判定
		int queId = quizForm.getQueId();
		//正解の番号をanswerに入れる
		int answer = quizRepository.getReferenceById(queId).getAnswer();
		
		//正答率を保存するEntity
		ProgressEntity progressEntity = new ProgressEntity();
		//ユーザーIDを保存
		UsersEntity usersEntity = new UsersEntity();
		usersEntity.setUsersId(usersForm.getUsersId());
		progressEntity.setUsersEntity(usersEntity);
		//問題IDを保存
		QuizEntity quizEntity = new QuizEntity();
		quizEntity.setQueId(quizForm.getQueId());
		progressEntity.setQuizEntity(quizEntity);
		//カテゴリーIDを保存
		CategoriesEntity categoriesEntity = new CategoriesEntity();
		categoriesEntity.setCategoryId(quizForm.getCategoryType());
		
		
		//選んだ選択肢の番号を取得する処理を考える
		//正解or不正解を保存(書き換え予定)
		if (quizForm.getAnswer() == answer) {
			model.addAttribute("judge", "正解");
			//間違いフラグに正解(0)を入れる
			progressEntity.setMissFlag(0);
		} else {
			model.addAttribute("judge", "不正解");
			//間違いフラグに不正解(1)を入れる
			progressEntity.setMissFlag(1);
		}
		//問題の情報をテーブルに保存
		progressRepository.save(progressEntity);

		//quiz/questionと同じ処理、htmlで解説を表示する
		model.addAttribute("question", quizRepository.getReferenceById(queId));
		return "quiz/explain";
	}

	/**
	 * 問題を表示する
	 * @param model 正答率
	 * @param quizForm 問題ID(ジャンル選択時は0)
	 * @param usersForm ユーザーID
	 * @return quiz/question.html
	 */
	@RequestMapping(path = "/quiz/question",method=RequestMethod.POST)
	public String quiz_select(Model model, QuizForm quizForm,UsersForm usersForm) {
		//オブジェクト生成
		CategoriesEntity categoriesEntity = new CategoriesEntity();
		//categoryName(htmlのnameの値)をセットする
		categoriesEntity.setCategoryName(quizForm.getCategoryName());
		//オブジェクト生成 
		QuizEntity quizEntity = new QuizEntity();
		UsersEntity usersEntity = new UsersEntity();
		//ユーザーIDを登録
		usersEntity.setUsersId(usersForm.getUsersId());
		AccuracyEntity accuracyEntity = accuracyRepository.findByCategoriesEntity(categoriesEntity);

		
		/** 正答率を表示する処理 */
		//ユーザーの問題を解いた数を数える
		int total = progressRepository.findByUsersEntityAndCategoriesEntity(usersEntity,categoriesEntity).size();
		model.addAttribute("total",total);
		//正解数を数える
		int collect = progressRepository.findByUsersEntityAndMissFlagAndCategoriesEntity(usersEntity, 0,categoriesEntity).size();
		model.addAttribute("collect",collect);
		//正答率をスコープに格納,正答率テーブルに保存
		int collectAnswerRate = collect/total;
		model.addAttribute("collectAnswerRate",collectAnswerRate);
		//accuracyテーブルの正解率を更新
		accuracyEntity.setProgress(collectAnswerRate);
		accuracyRepository.save(accuracyEntity);
		
		//IDを取得
		int queId = quizForm.getQueId();
		//問題数をsizeに入れる
		List<QuizEntity> findAll = quizRepository.findAll();
		int size = findAll.size();
		//問題が入るまで処理を続ける
		//問題をquizEntityに入れる
		while (queId < size) {
			//次の問題
			queId += 1;
			//IDとカテゴリーが一致する問題を変数に入れる
			quizEntity = quizRepository.findByQueIdAndCategoriesEntity(queId, categoriesEntity);
			//問題があるとき問題をスコープに格納
			if (quizRepository.getReferenceById(queId) != null) {
				model.addAttribute("question", quizEntity);
				return "quiz/question";
			}
		}
		//問題がないときカテゴリー選択画面に遷移する
		return "redirect:/quiz_list";

	}

}
