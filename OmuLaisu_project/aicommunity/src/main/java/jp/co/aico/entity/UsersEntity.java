package jp.co.aico.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class UsersEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users_gen")
	@SequenceGenerator(name = "seq_users_gen", sequenceName = "seq_users",allocationSize = 1)
	private Integer usersId;
	
	@Column
	private String password;
	
	@Column
	private String name;
	
	@Column
	private String mail;
	
	@Column
	private Integer authority;
	
	@Column
	private Integer possessionPoint;
	
	@Column
	private Integer delete_flag;
	
	@Column
	private Date insert_date;

}
