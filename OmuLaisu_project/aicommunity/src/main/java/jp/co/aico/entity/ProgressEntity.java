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
	private Integer progress;
	
	@Column
	private Integer missFlag;
	
	@ManyToOne
	@JoinColumn(name = "users_id",referencedColumnName = "usersId")
	private Integer usersId;
	
	@ManyToOne
	@JoinColumn(name = "que_id",referencedColumnName = "queId")
	private Integer queId;

	public Integer getProId() {
		return proId;
	}

	public void setProId(Integer proId) {
		this.proId = proId;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public Integer getMissFlag() {
		return missFlag;
	}

	public void setMissFlag(Integer missFlag) {
		this.missFlag = missFlag;
	}

	public Integer getUsersId() {
		return usersId;
	}

	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}

	public Integer getQueId() {
		return queId;
	}

	public void setQueId(Integer queId) {
		this.queId = queId;
	}
	

}
