package jp.co.aico.controller.help;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelpController {
@RequestMapping("help/question")
public String top() {
	return "help/question";
}
}
