package by.system.aibothelper.handler.message.impl;

import by.system.aibothelper.client.tg.TelegramClient;
import by.system.aibothelper.component.UserStateComponent;
import by.system.aibothelper.enums.UserState;
import by.system.aibothelper.handler.message.MessageHandler;
import by.system.aibothelper.service.knowledge.KnowledgeBaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
@RequiredArgsConstructor
public class KnowledgeFileHandler implements MessageHandler {

    private final TelegramClient telegramClient;
    private final UserStateComponent state;
    private final KnowledgeBaseService knowledgeService;

    @Override
    public boolean supports(UserState userState) {
        return userState == UserState.WAITING_FOR_KNOWLEDGE_FILE;
    }

    @Override
    public void handle(Message message) {
        var chatId = message.getChatId();

        try {
            var document = message.getDocument();
            var fileName = document.getFileName();

            if (!fileName.toLowerCase().endsWith(".pdf")) {
                telegramClient.sendMessage(chatId, "❌ Поддерживаются только PDF файлы");
                return;
            }

            if (document.getFileSize() > 20 * 1024 * 1024) {
                telegramClient.sendMessage(chatId, "❌ Файл слишком большой. Максимум 20 MB");
                return;
            }

            telegramClient.sendMessage(chatId, "⏳ Обрабатываю PDF...");

            byte[] fileContent = telegramClient.downloadFile(document.getFileId());
            knowledgeService.processPdf(fileContent, fileName);

            telegramClient.sendMessage(chatId, "✅ PDF обработан и добавлен в базу знаний!");

        } catch (Exception e) {
            log.error("Failed to process knowledge file", e);
            telegramClient.sendMessage(chatId, "❌ Ошибка: " + e.getMessage());
        } finally {
            state.clear(chatId);
        }
    }
}