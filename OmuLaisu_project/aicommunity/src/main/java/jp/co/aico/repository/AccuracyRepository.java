package jp.co.aico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.aico.entity.AccuracyEntity;
import jp.co.aico.entity.CategoriesEntity;

public interface AccuracyRepository extends JpaRepository<AccuracyEntity, Integer>{
	
	AccuracyEntity findByCategoriesEntity(CategoriesEntity categoriesEntity);

}
