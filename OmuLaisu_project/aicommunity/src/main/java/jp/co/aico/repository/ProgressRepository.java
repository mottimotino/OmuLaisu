package jp.co.aico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.aico.entity.CategoriesEntity;
import jp.co.aico.entity.ProgressEntity;
import jp.co.aico.entity.UsersEntity;

public interface ProgressRepository extends JpaRepository<ProgressEntity, Integer>{
		
	@Query("SELECT COUNT(p) FROM ProgressEntity p WHERE p.usersEntity = :usersEntity AND p.categoriesEntity = :categoriesEntity")
	public Integer findByUsersEntityAndCategoriesEntity(@Param("usersEntity") UsersEntity usersEntity,@Param("categoriesEntity") CategoriesEntity categoriesEntity);
	
	@Query("SELECT COUNT(p) FROM ProgressEntity p WHERE p.usersEntity = :usersEntity AND p.missFlag = :missFlag AND p.categoriesEntity = :categoriesEntity")
	public Integer findByUsersEntityAndMissFlagAndCategoriesEntity(@Param("usersEntity") UsersEntity usersEntity,@Param("missFlag") Integer missFlag,@Param("categoriesEntity") CategoriesEntity categoriesEntity);

}
