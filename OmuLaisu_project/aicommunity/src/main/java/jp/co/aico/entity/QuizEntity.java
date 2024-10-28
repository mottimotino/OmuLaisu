package jp.co.aico.entity;

import java.util.Date;
import java.util.Locale.Category;

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
	private Category categoryId;

	public Integer getQue_id() {
		return que_id;
	}

	public void setQue_id(Integer que_id) {
		this.que_id = que_id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(Integer delete_flag) {
		this.delete_flag = delete_flag;
	}

	public Date getInsert_date() {
		return insert_date;
	}

	public void setInsert_date(Date insert_date) {
		this.insert_date = insert_date;
	}

	public String getMean() {
		return mean;
	}

	public void setMean(String mean) {
		this.mean = mean;
	}

	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}

	public Category getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Category categoryId) {
		this.categoryId = categoryId;
	}

}
