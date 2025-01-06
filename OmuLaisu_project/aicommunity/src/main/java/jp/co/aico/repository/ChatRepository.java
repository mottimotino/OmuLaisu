package jp.co.aico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.aico.entity.ChatEntity;
import jp.co.aico.entity.UsersEntity;

public interface ChatRepository extends JpaRepository<ChatEntity, Integer> {
	
	List<ChatEntity> findBySendUsersEntity(UsersEntity sendUsersEntity);
	
	List<ChatEntity> findByReceUsersEntity(UsersEntity receUsersEntity);
	
	@Query("SELECT c FROM ChatEntity c WHERE c.sendUsersEntity = :usersEntity OR c.receUsersEntity = :usersEntity ORDER BY c.chatId ASC")
	List<ChatEntity> findByUsersEntity(@Param("usersEntity") UsersEntity usersEntity);


}
