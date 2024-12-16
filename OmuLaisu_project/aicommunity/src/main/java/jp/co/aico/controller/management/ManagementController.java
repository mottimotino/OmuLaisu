package jp.co.aico.controller.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	@RequestMapping("user/update/input/{usersId}")
	public String kousin(@PathVariable Integer usersId, Model model) {
		UsersEntity Entity = usersRepository.getReferenceById(usersId);
		model.addAttribute("oldPassword", Entity);
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
	@RequestMapping("user/update/complete/{usersId}")
	public String update(@PathVariable Integer usersId,Managementform form) {
		UsersEntity Entity = usersRepository.getReferenceById(usersId);
		//BeanUtils.copyProperties(form, Entity, "usersId");
		Entity.setPassword(form.getPassword());
		Entity = usersRepository.save(Entity);
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
		//ユーザーの正答率をスコープに格納
		model.addAttribute("accuracy",accuracyRepository.findByAccuracyId(userId));
		return "user/info";
	}
}
