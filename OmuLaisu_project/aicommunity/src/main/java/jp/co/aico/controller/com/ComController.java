package jp.co.aico.controller.com;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.aico.entity.ReservationEntity;
import jp.co.aico.entity.TimesEntity;
import jp.co.aico.form.ComForm;
import jp.co.aico.repository.ReservationRepository;
import jp.co.aico.repository.TimesRepository;

/**
 * 会話予約
 * @author 鈴木
 *
 *html名
 *日程選択:view
 *選択確認:check
 *予約完了:input
 */
@Controller
public class ComController {
@Autowired
private  ;
@Autowired
private TimesRepository timesRepository;
/**
 * @author 村越
 * @return　view
 * カレンダーの日程を全件検索で表示するメソット
 */
//カレンダー機能
RequestMapping("/allViews")
public String allDays() {
	return"calendar/view";

}
@RequestMapping("/check")
//formクラス作成
public String Check(Model model,ComForm Comform) {
model.addAttribute("dateTime",Comform);
	return"calendar/check";
}

@RequestMapping("/complete")
public String complete() {
Entity = repository.save(Entity);
	return"calendar/input";
}
}
