package jp.co.aico.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class TimesEntity {
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_time")
@SequenceGenerator(name = "seq_time", sequenceName = "seq_times",allocationSize = 1)
private Integer timesId;
@Column
private Date startTimes;
@Column
private Date endTimes;
public Integer getTimesId() {
	return timesId;
}
public void setTimesId(Integer timesId) {
	this.timesId = timesId;
}
public Date getStartTimes() {
	return startTimes;
}
public void setStartTimes(Date startTimes) {
	this.startTimes = startTimes;
}
public Date getEndTimes() {
	return endTimes;
}
public void setEndTimes(Date endTimes) {
	this.endTimes = endTimes;
}

}
