package jp.co.aico.controller.management;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jp.co.aico.entity.CategoriesEntity;
import jp.co.aico.entity.UsersEntity;
import jp.co.aico.form.Managementform;
import jp.co.aico.repository.AccuracyRepository;
import jp.co.aico.repository.CategoriesRepository;
import jp.co.aico.repository.ManagementRepository;
import jp.co.aico.repository.ProgressRepository;
import jp.co.aico.repository.ReservationDateRepository;
import jp.co.aico.repository.UsersRepository;

@Controller
public class ManagementController {
	@Autowired
	ManagementRepository repository;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	AccuracyRepository accuracyRepository;

	@Autowired
	ProgressRepository progressRepository;

	@Autowired
	CategoriesRepository categoriesRepository;
	
	@Autowired
	ReservationDateRepository rdRepository;

	
	@RequestMapping("/user/update/input/{select}")
	public String kousin(@PathVariable String select,HttpSession session) {
		session.setAttribute("select", select);
		return "user/update/input";
	}

	/**
	 * メールアドレス/パスワード変更
	 * 確認form
	 * @param form
	 * @param model
	 * @return check
	 */
	@RequestMapping(path="/user/update/check",method=RequestMethod.POST)
	public String check(Managementform form, Model model,HttpSession session) {
		String select = (String)session.getAttribute("select");
		if(select.equals("mail")) {
			UsersEntity usersEntity = usersRepository.findByMail(form.getInput());
			if(usersEntity != null) {
				model.addAttribute("select",select);
				model.addAttribute("msg","※入力したメールアドレスは使われています(The email address you entered is in use)");
				return "user/update/input";
			}
			
		}
		
		model.addAttribute("save", form.getInput());
		return "user/update/check";

	}

	/**
	 * 更新完了処理
	 * パスワード変更
	 * @param manaId
	 * @param Managementform
	 * @return complete
	 */
	@RequestMapping("user/update/complete/{usersId}/{select}")
	public String update(@PathVariable Integer usersId,@PathVariable String select ,Managementform form) {
		UsersEntity Entity = usersRepository.getReferenceById(usersId);
		//BeanUtils.copyProperties(form, Entity, "usersId");
		if(select.equals("pass")) {
			Entity.setPassword(form.getInput());
		} else if(select.equals("mail")) {
			Entity.setMail(form.getInput());
		}
		Entity = usersRepository.save(Entity);
		return "user/update/complete";
	}

	/**
	 * マイページ
	 * @author 水野
	 * @param userId ユーザーのID
	 * @param model ユーザー情報
	 * @return user/info.html
	 */
	@RequestMapping(path = "user/info/{userId}", method = RequestMethod.GET)
	public String user_info(@PathVariable Integer userId, Model model,HttpSession session) {
		//ユーザーの情報を検索
		UsersEntity usersEntity = usersRepository.getReferenceById(userId);
		//検索した内容を保存
		model.addAttribute("user", usersEntity);
		
		String[] categories = new String[2];
		//漢字問題の検索に利用
		categories[0] = "級";
		//ビジネスマナー問題の検索に利用
		categories[1] = "編";
		//漢字とビジネスマナーの正答率をmodelに格納する
		for(int i = 0; i < categories.length; i++) {
			//カテゴリーを漢字/ビジネスマナーに絞る
			List<CategoriesEntity> categoriesEntity = categoriesRepository.findByCategoryNameContaining(categories[i]);
			//検索したカテゴリーの中でcategory_idが最小値のものを変数に保存
			CategoriesEntity category_min = categoriesEntity.get(0);
			//検索したカテゴリーの中でcategory_idが最大値のものを変数に保存
			CategoriesEntity category_max = categoriesEntity.get(categoriesEntity.size()-1);
			//問題数を数える
			int total = progressRepository.findByUsersEntityAndCategoriesEntity2(usersEntity, category_min, category_max);
			//正解数を数える
			int collect = progressRepository.findByUsersEntityAndMissFlagAndCategoriesEntity2(usersEntity, 0, category_min, category_max);
			//正答率をmodelに格納
			double collectAnswerRate = 0;
			double wrongAnswerRate = 0;
			if (total != 0) {
				//正答率を計算
				collectAnswerRate = (double)collect / total *100;
				collectAnswerRate = (double)Math.round(collectAnswerRate*10)/10;
				wrongAnswerRate = 100 - collectAnswerRate;
				wrongAnswerRate = (double)Math.round(wrongAnswerRate*10)/10;
			//問題を解いていない場合、正答率は0%とする
			} else {
				collectAnswerRate = 0;
				wrongAnswerRate = 100;
			}
			String category = "";
			//modelに格納する名前を変更
			if(i == 0) {
				category = "kanji";
			} else {
				category = "business";
			}
			//modelに正答率を格納
			//Correctが正解,Incorrectが不正解の割合
			//modelに格納する名前 : "kanjiCorrect","kanjiIncorrect","businessCorrect","businessIncorrect"
			model.addAttribute(category+"Correct",collectAnswerRate);
			model.addAttribute(category+"Incorrect",wrongAnswerRate);
			
		}		
		UsersEntity users=new UsersEntity();
		users.setUsersId((int)session.getAttribute("usersId"));
		model.addAttribute("zoomLinkSave",rdRepository.findByUsersEntity(users));
		return "user/info";
	}
}
