package jp.co.aico.controller.login;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;

public class LoginController {

	@RequestMapping(path = "/loginOnSession", method = RequestMethod.GET)
	public String loginOnSession() {
		return "session/loginOnSession";
	}

	@RequestMapping(path = "/doLginOnSession", method = RequestMethod.POST)
	public String doLoginOnSession(LoginForm form, HttpSession session) {
		if (form.getUserId() == 123456) {
			session.setAttribute("userId", form.getUserId());
			return "redirect:/";

		} else {
			return "session/loginOnSession";
		}
	}

	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
