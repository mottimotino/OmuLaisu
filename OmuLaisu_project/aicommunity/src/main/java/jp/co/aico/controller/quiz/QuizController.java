package jp.co.aico.controller.quiz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jp.co.aico.entity.CategoriesEntity;
import jp.co.aico.entity.QuizEntity;
import jp.co.aico.repository.QuizRepository;

@Controller
public class QuizController {

	@Autowired
	QuizRepository quizRepository;

	/**
	 * クイズのカテゴリーを選択する画面
	 * @return quiz/list.html
	 */
	@RequestMapping(path = "/quiz_list", method = RequestMethod.GET)
	public String quiz_list() {
		return "quiz/list";
	}

	/**
	 * 選択されたカテゴリーの問題を抽出する
	 * @param type categoriesテーブルのcategory_id(主キー)
	 * @param session 選択されたカテゴリーの問題一覧
	 * @return redirect:/quiz/select/0
	 */
	@RequestMapping(path = "/quiz/question/{type}", method = RequestMethod.GET)
	public String quiz_select(@PathVariable Integer type, HttpSession session) {
		//オブジェクト生成
		CategoriesEntity categoriesEntity = new CategoriesEntity();
		//受け取ったカテゴリー番号で問題を検索
		categoriesEntity.setCategoryId(type);
		//検索したカテゴリーの問題を変数に入れる
		List<QuizEntity> quizEntity = quizRepository.findByCategoriesEntity(categoriesEntity);
		//セッションスコープに問題を保存
		session.setAttribute("questions", quizEntity);
		return "redirect:/quiz/select/0";
	}

	

	/**
	 * クイズ解説を表示する
	 * @param queId questionテーブルのque_id(主キー)
	 * @param model 問題(解答、解説等)
	 * @return quiz_explain.html
	 */
	@RequestMapping(path = "/quiz/explain/{queId}", method = RequestMethod.GET)
	public String quiz_explain(@PathVariable Integer queId, Model model) {
		//quiz_questionと同じ処理、htmlで解説を表示する
		model.addAttribute("question", quizRepository.getReferenceById(queId));
		return "quiz/explain";
	}

	/**
	 * 問題を表示する
	 * @param queId questionテーブルのque_id(主キー)
	 * @param model 問題(問題文、選択肢等)
	 * @param type カテゴリー番号を受け取る(formに変更予定)
	 * @return quiz/question.html
	 */
	@RequestMapping(path = "/quiz_select/{queId}")
	public String quiz_select(@PathVariable Integer queId, Model model,Integer type) {
		//オブジェクト生成
		CategoriesEntity categoriesEntity = new CategoriesEntity();
		QuizEntity quizEntity = new QuizEntity();
		//問題が入るまで処理を続ける
		//問題をquizEntityに入れる
		while(quizEntity == null) {
			//次の問題
			queId += 1;
			//次以降の問題が存在しないとき別の画面に遷移する
			if(quizRepository.getReferenceById(queId) == null) {
				return "redirect:/quiz_list";
			}
			//受け取ったカテゴリー番号で問題を検索
			categoriesEntity.setCategoryId(type);
			//IDとカテゴリーが一致する問題が存在していた時、問題を変数に入れる
			quizEntity = quizRepository.findByIdAndCategoriesEntity(queId, categoriesEntity);
		}
		//問題を保存
		model.addAttribute("question",quizEntity);
		//問題番号を保存
		model.addAttribute("queId",queId);
		return "quiz/question";
	}
	
	//今後の作成物
	//・正解率表示機能 ・form作成

}
