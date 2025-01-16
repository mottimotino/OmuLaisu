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
@Table(name = "ca_zoom")
public class CaZoomEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ca_zoom_gen")
	@SequenceGenerator(name = "seq_ca_zoom_gen", sequenceName = "seq_ca_zoom_date", allocationSize = 1)
	private Integer zoomId;
	
	@Column
	private String url;
	
	@Column
	private Date insertDate;
	
	@Column
	private Integer deleteFlag;

	public Integer getZoomId() {
		return zoomId;
	}

	public void setZoomId(Integer zoomId) {
		this.zoomId = zoomId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	


}
