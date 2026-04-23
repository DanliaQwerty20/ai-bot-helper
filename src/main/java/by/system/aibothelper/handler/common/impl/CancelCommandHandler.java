package by.system.aibothelper.handler.common.impl;

import by.system.aibothelper.client.tg.TelegramClient;
import by.system.aibothelper.component.UserStateComponent;
import by.system.aibothelper.handler.common.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class CancelCommandHandler implements CommandHandler {
    private final TelegramClient telegramClient;
    private final UserStateComponent state;

    @Override
    public boolean supports(String command) {
        return "/cancel".equals(command);
    }

    @Override
    public void handle(Message message) {
        var chatId = message.getChatId();
        state.clear(chatId);
        telegramClient.sendMessage(chatId, "❌ Действие отменено");
    }
}