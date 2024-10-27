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
@Table(name = "quiz")
public class QuizEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_quiz_gen")
	@SequenceGenerator(name = "seq_quiz_gen",sequenceName = "seq_quiz",allocationSize = 1)
	private Integer que_id;
	
	@Column
	private String question;
	
	@Column
	private String answer;
	
	@Column
	private Integer delete_flag;
	
	@Column
	private Date insert_date;
	
	@Column
	private String mean;
	
	@Column
	private String read;
	
	//Category型とは？
	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "categoryId")
	private Integer categoryId;

}
