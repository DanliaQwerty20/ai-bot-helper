package by.system.aibothelper.common;

import by.system.aibothelper.dto.Message;
import by.system.aibothelper.component.UserStateComponent;
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

    public void dispatch(Message message) {

        var text = message.text();
        var chatId = message.chat().id();

        if (text == null) return;

        // 1. COMMANDS
        if (text.startsWith("/")) {
            commandHandlers.stream()
                    .filter(h -> h.supports(text))
                    .findFirst()
                    .ifPresent(h -> h.handle(message));
            return;
        }

        var state = stateComponent.getState(chatId);

        messageHandlers.stream()
                .filter(h -> h.supports(state))
                .findFirst()
                .ifPresent(h -> h.handle(message));
    }
}
