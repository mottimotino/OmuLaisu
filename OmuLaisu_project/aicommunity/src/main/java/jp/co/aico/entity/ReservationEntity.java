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
@Table(name = "reservation")
public class ReservationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_yoyaku")
	@SequenceGenerator(name = "seq_yoyaku", sequenceName = "seq_reservation", allocationSize = 1)
	private Integer reservationId;
	@Column
	private String Monday;
	@Column
	private String Tuesday;
	@Column
	private String Wednesday;
	@Column
	private String Thursday;
	@Column
	private String Friday;
	@Column
	private String Saturday;
	@Column
	private String Sunday;
	@ManyToOne
	@JoinColumn(name = "users_id", referencedColumnName = "usersId")
	private UsersEntity usersEntity;
	@ManyToOne
	@JoinColumn(name = "times_id", referencedColumnName = "timesId")
	private TimesEntity timesEntity;

	public Integer getReservationId() {
		return reservationId;
	}

	public void setReservationId(Integer reservationId) {
		this.reservationId = reservationId;
	}

	public String getMonday() {
		return Monday;
	}

	public void setMonday(String monday) {
		Monday = monday;
	}

	public String getTuesday() {
		return Tuesday;
	}

	public void setTuesday(String tuesday) {
		Tuesday = tuesday;
	}

	public String getWednesday() {
		return Wednesday;
	}

	public void setWednesday(String wednesday) {
		Wednesday = wednesday;
	}

	public String getThursday() {
		return Thursday;
	}

	public void setThursday(String thursday) {
		Thursday = thursday;
	}

	public String getFriday() {
		return Friday;
	}

	public void setFriday(String friday) {
		Friday = friday;
	}

	public String getSaturday() {
		return Saturday;
	}

	public void setSaturday(String saturday) {
		Saturday = saturday;
	}

	public String getSunday() {
		return Sunday;
	}

	public void setSunday(String sunday) {
		Sunday = sunday;
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
