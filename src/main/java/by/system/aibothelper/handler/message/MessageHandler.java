package by.system.aibothelper.handler.message;

import by.system.aibothelper.dto.Message;
import by.system.aibothelper.enums.UserState;

public interface MessageHandler {
    boolean supports(UserState state);
    void handle(Message message);
}
