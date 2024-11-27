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
	private PrizeEntity prizeEntity;
	
	@ManyToOne
	@JoinColumn(name = "users_id", referencedColumnName = "usersId")
	private UsersEntity usersEntity;

	public Integer getManaId() {
		return manaId;
	}

	public void setManaId(Integer manaId) {
		this.manaId = manaId;
	}

	public PrizeEntity getPrizeEntity() {
		return prizeEntity;
	}

	public void setPrizeEntity(PrizeEntity prizeEntity) {
		this.prizeEntity = prizeEntity;
	}

	public UsersEntity getUsersEntity() {
		return usersEntity;
	}

	public void setUsersEntity(UsersEntity usersEntity) {
		this.usersEntity = usersEntity;
	}

}