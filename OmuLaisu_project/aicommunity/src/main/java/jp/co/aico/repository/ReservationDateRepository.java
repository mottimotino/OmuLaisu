package jp.co.aico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.aico.entity.ReservationDateEntity;
import jp.co.aico.entity.UsersEntity;

public interface ReservationDateRepository extends JpaRepository<ReservationDateEntity, Integer> {
List<ReservationDateEntity> findByUsersEntity(UsersEntity usersEntity);
}
