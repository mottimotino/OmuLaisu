package jp.co.aico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jp.co.aico.entity.CategoriesEntity;
import jp.co.aico.entity.QuizEntity;

public interface QuizRepository extends JpaRepository<QuizEntity,Integer > {
	
	List<QuizEntity> findByCategoriesEntity(CategoriesEntity categories);
	
	QuizEntity findByQueIdAndCategoriesEntity(Integer queId,CategoriesEntity categories);
	
	List<QuizEntity> findByCategoriesEntityContaining(CategoriesEntity categories);
	
	@Query("SELECT COUNT(q) FROM QuizEntity q")
	public Integer findAllCount();

}
