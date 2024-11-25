package jp.co.aico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.aico.entity.CategoriesEntity;

public interface CategoriesRepository extends JpaRepository<CategoriesEntity, Integer>  {
	
	CategoriesEntity findByCategoryName(String categoryName);
	
	List<CategoriesEntity> findByCategoryNameContaining(String categoryName);
	

}