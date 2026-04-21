package by.system.aibothelper.handler.common.impl;

import by.system.aibothelper.client.tg.TelegramClient;
import by.system.aibothelper.dto.Message;
import by.system.aibothelper.handler.common.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
        telegramClient.sendMessage(
                message.chat().id(),
                "👋 Привет! Используй /review чтобы начать."
        );
    }
}