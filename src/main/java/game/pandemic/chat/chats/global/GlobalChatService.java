package game.pandemic.chat.chats.global;

import game.pandemic.chat.ChatMessageSender;
import game.pandemic.user.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GlobalChatService {
    private final GlobalChatRepository globalChatRepository;
    private final UserRepository userRepository;

    @PostConstruct
    private void postConstruct() {
        createAndSaveGlobalChat();
    }

    private void createAndSaveGlobalChat() {
        if (!this.globalChatRepository.existsBy()) {
            this.globalChatRepository.save(createGlobalChat());
        }
    }

    private GlobalChat createGlobalChat() {
        return new GlobalChat(new HashSet<>(this.userRepository.findAll()));
    }

    @Transactional
    public void addMember(final ChatMessageSender member) {
        final Optional<GlobalChat> globalChatOptional = this.globalChatRepository.findTopBy();
        globalChatOptional.ifPresent(globalChat -> {
            globalChat.addMember(member);
            this.globalChatRepository.save(globalChat);
        });
    }
}
