package jp.co.aico.controller.com;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.aico.entity.ReservationEntity;
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
@RequestMapping("/checks")
public String Checks(@RequestBody ReservationEntity reservationRequest){
	//nullチェック
    Optional<TimesEntity> timesCheck = timesRepository.findById(reservationRequest.getTimesEntity().getTimesId());
    if (timesCheck.isPresent()) {
    	//GET送信
        TimesEntity timesEntity =timesCheck.get();
        //インスタンス化
        ReservationEntity reservationEntity = new ReservationEntity();
        //Set化
        reservationEntity.setTimesEntity(timesEntity);
	return"calendar/check";
    } else {
    return "calendar/view";
    }
    // 日程が見つからない場合、再度選択に戻す
    }
    @RequestMapping("/complete")
    public String complete(@RequestBody ReservationEntity reservationRequest) {
        Optional<TimesEntity> timesCheck = timesRepository.findById(reservationRequest.getTimesEntity().getTimesId());
        if (timesCheck.isPresent()) {
            TimesEntity timesEntity = timesCheck.get();

            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setTimesEntity(timesEntity);

            // 曜日の予約可否を設定
            reservationEntity.setMonday(reservationRequest.getMonday());
            reservationEntity.setTuesday(reservationRequest.getTuesday());
            reservationEntity.setWednesday(reservationRequest.getWednesday());
            reservationEntity.setThursday(reservationRequest.getThursday());
            reservationEntity.setFriday(reservationRequest.getFriday());
            reservationEntity.setSaturday(reservationRequest.getSaturday());
            reservationEntity.setSunday(reservationRequest.getSunday());
            reservationRepository.save(reservationEntity);
            return "calendar/input";  // 予約が完了し、予約完了の確認を行う
        } else {
            return "calendar/view";
            // 日程が見つからない場合、再度選択に戻す
        }
    }
}



