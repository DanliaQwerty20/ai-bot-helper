package by.system.aibothelper.handler.common;

import by.system.aibothelper.dto.Message;

public interface CommandHandler {
    boolean supports(String command);

    void handle(Message message);
}
