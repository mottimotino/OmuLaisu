package jp.co.aico.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "chat")
public class ChatEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_chat_gen")
	@SequenceGenerator(name = "seq_chat_gen", sequenceName = "seq_chat",allocationSize = 1)
	private Integer chatId;
	
	@Column
	private String message;
	
	@ManyToOne
	@JoinColumn(name = "users_id", referencedColumnName = "usersId")
	private Integer sendUser;
	
	@ManyToOne
	@JoinColumn(name = "users_id", referencedColumnName = "usersId")
	private Integer receUser;

	public Integer getChatId() {
		return chatId;
	}

	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getSendUser() {
		return sendUser;
	}

	public void setSendUser(Integer sendUser) {
		this.sendUser = sendUser;
	}

	public Integer getReceUser() {
		return receUser;
	}

	public void setReceUser(Integer receUser) {
		this.receUser = receUser;
	}
	

}
