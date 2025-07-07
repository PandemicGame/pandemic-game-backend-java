package game.pandemic.chat.chats.global;

import game.pandemic.chat.ChatMessageSender;
import game.pandemic.chat.chats.Chat;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GlobalChat extends Chat {
    public GlobalChat(final Set<ChatMessageSender> members) {
        super(members);
    }
}
