package jp.co.aico.controller.login;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
@Controller
public class LoginController {

//	//会員テーブルのリポジトリ
	@Autowired
	UsersRepository usersRepository;	
	
	@RequestMapping(path = "/top2", method = RequestMethod.GET)
	public String top2() {
		return "top/top2";
	}
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
	 */	
	@RequestMapping(path = "/doLogin", method = RequestMethod.POST)
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
			session.setAttribute("usersId", users.getUsersId());
			session.setAttribute("mail", mail);
			session.setAttribute("password", password);
			//ログイン後トップ画面に遷移
			return "top/dotop";
		//ログインに失敗
		} else {
			//入力画面に遷移
			return "login/login";
		}	}
	@RequestMapping(path = "/doLogin",method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "top:/";
	}
	//マネジメントコントローラーに記載
//	//パスワード再設定画面への遷移
//	@RequestMapping("user/update/input")
//	public String passwordChange() {
//		return "user/update/input";
//	}
	
	
	
	/**
	 * ▼▼ここからパスワードを忘れた場合のパスワード変更
	 * メールアドレス確認
	 * @param manaId
	 * @param model
	 * @return input
	 * 
	 */
//	@RequestMapping("user/update/input/{usersId}")
//	public String kousin(@PathVariable Integer usersId, Model model) {
//		UsersEntity Entity = usersRepository.getReferenceById(usersId);
//		model.addAttribute("oldPassword", Entity);
//		return "user/update/input";
//
//	}
	
	@RequestMapping("/login/mailCheck")
	public String mailCheck() {
//	public String mailCheck(@PathVariable String select,HttpSession session) {
//		session.setAttribute("select", select);
		return "login/mailCheck";
	}
	
//	▲▲メールアドレス確認

	/**
	 *パスワード変更
	 * 確認form
	 * @param form
	 * @param model
	 * @return check
	 */
	@RequestMapping(path="/login/passwordForget",method=RequestMethod.POST)
	public String Forgetpass(UsersForm form, Model model) {
		UsersEntity usersEntity = usersRepository.findByMail(form.getMail());
		
	if(form.getMail().equals(usersEntity.getMail())){
		model.addAttribute("mail", form.getMail());
		model.addAttribute("user",usersEntity);
		
		return "login/passwordForget";
	} else{
		return "user/mailCheck";
		}

	}

	/**
	 * 更新完了処理
	 * パスワード変更完了
	 * @param manaId
	 * @param Managementform
	 * @return complete
	 * パスワードが正しく変わっているかを確かめる
	 * パスワードとメルアドを同時に照合
	 * メアドで検索→パスワードを上書き
	 */
	@RequestMapping(path = "/login/passwordCommit", method = RequestMethod.POST)
	public String PassChange(String password,UsersEntity user) {
//		public String PassChange(UsersForm form, Model model) {
//		これで実行できるか確認→実行できないとパスワード以外はnullで更新するといった行動になってしまっている。
//		UsersEntity usersEntity = usersRepository.findByMailAndPassword(form.getMail(),form.getPassword());
//		BeanUtils.copyProperties(form, user, "usersId");
		System.out.println(user.getName());
		user.setPassword(password);
		usersRepository.save(user);
//		UsersBean usersBean = new UsersBean();
//		BeanUtils.copyProperties(usersEntity, usersBean);
//		model.addAttribute("users", usersBean);
		return "login/passwordCommit";
	}
	
	@RequestMapping("/login")
	public String passCommit(@PathVariable String select,HttpSession session) {
		session.setAttribute("select", select);
		return "login/login";
	}
	
//	パスワード変更ここまで▲▲

}
