package jp.co.aico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.aico.entity.UsersEntity;

public interface UsersRepository extends JpaRepository<UsersEntity,Integer>{
	
	UsersEntity findByMailAndPassword(String mail,String password);
	
	UsersEntity findByMail(String mail);
	
	List<UsersEntity> findByAuthority(Integer authority);

}
