package jp.co.aico.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "times")
public class TimesEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_time")
	@SequenceGenerator(name = "seq_time", sequenceName = "seq_times", allocationSize = 1)
	private Integer timesId;
	@Column
	private String startTimes;
	@Column
	private String endTimes;

	public Integer getTimesId() {
		return timesId;
	}

	public void setTimesId(Integer timesId) {
		this.timesId = timesId;
	}

	public String getStartTimes() {
		return startTimes;
	}

	public void setStartTimes(String startTimes) {
		this.startTimes = startTimes;
	}

	public String getEndTimes() {
		return endTimes;
	}

	public void setEndTimes(String endTimes) {
		this.endTimes = endTimes;
	}

}
