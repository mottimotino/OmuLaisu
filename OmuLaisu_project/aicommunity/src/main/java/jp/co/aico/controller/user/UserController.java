package jp.co.aico.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
	
	//ログイン中サイドバー内画面遷移(一旦基本機能のみ)
	@RequestMapping("/top/sidebar")
	public String sidebar() {
		return "top/sidebar";
	}
	
	//日程予約
	@RequestMapping("/calender/view")
	public String view() {
		return "calender/view";
	}
	
	//クイズの遷移はQuizControllerにあります
//	@RequestMapping(path = "/quiz/list", method = RequestMethod.GET)
//	public String quiz_list() {
//		return "quiz/list";
//	}
	
	//マイページ(ManagementControllerに記載)
	@RequestMapping(path = "/user/info", method = RequestMethod.GET)
	public String mypage() {
		return "user/info";
	}
	
	//Topに戻る(命名どうする？)
	@RequestMapping(path = "/top/dotop/back", method = RequestMethod.GET)
	public String loginAfter() {
		return "top/dotop";
		
	}
	
	//追加機能コントローラー
	//日程予約リンク表示
	//チャット
	//問い合わせ
	

}
