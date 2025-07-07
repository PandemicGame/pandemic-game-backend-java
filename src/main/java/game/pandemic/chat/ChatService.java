package game.pandemic.chat;

import game.pandemic.chat.chats.Chat;
import game.pandemic.chat.chats.ChatRepository;
import game.pandemic.chat.message.ChatMessage;
import game.pandemic.chat.message.ChatMessageRepository;
import game.pandemic.jackson.JacksonView;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageSenderMessenger chatMessageSenderMessenger;
    private final ChatRepository chatRepository;

    @Transactional
    public void createChatMessage(final ChatMessageSender sender, final ChatMessage chatMessage) {
        chatMessage.setSender(sender);

        final Long chatId = chatMessage.getChatId();
        final Optional<Chat> chatOptional = this.chatRepository.findById(chatId);
        if (chatOptional.isEmpty()) {
            log.warn("No chat found for id: " + chatId);
            return;
        }

        final Chat chat = chatOptional.get();
        if (!chat.containsMember(sender)) {
            log.warn("Sender with id " + sender.getId() + " is not a member of chat with id " + chatId + ".");
            return;
        }

        chatMessage.setChat(chat);

        final ChatMessage saved = this.chatMessageRepository.save(chatMessage);

        this.chatMessageSenderMessenger.multicast(chat.getMembers(), saved, JacksonView.Read.class);
    }
}
