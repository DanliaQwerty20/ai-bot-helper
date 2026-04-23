package by.system.aibothelper.common;

import by.system.aibothelper.component.UserStateComponent;
import by.system.aibothelper.dto.TelegramUpdateDto;
import by.system.aibothelper.handler.common.CommandHandler;
import by.system.aibothelper.handler.message.MessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateDispatcher {

    private final List<CommandHandler> commandHandlers;
    private final List<MessageHandler> messageHandlers;
    private final UserStateComponent stateComponent;

    public void dispatch(TelegramUpdateDto update) {
        var message = update.message();
        if (message == null) return;

        var chatId = message.getChatId();
        var text = message.getText();

        // 1. COMMANDS
        if (text != null && text.startsWith("/")) {
            commandHandlers.stream()
                    .filter(h -> h.supports(text))
                    .findFirst()
                    .ifPresent(h -> h.handle(message));
            return;
        }

        var state = stateComponent.getState(chatId);

        messageHandlers.stream()
                .filter(h -> h.supports(state, message))
                .findFirst()
                .ifPresent(h -> h.handle(message));
    }
}
