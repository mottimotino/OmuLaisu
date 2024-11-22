package jp.co.aico.controller.management;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.aico.bean.ManagementBean;
import jp.co.aico.entity.CategoriesEntity;
import jp.co.aico.entity.ManagementEntity;
import jp.co.aico.entity.UsersEntity;
import jp.co.aico.form.Managementform;
import jp.co.aico.repository.AccuracyRepository;
import jp.co.aico.repository.ManagementRepository;
import jp.co.aico.repository.ProgressRepository;
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
	
	/**
	 * パスワード変更
	 * 入力フォーム
	 * @param manaId
	 * @param model
	 * @return input
	 * 
	 */
	@RequestMapping("user/update/input/{manaId}")
	public String kousin(@PathVariable Integer manaId, Model model) {
		ManagementEntity Entity = repository.getReferenceById(manaId);
		ManagementBean bean = new ManagementBean();
		BeanUtils.copyProperties(Entity, bean);
		model.addAttribute("oldPassword", bean);
		return "user/update/input";

	}
	/**
	 * パスワード変更
	 * 確認form
	 * @param form
	 * @param model
	 * @return check
	 */
	@RequestMapping("user/update/check")
	public String check(Managementform form,Model model) {
		model.addAttribute("passwordSave",form);
		return "user/update/check";
		
}
/**
 * 更新完了処理
 * パスワード変更
 * @param manaId
 * @param Managementform
 * @return complete
 */
	@RequestMapping("user/update/complete/{manaId}")
	public String update(@PathVariable Integer manaId,Managementform form) {
		 ManagementEntity Entity = repository.getReferenceById(manaId);
		BeanUtils.copyProperties(form, Entity, "manaId");
		Entity = repository.save(Entity);
		return "user/update/complete";
	}
	
	/**
	 * マイページ
	 * @param userId ユーザーのID
	 * @param model ユーザー情報
	 * @return user/info.html
	 */
	@RequestMapping(path="user/info/{userId}",method=RequestMethod.GET)
	public String user_info(@PathVariable Integer userId,Model model) {
		//ユーザーの情報を検索
		UsersEntity  usersEntity= usersRepository.getReferenceById(userId);
		//検索した内容を保存
		model.addAttribute("user",usersEntity);
		//正答率情報を取得してmodelで保存
		/** 正答率を表示する処理 */
		CategoriesEntity categoriesEntity = new CategoriesEntity();
		//ユーザーの問題を解いた数を数える
		int total = progressRepository.findByUsersEntityAndCategoriesEntity(usersEntity,categoriesEntity).size();
		model.addAttribute("total",total);
		//正解数を数える
		int collect = progressRepository.findByUsersEntityAndMissFlagAndCategoriesEntity(usersEntity, 0,categoriesEntity).size();
		model.addAttribute("collect",collect);
		//正答率をスコープに格納,正答率テーブルに保存
		int collectAnswerRate = collect/total;
		model.addAttribute("collectAnswerRate",collectAnswerRate);
			
		return "user/info";
	}
}
