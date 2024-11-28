package jp.co.aico.form;
/*
 * クイズ機能のform
 * @author 水野学
 *
 */
public class QuizForm {
	
	/** 問題ID */
	private Integer queId;
	/** 選択した番号 */
	private  Integer answer;
	/** 問題のジャンル(読み/書き) */
	private String categoryType;
	/** 問題のジャンル */
	private String categoryName;

	public Integer getQueId() {
		return queId;
	}

	public void setQueId(Integer queId) {
		this.queId = queId;
	}

	public Integer getAnswer() {
		return answer;
	}

	public void setAnswer(Integer answer) {
		this.answer = answer;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	

	
	
	
	
	

}
