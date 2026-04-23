package by.system.aibothelper.handler.message.impl;

import by.system.aibothelper.client.tg.TelegramClient;
import by.system.aibothelper.component.UserStateComponent;
import by.system.aibothelper.dto.LegendReviewResult;
import by.system.aibothelper.enums.UserState;
import by.system.aibothelper.handler.message.MessageHandler;
import by.system.aibothelper.service.legend.LegendPipelineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
@RequiredArgsConstructor
public class LegendMessageHandler implements MessageHandler {

    private final TelegramClient telegramClient;
    private final LegendPipelineService pipeline;
    private final UserStateComponent state;

    @Override
    public boolean supports(UserState userState) {
        return userState == UserState.WAITING_FOR_LEGEND;
    }

    @Override
    public void handle(Message message) {
        var chatId = message.getChatId();
        var text = message.getText();

        try {
            telegramClient.sendChatAction(chatId, "typing");
            var result = pipeline.process(text);
            telegramClient.sendMessage(chatId, format(result));
        } catch (Exception e) {
            log.error("LegendMessageHandler failed", e);
            telegramClient.sendMessage(chatId, "⚠️ Ошибка: " + e.getMessage());
        } finally {
            state.clear(chatId);
        }
    }

    private String format(LegendReviewResult r) {
        return """
                📊 **Оценка: %d/10**
                
                🔍 **Проблемы:**
                %s
                
                💡 **Улучшения:**
                %s
                
                ✍️ **Улучшенная версия:**
                %s
                """.formatted(
                r.score(),
                safeJoin(r.problems()),
                safeJoin(r.improvements()),
                r.rewritten()
        );
    }

    private String safeJoin(java.util.List<String> list) {
        if (list == null || list.isEmpty()) return "-";
        return String.join("\n", list);
    }
}