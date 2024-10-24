package jp.co.aico.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class CategoriesEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_categories_gen")
	@SequenceGenerator(name = "seq_categories_gen", sequenceName = "seq_categories",allocationSize = 1)
	private Integer category_id;
	
	@Column
	private String category_name;
	
	@Column
	private Integer category_type;
}
