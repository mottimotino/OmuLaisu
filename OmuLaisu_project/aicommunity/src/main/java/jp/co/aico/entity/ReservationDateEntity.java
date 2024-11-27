package jp.co.aico.entity;

import java.util.Date;

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
@Table(name = "reservation_date")
public class ReservationDateEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_reservation_date_gen")
	@SequenceGenerator(name = "seq_reservation_date_gen", sequenceName = "seq_reservation_date", allocationSize = 1)
	public Integer dateId;
	@Column
	public Date day;
	@Column
	public String weekday;
	@ManyToOne
	@JoinColumn(name = "users_id", referencedColumnName = "usersId")
	private UsersEntity usersEntity;
	@ManyToOne
	@JoinColumn(name = "times_id", referencedColumnName = "timesId")
	private TimesEntity timesEntity;
	public Integer getDateId() {
		return dateId;
	}
	public void setDateId(Integer dateId) {
		this.dateId = dateId;
	}
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public String getWeekday() {
		return weekday;
	}
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	public UsersEntity getUsersEntity() {
		return usersEntity;
	}
	public void setUsersEntity(UsersEntity usersEntity) {
		this.usersEntity = usersEntity;
	}
	public TimesEntity getTimesEntity() {
		return timesEntity;
	}
	public void setTimesEntity(TimesEntity timesEntity) {
		this.timesEntity = timesEntity;
	}
	
	

}
