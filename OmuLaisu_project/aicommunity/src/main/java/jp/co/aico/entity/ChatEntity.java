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
	@JoinColumn(name = "send_user", referencedColumnName = "usersId")
	private UsersEntity sendUsersEntity;
	
	@ManyToOne
	@JoinColumn(name = "rece_user", referencedColumnName = "usersId")
	private UsersEntity receUsersEntity;

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

	public UsersEntity getSendUsersEntity() {
		return sendUsersEntity;
	}

	public void setSendUsersEntity(UsersEntity sendUsersEntity) {
		this.sendUsersEntity = sendUsersEntity;
	}

	public UsersEntity getReceUsersEntity() {
		return receUsersEntity;
	}

	public void setReceUsersEntity(UsersEntity receUsersEntity) {
		this.receUsersEntity = receUsersEntity;
	}

}