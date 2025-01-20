package jp.co.aico.form;

import java.util.Date;

/**
 * ユーザーに関する情報を受け取るform
 * @author 水野
 *
 */
public class UsersForm {

	private Integer usersId;

	private String password;

	private String name;

	private String mail;
	
	private Integer possessionPoint;
	
	private Date insertDate;
	
//	▼パスワード再設定用変数
	private String input;
	
	private String check;
//	▲パスワード再設定用変数

	public Integer getUsersId() {
		return usersId;
	}

	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Integer getPossessionPoint() {
		return possessionPoint;
	}

	public void setPossessionPoint(Integer possessionPoint) {
		this.possessionPoint = possessionPoint;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}
	
	
	
	

}
