package jp.co.aico.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "accuracy")
public class AccuracyEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_acc_gen")
	@SequenceGenerator(name = "seq_acc_gen", sequenceName = "seq_acc",allocationSize = 1)
	private Integer accuracyId;
	
	@Column
	private Integer progress;
	
	@OneToOne
	@JoinColumn(name = "category_id", referencedColumnName = "categoryId")
	private CategoriesEntity categoriesEntity;

	public Integer getAccuracyId() {
		return accuracyId;
	}

	public void setAccuracyId(Integer accuracyId) {
		this.accuracyId = accuracyId;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public CategoriesEntity getCategoriesEntity() {
		return categoriesEntity;
	}

	public void setCategoriesEntity(CategoriesEntity categoriesEntity) {
		this.categoriesEntity = categoriesEntity;
	}
	
	

}
