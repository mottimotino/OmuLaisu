package jp.co.aico.controller.login;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jp.co.aico.entity.UsersEntity;
import jp.co.aico.form.UsersForm;
import jp.co.aico.repository.UsersRepository;

/**
 * ログイン機能のコントローラー
 * @author 水野
 *
 */
public class LoginController {

//	//会員テーブルのリポジトリ
	@Autowired
	UsersRepository usersRepository;
	/**
	 * ログイン画面を表示
	 * @return login内のlogin.html
	 */
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login() {
		return "login/login";
	}

/**
	 * ログイン認証
	 * @param form 入力されたメールアドレス,パスワード
	 * @param session メールアドレス,パスワードを保存
	 * @return top内のdotop.html/login内のlogin.html
	 */	@RequestMapping(path = "/doLogin", method = RequestMethod.POST)
	public String doLogin(UsersForm form, HttpSession session) {
		//入力されたメールアドレスを受け取る
		String mail = form.getMail();
		//入力パスワードを受け取る
		String password = form.getPassword();
		//入力された内容で条件検索
		UsersEntity users = usersRepository.findByMailAndPassword(mail, password);
		
	//ログインに成功
		if(users != null) {
			//入力内容をsessionで保存
			session.setAttribute("mail", mail);
			session.setAttribute("password", password);
			//ログイン後トップ画面に遷移
			return "top/dotop";
		//ログインに失敗
		} else {
			//入力画面に遷移
			return "login/login";
		}	}
	@RequestMapping(path = "/doLogin",method = RequestMethod.POST)
	public String logout(HttpSession session) {
		session.invalidate();
		return "top:/";
	}
}
