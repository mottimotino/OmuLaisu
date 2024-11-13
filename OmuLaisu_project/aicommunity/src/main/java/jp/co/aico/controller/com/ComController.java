package jp.co.aico.controller.com;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.aico.entity.TimesEntity;
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
private  ReservationRepository reservationRepository;
@Autowired
private TimesRepository timesRepository;
/**
 * @author 村越
 * @return　view
 * カレンダーの日程を全件検索で表示するメソット
 */
//カレンダー機能
@RequestMapping("allViews")
public String views(Model model) {
	List<TimesEntity> timesAll = timesRepository.findAll();
	model.addAttribute("timesAll", timesAll);
    return "calendar/view";
}
@RequestMapping("reservationCheck")
public String Checks(Model model){
    Optional<TimesEntity> timesCheck = timesRepository.findById(reservationRequest.().gettimesId());

	return"calendar/check";
}
}