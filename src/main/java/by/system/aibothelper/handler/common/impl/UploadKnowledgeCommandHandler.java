package by.system.aibothelper.handler.common.impl;

import by.system.aibothelper.client.tg.TelegramClient;
import by.system.aibothelper.component.UserStateComponent;
import by.system.aibothelper.enums.UserState;
import by.system.aibothelper.handler.common.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class UploadKnowledgeCommandHandler implements CommandHandler {
    private final TelegramClient telegramClient;
    private final UserStateComponent state;

    @Override
    public boolean supports(String command) {
        return "/upload_knowledge".equals(command) || "/uk".equals(command);
    }

    @Override
    public void handle(Message message) {
        var chatId = message.getChatId();
        state.setState(chatId, UserState.WAITING_FOR_KNOWLEDGE_FILE);
        telegramClient.sendMessage(chatId, """
                📚 Загрузи PDF с эталонными легендами
                
                🔹 Файл должен быть в формате PDF
                🔹 Максимальный размер: 20 MB
                
                Отправь файл одним сообщением
                """);
    }
}