package game.pandemic.chat.message;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import game.pandemic.chat.ChatMessageSender;
import game.pandemic.chat.chats.Chat;
import game.pandemic.jackson.JacksonView;
import game.pandemic.websocket.IWebSocketData;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ChatMessage implements IWebSocketData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @JsonView(JacksonView.Read.class)
    private Long id;
    @Setter
    @ManyToOne
    @JsonView(JacksonView.Read.class)
    private ChatMessageSender sender;
    @Getter
    @Setter
    @ManyToOne
    @JsonView(JacksonView.Read.class)
    @JsonIdentityReference(alwaysAsId = true)
    private Chat chat;
    @Getter
    @Transient
    @JsonView(JacksonView.Create.class)
    private Long chatId;
    @JsonView(JacksonView.Any.class)
    @NotBlank
    private String message;
    @JsonView(JacksonView.Read.class)
    private ZonedDateTime createdAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = ZonedDateTime.now();
    }
}
