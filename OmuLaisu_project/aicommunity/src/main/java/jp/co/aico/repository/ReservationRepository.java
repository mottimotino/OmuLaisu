package jp.co.aico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.aico.entity.ReservationEntity;


public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {

}
