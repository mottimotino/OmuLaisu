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

import jakarta.servlet.http.HttpSession;
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
	public String complete(ComForm Comform, Model model) throws ParseException {
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
		if (caZoomEntity.size() == 0) {
			//URLがありません
			return "redirect:/allViews";
		} else {
			CaZoomEntity czEntity = caZoomEntity.get(0);
			ReservationDateEntity.setCaZoomEntity(czEntity);
			//削除フラグを変更
			//			czEntity.setDeleteFlag(1);
			//			caZoomRepository.save(czEntity);
			//zoomのURLを格納
			model.addAttribute("zoom", czEntity);
		}

		ReservationDateEntity.setDay(day);
		String weekday = Comform.getWeekday();
		ReservationDateEntity.setWeekday(weekday);
		ReservationDateEntity.setTimesEntity(timesEntity);
		ReservationDateEntity.setUsersEntity(usersEntity);
		//予約日時をDBに保存
		ReservationDateEntity = rdRepository.save(ReservationDateEntity);
		//予約情報を格納
		dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String showDay = dateFormat.format(day);
		model.addAttribute("day", showDay);
		model.addAttribute("weekday", weekday);
		return "calendar/input";
	}

	/**
	 * チャット画面
	 * @author 水野
	 * @param usersId ログイン中のid
	 * @param model メッセージ,ログイン中のユーザー
	 * @return チャット画面
	 */
	//	@RequestMapping("/chat/view/{usersId}")
	//	public String chat(@PathVariable String usersId, Model model) {
	//		UsersEntity usersEntity = new UsersEntity();
	//		usersEntity.setUsersId(Integer.parseInt(usersId));
	//		//メッセージを格納
	//		List<ChatEntity> chatEntity = chatRepository.findByUsersEntity(usersEntity);
	//		//一般ユーザーが送ったメッセージの処理
	//		//送られたユーザーIDをnullにしない為に仮で0を入れる
	//		for (int i = 0; i < chatEntity.size(); i++) {
	//			ChatEntity chat = chatEntity.get(i);
	//			if (chat.getReceUsersEntity() == null) {
	//				UsersEntity users = new UsersEntity();
	//				users.setUsersId(0);
	//				chat.setReceUsersEntity(users);
	//				chatEntity.set(i, chat);
	//			}
	//		}
	//
	//		model.addAttribute("messages", chatEntity);
	//		//ユーザーの情報をmodelに格納
	//		model.addAttribute("user", usersRepository.getReferenceById(Integer.parseInt(usersId)));
	//
	//		return "chat/view";
	//	}

	@RequestMapping(path = "/chat/select", method = RequestMethod.GET)
	public String chat(Model model, HttpSession session) {
		//自身のユーザー情報を取得
		int usersId = (int) session.getAttribute("usersId");
		UsersEntity user = usersRepository.getReferenceById(usersId);
		//権限を変数に代入
		int authority = user.getAuthority();
		//自身の権限が一般ユーザーの場合メンター、メンターなら一般ユーザーを検索する
		if (authority == 2) {
			authority = 3;
		} else if (authority == 3) {
			authority = 2;
		}
		//権限で検索し、modelに格納
		model.addAttribute("users", usersRepository.findByAuthority(authority));
		//自身の権限をmodelに格納
		model.addAttribute("loginAuthority", user.getAuthority());
		return "chat/select";
	}

	/**
	 * メッセージを送る(登録する)処理
	 * @param form sendUsersId,receUsersId,message
	 * @return chat/view.html
	 */
	@RequestMapping(path = "/chat/send", method = RequestMethod.POST)
	public String chat_send(ChatForm form, Model model) {
		ChatEntity chatEntity = new ChatEntity();
		//送るユーザーのid
		UsersEntity sendUser = new UsersEntity();
		sendUser.setUsersId(form.getSendUser());
		chatEntity.setSendUsersEntity(sendUser);
		//受け取るユーザーのid
		UsersEntity raceUser = new UsersEntity();

		raceUser.setUsersId(form.getReceUser());
		chatEntity.setReceUsersEntity(raceUser);

		//メッセージの内容
		chatEntity.setMessage(form.getMessage());
		//chatテーブルにデータ登録
		chatRepository.save(chatEntity);

		return "redirect:/chat/view";

	}

	/**
	 * チャット画面(メンター用)
	 * @author 水野
	 * @param loginUsersId ログイン中のid
	 * @param usersId 相手のid
	 * @param model メッセージ,ログイン中のユーザー
	 * @return チャット画面
	 */
	@RequestMapping("/chat/view")
	public String chatView(ComForm comForm, Model model, HttpSession session) {
		//ログイン中のusersIdを取得
		int loginUsersId = (int) session.getAttribute("usersId");
		//選択したusersIdを取得
		Integer usersId = comForm.getUsersId();
		if(usersId == null) {
			UsersEntity usersEntity = (UsersEntity)session.getAttribute("receUser");
			usersId = usersEntity.getUsersId();
		}
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
		model.addAttribute("messages", chatEntity);
		//ユーザーの情報をmodelに格納
		model.addAttribute("loginUser", usersRepository.getReferenceById(loginUsersId));
		//相手のユーザー情報をmodelに格納
		session.setAttribute("receUser", usersRepository.getReferenceById(usersId));

		return "chat/view";
	}
//	@RequestMapping("/zoom/transition")
//		public String userExternalReference(Model model,HttpSession session) {
//		UsersEntity users=new UsersEntity();
//		users.setUsersId((int)session.getAttribute("usersId"));
//			model.addAttribute("zoomLinkSave",rdRepository.findByUsersEntity(users));
//			return"/transition";
//		}
	}


