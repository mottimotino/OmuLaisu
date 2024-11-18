package jp.co.aico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.aico.entity.CategoriesEntity;
import jp.co.aico.entity.ProgressEntity;
import jp.co.aico.entity.UsersEntity;

public interface ProgressRepository extends JpaRepository<ProgressEntity, Integer>{
	
	List<ProgressEntity> findByUsersEntityAndCategoriesEntity(UsersEntity usersEntity,CategoriesEntity categoriesEntity);
	
	List<ProgressEntity> findByUsersEntityAndMissFlagAndCategoriesEntity(UsersEntity usersEntity,Integer missFlag,CategoriesEntity categoriesEntity);

}
