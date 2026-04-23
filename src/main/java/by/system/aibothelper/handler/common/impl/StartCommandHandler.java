package by.system.aibothelper.handler.common.impl;

import by.system.aibothelper.client.tg.TelegramClient;
import by.system.aibothelper.handler.common.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class StartCommandHandler implements CommandHandler {
    private final TelegramClient telegramClient;

    @Override
    public boolean supports(String command) {
        return "/start".equals(command);
    }

    @Override
    public void handle(Message message) {
        telegramClient.sendMessage(message.getChatId(),
                "👋 Привет!\n\n" +
                        "/review - отправить легенду на ревью\n" +
                        "/upload_knowledge - загрузить PDF с эталонами\n" +
                        "/cancel - отменить текущее действие");
    }
}