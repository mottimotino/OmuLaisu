package jp.co.aico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.aico.entity.ReservationDateEntity;

public interface ReservationDateRepository extends JpaRepository<ReservationDateEntity, Integer> {

}
