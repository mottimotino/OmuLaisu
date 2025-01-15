package jp.co.aico.controller.com;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.aico.entity.CaZoomEntity;
import jp.co.aico.entity.ChatEntity;
import jp.co.aico.entity.ReservationDateEntity;
import jp.co.aico.entity.TimesEntity;
import jp.co.aico.entity.UsersEntity;
import jp.co.aico.form.ChatForm;
import jp.co.aico.form.ComForm;
import jp.co.aico.repository.CaZoomRepository;
import jp.co.aico.repository.ChatRepository;
import jp.co.aico.repository.ReservationDateRepository;
import jp.co.aico.repository.TimesRepository;
import jp.co.aico.repository.UsersRepository;

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

	@Autowired
	ChatRepository chatRepository;
	@Autowired
	UsersRepository usersRepository;
	@Autowired
	CaZoomRepository caZoomRepository;

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
		model.addAttribute("weekDay", Comform.getWeekday());
		return "calendar/check";
	}

	//2
	/*
	 * 時間確認画面の遷移
	 */
	@RequestMapping(path = "/check/{day}/{weekDay}", method = RequestMethod.GET)

	public String Check(Model model, ComForm Comform, @PathVariable String day, @PathVariable String weekDay) {
		model.addAttribute("day", day);
		model.addAttribute("weekDay", weekDay);
		//時間の選択肢の情報をモデルに格納
		model.addAttribute("times", timesRepository.findAll());
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

		//日付をDate型に変換
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date day = dateFormat.parse(Comform.getDay());
		
		//zoomURL
		//削除されていない,一番古いレコードを取得
		List<CaZoomEntity> caZoomEntity = caZoomRepository.findByDeleteFlag(0);
		if(caZoomEntity.size() == 0) {
			//URLがありません
			return "redirect:/allViews";
		} else {
			CaZoomEntity czEntity = caZoomEntity.get(0);
			ReservationDateEntity.setCaZoomEntity(czEntity);
			//削除フラグを変更
			czEntity.setDeleteFlag(1);
			caZoomRepository.save(czEntity);
		}

		ReservationDateEntity.setDay(day);
		ReservationDateEntity.setWeekday(Comform.getWeekday());
		ReservationDateEntity.setTimesEntity(timesEntity);
		ReservationDateEntity.setUsersEntity(usersEntity);
		ReservationDateEntity = rdRepository.save(ReservationDateEntity);
		return "calendar/input";
	}

	/**
	 * チャット画面
	 * @author 水野
	 * @param usersId ログイン中のid
	 * @param model メッセージ,ログイン中のユーザー
	 * @return チャット画面
	 */
	@RequestMapping("/chat/view/{usersId}")
	public String chat(@PathVariable Integer usersId, Model model) {
		UsersEntity usersEntity = new UsersEntity();
		usersEntity.setUsersId(usersId);
		//メッセージを格納
		List<ChatEntity> chatEntity = chatRepository.findByUsersEntity(usersEntity);
		//一般ユーザーが送ったメッセージの処理
		//送られたユーザーIDをnullにしない為に仮で0を入れる
		for (int i = 0; i < chatEntity.size(); i++) {
			ChatEntity chat = chatEntity.get(i);
			if (chat.getReceUsersEntity() == null) {
				UsersEntity users = new UsersEntity();
				users.setUsersId(0);
				chat.setReceUsersEntity(users);
				chatEntity.set(i, chat);
			}
		}

		model.addAttribute("messages", chatEntity);
		//ユーザーの情報をmodelに格納
		model.addAttribute("user", usersRepository.getReferenceById(usersId));

		return "chat/view";
	}

	/**
	 * メッセージを送る(登録する)処理
	 * @param form sendUsersId,receUsersId,message
	 * @return chat/view.html
	 */
	@RequestMapping(path = "/chat/send", method = RequestMethod.POST)
	public String chat_send(ChatForm form) {

		ChatEntity chatEntity = new ChatEntity();
		//送るユーザーのid
		UsersEntity sendUser = new UsersEntity();
		sendUser.setUsersId(form.getSendUser());
		chatEntity.setSendUsersEntity(sendUser);
		//受け取るユーザーのid
		UsersEntity raceUser = new UsersEntity();
		if (form.getReceUser() != null) {
			raceUser.setUsersId(form.getReceUser());
			chatEntity.setReceUsersEntity(raceUser);
		}

		//メッセージの内容
		chatEntity.setMessage(form.getMessage());
		//chatテーブルにデータ登録
		chatRepository.save(chatEntity);
		
		//ユーザーの権限によってreturnを変更
		sendUser = usersRepository.getReferenceById(form.getSendUser());
		//メンター
		if(sendUser.getAuthority() == 3) {
			return "redirect:/chat/view/" + form.getSendUser() + "/" + form.getReceUser();
		//一般ユーザー
		} else if(sendUser.getAuthority() == 2) {
			return "redirect:/chat/view/" + form.getSendUser();
		} else {
			return "";
		}
	}

	/**
	 * チャット画面(メンター用)
	 * @author 水野
	 * @param loginUsersId ログイン中のid
	 * @param usersId 相手のid
	 * @param model メッセージ,ログイン中のユーザー
	 * @return チャット画面
	 */
	@RequestMapping("/chat/view/{loginUsersId}/{usersId}")
	public String chat(@PathVariable Integer loginUsersId,@PathVariable Integer usersId, Model model) {
		//ログイン中のusersIdをエンティティに保存
		UsersEntity loginUsersEntity = new UsersEntity();
		loginUsersEntity.setUsersId(loginUsersId);
		//チャット相手のusersIdをエンティティに保存
		UsersEntity usersEntity = new UsersEntity();
		usersEntity.setUsersId(usersId);
		//メッセージを格納
		List<ChatEntity> chatEntity = chatRepository.findByUsersEntity(usersEntity);
		//受け取ったユーザーIDをnullにしない為に仮で0を入れる
		for (int i = 0; i < chatEntity.size(); i++) {
			ChatEntity chat = chatEntity.get(i);
			if (chat.getReceUsersEntity() == null) {
				UsersEntity users = new UsersEntity();
				users.setUsersId(0);
				chat.setReceUsersEntity(users);
				chatEntity.set(i, chat);
			}
		}
		model.addAttribute("messages",chatEntity);
		//ユーザーの情報をmodelに格納
		model.addAttribute("user",usersRepository.getReferenceById(loginUsersId));
		//相手のユーザー情報をmodelに格納
		model.addAttribute("receUser",usersRepository.getReferenceById(usersId));
		
		return "chat/view";
	}

}
