package by.system.aibothelper.handler.common.impl;

import by.system.aibothelper.client.tg.TelegramClient;
import by.system.aibothelper.component.UserStateComponent;
import by.system.aibothelper.dto.Message;
import by.system.aibothelper.enums.UserState;
import by.system.aibothelper.handler.common.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewCommandHandler implements CommandHandler {

    private final TelegramClient telegramClient;
    private final UserStateComponent state;

    @Override
    public boolean supports(String command) {
        return "/review".equals(command);
    }

    @Override
    public void handle(Message message) {
        var chatId = message.chat().id();

        state.setState(chatId, UserState.WAITING_FOR_LEGEND);

        telegramClient.sendMessage(chatId,
                "📝 Отправь свою легенду");
    }
}