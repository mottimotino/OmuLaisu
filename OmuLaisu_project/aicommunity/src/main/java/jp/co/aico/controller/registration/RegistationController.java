package jp.co.aico.controller.registration;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jp.co.aico.entity.UsersEntity;
import jp.co.aico.form.UsersForm;
import jp.co.aico.repository.UsersRepository;

@Controller
public class RegistationController {
	@Autowired
	UsersRepository repository;

	/**
	 * /RepositoryをAutowiredの下にインスタンス化してください
	 * 入力ホーム
	 * 村越
	 * 	return input.html
	 */
	@RequestMapping("/user/regist/input")
	public String registForm() {
		return "/user/regist/input";
	}

	/**
	 * 確認ホーム
	 * 村越
	 * returnで/user/regist/checkに遷移
	 */
	@RequestMapping("/user/regist/check")
	public String registCheck(Model model, UsersForm form) {
		UsersEntity usersEntity = repository.findByMail(form.getMail());
		if(usersEntity != null) {
			model.addAttribute("msg","※入力したメールアドレスは使われています(The email address you entered is in use)");
			return "user/regist/input";
		}
		model.addAttribute("Users", form);
		return "/user/regist/check";
	}

	//完了画面
	@RequestMapping(path = "/user/regist/complete", method = RequestMethod.POST)
	public String registComplete(UsersForm form, HttpSession session) {
		UsersEntity Entity = new UsersEntity();
		Entity.setName(form.getName());
		Entity.setPassword(form.getPassword());
		Entity.setMail(form.getMail());
		Entity.setAuthority(form.getAuthority());
		Entity.setDeleteFlag(0);
		Date date = new Date();
		Entity.setInsertDate(date);
		Entity = repository.save(Entity);
		session.setAttribute("userId", Entity.getUsersId());
		return "/user/regist/complete";
	}
}