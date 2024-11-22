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
	private Integer queId;
	
	@Column
	private String question;
	
	@Column
	private Integer answer;
	
	@Column
	private Integer delete_flag;
	
	@Column
	private Date insert_date;
	
	@Column
	private String mean;
	
	@Column
	private String read;
	
	@Column
	private String explanation;
	
	@Column
	private String choise1;
	@Column
	private String choise2;
	@Column
	private String choise3;
	@Column
	private String choise4;
	//Category型とは？
	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "categoryId")
	private CategoriesEntity categoriesEntity;
	public Integer getQueId() {
		return queId;
	}
	public void setQueId(Integer queId) {
		this.queId = queId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Integer getAnswer() {
		return answer;
	}
	public void setAnswer(Integer answer) {
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
	
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public String getChoise1() {
		return choise1;
	}
	public void setChoise1(String choise1) {
		this.choise1 = choise1;
	}
	public String getChoise2() {
		return choise2;
	}
	public void setChoise2(String choise2) {
		this.choise2 = choise2;
	}
	public String getChoise3() {
		return choise3;
	}
	public void setChoise3(String choise3) {
		this.choise3 = choise3;
	}
	public String getChoise4() {
		return choise4;
	}
	public void setChoise4(String choise4) {
		this.choise4 = choise4;
	}
	public CategoriesEntity getCategoriesEntity() {
		return categoriesEntity;
	}
	public void setCategoriesEntity(CategoriesEntity categoriesEntity) {
		this.categoriesEntity = categoriesEntity;
	}

}