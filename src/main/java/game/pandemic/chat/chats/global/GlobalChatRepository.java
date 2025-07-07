package game.pandemic.chat.chats.global;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GlobalChatRepository extends JpaRepository<GlobalChat, Long> {
    boolean existsBy();
    Optional<GlobalChat> findTopBy();
}
