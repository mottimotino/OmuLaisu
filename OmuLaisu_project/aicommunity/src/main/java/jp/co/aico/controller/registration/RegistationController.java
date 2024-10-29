package jp.co.aico.controller.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistationController {
@Autowired
/**
 * /RepositoryをAutowiredの下にインスタンス化してください
 * 入力ホーム
 * 村越
 * 	return input.html
 */
@RequestMapping("/user/regist/Input")
public String registForm() {
	return "/user/regist/input";
}
/**
 * 確認ホーム
 * 村越
 */
@RequestMapping("/user/regist/check")
public String registCheck() {
	return "";
	
}
}
