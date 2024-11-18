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
@Table(name = "progress")
public class ProgressEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_progress_gen")
	@SequenceGenerator(name = "seq_progress_gen", sequenceName = "seq_progress",allocationSize = 1)
	private Integer proId;
	
	@Column
	private Integer missFlag;
	
	@ManyToOne
	@JoinColumn(name = "users_id",referencedColumnName = "usersId")
	private UsersEntity usersEntity;
	
	@ManyToOne
	@JoinColumn(name = "que_id",referencedColumnName = "queId")
	private QuizEntity quizEntity;

	public Integer getProId() {
		return proId;
	}

	public void setProId(Integer proId) {
		this.proId = proId;
	}

	public Integer getMissFlag() {
		return missFlag;
	}

	public void setMissFlag(Integer missFlag) {
		this.missFlag = missFlag;
	}

	public UsersEntity getUsersEntity() {
		return usersEntity;
	}

	public void setUsersEntity(UsersEntity usersEntity) {
		this.usersEntity = usersEntity;
	}

	public QuizEntity getQuizEntity() {
		return quizEntity;
	}

	public void setQuizEntity(QuizEntity quizEntity) {
		this.quizEntity = quizEntity;
	}

	
	

}
