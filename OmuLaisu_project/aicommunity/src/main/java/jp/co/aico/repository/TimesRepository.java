package jp.co.aico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.aico.entity.TimesEntity;


public interface TimesRepository extends JpaRepository<TimesEntity, Integer> {

}
