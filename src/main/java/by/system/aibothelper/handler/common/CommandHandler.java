package by.system.aibothelper.handler.common;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface CommandHandler {
    boolean supports(String command);
    void handle(Message message);
}