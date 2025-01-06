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
	
	//category_idの最小値と最大値を指定して検索
	@Query("SELECT COUNT(p) FROM ProgressEntity p WHERE p.usersEntity = :usersEntity AND :category_min <= p.categoriesEntity AND p.categoriesEntity <= :category_max")
	public Integer findByUsersEntityAndCategoriesEntity2(@Param("usersEntity") UsersEntity usersEntity,@Param("category_min") CategoriesEntity category_min,@Param("category_max") CategoriesEntity category_max);
	//category_idの最小値と最大値を指定して検索
	@Query("SELECT COUNT(p) FROM ProgressEntity p WHERE p.usersEntity = :usersEntity AND p.missFlag = :missFlag AND :category_min <= p.categoriesEntity AND p.categoriesEntity <= :category_max")
	public Integer findByUsersEntityAndMissFlagAndCategoriesEntity2(@Param("usersEntity") UsersEntity usersEntity,@Param("missFlag") Integer missFlag,@Param("category_min") CategoriesEntity category_min,@Param("category_max") CategoriesEntity category_max);


}
