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
@Table(name = "prize")
public class PrizeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_prize_gen")
	@SequenceGenerator(name = "seq_prize_gen", sequenceName = "seq_prize",allocationSize = 1)
	private Integer prizeId;
	
	@Column
	private String prizeName;
	
	//default制約の書き方→@Columnで大丈夫
	@Column
	private Integer deleteFlag;
	
	//一旦確認
	@ManyToOne
	@JoinColumn(name = "category_id",referencedColumnName = "usersId")
	private Integer categoryId;
}
