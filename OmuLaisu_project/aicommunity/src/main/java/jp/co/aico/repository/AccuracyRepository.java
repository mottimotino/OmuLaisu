package jp.co.aico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.aico.entity.AccuracyEntity;
import jp.co.aico.entity.CategoriesEntity;
import jp.co.aico.entity.UsersEntity;

public interface AccuracyRepository extends JpaRepository<AccuracyEntity, Integer>{
	
	AccuracyEntity findByCategoriesEntityAndUsersEntity(CategoriesEntity categoriesEntity,UsersEntity usersEntity);
	
	List<AccuracyEntity> findByUsersEntity(UsersEntity usersEntity);
	
//	@Query("SELECT a FROM AccuracyEntity a WHERE a.categoriesEntity <= :categoriesEntity AND a.usersEntity = :usersEntity")
//	public List<AccuracyEntity> findByCategoriesEntityAndUsersEntityQuery(@Param("categoriesEntity") CategoriesEntity categoriesEntity,@Param("usersEntity") UsersEntity usersEntity);

}
