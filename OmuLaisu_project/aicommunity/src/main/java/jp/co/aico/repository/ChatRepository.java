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
	/*一般ユーザー用(自分の情報のみで検索)*/
	@Query("SELECT c FROM ChatEntity c WHERE c.sendUsersEntity = :usersEntity OR c.receUsersEntity = :usersEntity ORDER BY c.chatId ASC")
	List<ChatEntity> findByUsersEntity(@Param("usersEntity") UsersEntity usersEntity);
	/*メンター用(自分と相手の情報で検索)*/
	@Query("SELECT c FROM ChatEntity c WHERE c.sendUsersEntity = :loginUsersEntity AND c.receUsersEntity = :usersEntity OR c.receUsersEntity = :loginUsersEntity AND c.sendUsersEntity = :usersEntity ORDER BY c.chatId ASC")
	List<ChatEntity> findByUsersEntityAndUsersEntity(@Param("loginUsersEntity") UsersEntity loginUsersEntity,@Param("usersEntity") UsersEntity usersEntity);


}
