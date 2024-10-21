package jp.co.aico.controller.login;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class Login {

	@RequestMapping(path = "/loginOnRequest", method = RequestMethod.GET)
	public String loginOnRequest() {
		return "session/loginOnRequest";

	}

	@RequestMapping(path = "/logoutOnRequest", method = RequestMethod.POST)
	public String logoutOnRequest() {
		return "session/logoutOnRequest";
	}
}
