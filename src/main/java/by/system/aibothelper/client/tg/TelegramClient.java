package by.system.aibothelper.client.tg;

public interface TelegramClient {
    void sendMessage(Long chatId, String text);

    void sendChatAction(Long chatId, String action);
}
