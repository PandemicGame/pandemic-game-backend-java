package game.pandemic.chat;

import game.pandemic.chat.chats.Chat;
import game.pandemic.chat.chats.ChatRepository;
import game.pandemic.chat.chats.global.GlobalChatService;
import game.pandemic.chat.message.ChatMessage;
import game.pandemic.chat.message.ChatMessageRepository;
import game.pandemic.jackson.JacksonView;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageSenderMessenger chatMessageSenderMessenger;
    private final ChatRepository chatRepository;
    private final GlobalChatService globalChatService;

    @Transactional
    public void createChatMessage(final ChatMessageSender sender, final ChatMessage chatMessage) {
        chatMessage.setSender(sender);

        executeIfChatExistsAndEntityIsMember(sender, chatMessage.getChatId(), this.chatRepository::findById, (m, c) -> {
            chatMessage.setChat(c);

            final ChatMessage saved = this.chatMessageRepository.save(chatMessage);

            sendChatMessageToChatMembers(c, saved);
        });
    }

    private void sendChatMessageToChatMembers(final Chat chat, final ChatMessage chatMessage) {
        this.chatMessageSenderMessenger.multicast(chat.getMembers(), chatMessage, JacksonView.Read.class);
    }

    @Transactional
    public void sendChatWithIdToChatMember(final ChatMessageSender chatMember, final String chatId) {
        executeIfChatExistsAndEntityIsMember(chatMember, chatId, this::findChatByIdOrGlobalChat, this::sendChatToMember);
    }

    private Optional<? extends Chat> findChatByIdOrGlobalChat(final String chatId) {
        final boolean isChatIdNumeric = NumberUtils.isCreatable(chatId);
        if (isChatIdNumeric) {
            final Long id = Long.parseLong(chatId);
            return this.chatRepository.findById(id);
        } else if (this.globalChatService.isGlobalChatIdentifier(chatId)) {
            return this.globalChatService.findGlobalChat();
        }
        return Optional.empty();
    }

    private void sendChatToMember(final ChatMessageSender chatMember, final Chat chat) {
        this.chatMessageSenderMessenger.unicast(chatMember, chat, JacksonView.Read.class);
    }

    private <I, C extends Chat> void executeIfChatExistsAndEntityIsMember(final ChatMessageSender entity,
                                                                          final I chatId,
                                                                          final Function<I, Optional<C>> chatOptionalFetcher,
                                                                          final BiConsumer<ChatMessageSender, C> callback) {
        final Optional<C> chatOptional = chatOptionalFetcher.apply(chatId);
        if (chatOptional.isEmpty()) {
            log.warn("No chat found for id: " + chatId);
            return;
        }

        final C chat = chatOptional.get();
        if (!chat.containsMember(entity)) {
            log.warn("Sender with id " + entity.getId() + " is not a member of chat with id " + chatId + ".");
            return;
        }

        callback.accept(entity, chat);
    }
}
