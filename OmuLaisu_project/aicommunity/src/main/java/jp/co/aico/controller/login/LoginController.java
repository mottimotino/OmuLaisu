package jp.co.aico.controller.login;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jp.co.aico.form.UsersForm;

public class LoginController {

//	@Autowired
//	UsersRepository;
	/**
	 * ログイン画面を表示
	 * @return login内のlogin.html
	 */
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login() {
		return "login/login";
	}

	@RequestMapping(path = "/doLgin", method = RequestMethod.POST)
	public String doLoginOnSession(UsersForm form, HttpSession session) {
		//入力されたメールアドレスを受け取る
		String mail = form.getMail();
		//入力パスワードを受け取る
		String password = form.getPassword();
		
		
		
		return "";
	}

}
