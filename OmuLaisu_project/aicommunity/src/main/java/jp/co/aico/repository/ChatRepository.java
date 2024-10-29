package jp.co.aico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.aico.entity.ChatEntity;

public interface ChatRepository extends JpaRepository<ChatEntity, Integer> {

}
