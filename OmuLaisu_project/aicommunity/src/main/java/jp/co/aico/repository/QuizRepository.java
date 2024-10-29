package jp.co.aico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.aico.entity.QuizEntity;

public interface QuizRepository extends JpaRepository<QuizEntity,Integer > {

}
