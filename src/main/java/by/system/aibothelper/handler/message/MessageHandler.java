package by.system.aibothelper.handler.message;

import org.telegram.telegrambots.meta.api.objects.Message;
import by.system.aibothelper.enums.UserState;

public interface MessageHandler {

    boolean supports(UserState userState);

    default boolean supports(UserState userState, Message message) {
        if (!supports(userState)) return false;

        if (userState == UserState.WAITING_FOR_KNOWLEDGE_FILE) {
            return message.hasDocument();
        }

        if (userState == UserState.WAITING_FOR_LEGEND) {
            return message.hasText();
        }

        return false;
    }

    void handle(Message message);
}