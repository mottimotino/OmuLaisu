package jp.co.aico.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "management")
public class ManagementEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mana_gen")
	@SequenceGenerator(name = "seq_mana_gen", sequenceName = "seq_mana",allocationSize = 1)
	private Integer manaId;
	
	@ManyToOne
	@JoinColumn(name = "prize_id", referencedColumnName = "prizeId")
	private Integer prizeId;
	
	@ManyToOne
	@JoinColumn(name = "users_id", referencedColumnName = "usersId")
	private Integer usersId;

	public Integer getManaId() {
		return manaId;
	}

	public void setManaId(Integer manaId) {
		this.manaId = manaId;
	}

	public Integer getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Integer prizeId) {
		this.prizeId = prizeId;
	}

	public Integer getUsersId() {
		return usersId;
	}

	public void setUsersId(Integer usersId) {
		this.usersId = usersId;
	}

}
