package game.pandemic.chat.chats;

import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.chat.ChatMessageSender;
import game.pandemic.chat.message.ChatMessage;
import game.pandemic.jackson.JacksonView;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Chat implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(JacksonView.Read.class)
    protected Long id;
    @ManyToMany
    protected Set<ChatMessageSender> members;
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "createdAt")
    protected Set<ChatMessage> messages;

    protected Chat(final Set<ChatMessageSender> members) {
        this.members = members;
    }

    public boolean containsMember(final ChatMessageSender member) {
        return this.members.contains(member);
    }

    public void addMember(final ChatMessageSender member) {
        this.members.add(member);
    }
}
