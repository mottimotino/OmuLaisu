package jp.co.aico.controller.com;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 会話予約
 * @author 鈴木
 *
 */
@Controller
public class ComController {
	
	@RequestMapping("/calender")
	//scheduleReservation→日程予約 名前短縮してscheReserv
	public String scheReserv() {
		return "";
	}
	
	@RequestMapping("/calender")
	//scheduleReservationConfirmation→日程予約確認 名前短縮してscheReservConfir
	public String scheReservConfir() {
		return "";
	}
	
	@RequestMapping("/calender")
	//scheduleReservationCompleted→日程予約完了 名前短縮してscheReservComp
	public String scheReservComp() {
		return "";
	}

}
