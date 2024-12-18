package jp.co.aico.controller.com;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.aico.entity.ReservationDateEntity;
import jp.co.aico.entity.TimesEntity;
import jp.co.aico.entity.UsersEntity;
import jp.co.aico.form.ComForm;
import jp.co.aico.repository.ReservationDateRepository;
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
	ReservationDateRepository rdRepository;
	@Autowired
	TimesRepository timesRepository;

	/**
	 * @author 村越
	 * @return　view
	 * カレンダーの日程を全件検索で表示するメソット
	 */
	//カレンダー機能
	/*
	 * 全件処理
	 */
	@RequestMapping("/allViews")
	public String allDays() {
		return "calendar/view";

	}

	//3
	/*
	 * 日時の確認画面
	 */
	@RequestMapping(path = "/timesCheck", method = RequestMethod.GET)
	public String timesCheck(Model model, ComForm Comform) {
		TimesEntity timesEntity = timesRepository.getReferenceById(Comform.getTimesId());
		model.addAttribute("timesId", timesEntity);
		model.addAttribute("day", Comform.getDay());
		model.addAttribute("weekDay",Comform.getWeekday());
		return "calendar/check";
	}

	//2
	/*
	 * 時間確認画面の遷移
	 */
	@RequestMapping(path = "/check/{day}/{weekDay}", method = RequestMethod.GET)

	public String Check(Model model, ComForm Comform, @PathVariable String day,@PathVariable String weekDay) {
		model.addAttribute("day", day);
		model.addAttribute("weekDay",weekDay);
		//時間の選択肢の情報をモデルに格納
		model.addAttribute("times",timesRepository.findAll());
		return "calendar/timesCheck";
	}

	//4
	/*
	 * 登録処理
	 */
	@RequestMapping("/complete")
	public String complete(ComForm Comform) throws ParseException {
		ReservationDateEntity ReservationDateEntity = new ReservationDateEntity();
		TimesEntity timesEntity = new TimesEntity();
		timesEntity.setTimesId(Comform.getTimesId());
		UsersEntity usersEntity = new UsersEntity();
		usersEntity.setUsersId(Comform.getUsersId());
		//BeanUtils.copyProperties(Comform, ReservationDateEntity, "dateId");
		//もし、上がダメだった場合以下のコメントアウトしたコードを実装する
		
		//Date型に変換
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date day = dateFormat.parse(Comform.getDay());
		
		ReservationDateEntity.setDay(day);
		ReservationDateEntity.setWeekday(Comform.getWeekday());
		ReservationDateEntity.setTimesEntity(timesEntity);
		ReservationDateEntity.setUsersEntity(usersEntity);
		ReservationDateEntity = rdRepository.save(ReservationDateEntity);
		return "calendar/input";
	}

	@RequestMapping("/chat/view")
	public String chat() {
		return "chat/view";
	}
	
	
}
