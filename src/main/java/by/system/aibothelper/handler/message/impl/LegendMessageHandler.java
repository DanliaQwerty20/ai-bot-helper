package by.system.aibothelper.handler.message.impl;

import by.system.aibothelper.client.tg.TelegramClient;
import by.system.aibothelper.component.UserStateComponent;
import by.system.aibothelper.dto.LegendReviewResult;
import by.system.aibothelper.dto.Message;
import by.system.aibothelper.enums.UserState;
import by.system.aibothelper.handler.message.MessageHandler;
import by.system.aibothelper.service.legend.LegendPipelineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

        var chatId = message.chat().id();
        var text = message.text();

        try {
            telegramClient.sendChatAction(chatId, "typing");

            var result = pipeline.process(text);

            telegramClient.sendMessage(chatId, format(result));

        } catch (Exception e) {
            log.error("LegendMessageHandler failed", e);

            telegramClient.sendMessage(chatId,
                    "⚠️ Ошибка обработки: " + e.getClass().getSimpleName());
        } finally {
            state.clear(chatId);
        }
    }

    private String format(LegendReviewResult r) {
        return """
                📊 %d/10
                
                🔍 Проблемы:
                %s
                
                💡 Улучшения:
                %s
                
                ✍️ Версия:
                %s
                """.formatted(
                r.score(),
                safeJoin(r.problems()),
                safeJoin(r.improvements()),
                r.rewritten()
        );
    }

    private String safeJoin(java.util.List<String> list) {
        if (list == null || list.isEmpty()) {
            return "-";
        }
        return String.join("\n", list);
    }
}
