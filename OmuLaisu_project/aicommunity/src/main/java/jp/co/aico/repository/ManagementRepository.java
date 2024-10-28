package jp.co.aico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.aico.entity.ManagementEntity;

public interface ManagementRepository extends JpaRepository<ManagementEntity,Integer>{

}
