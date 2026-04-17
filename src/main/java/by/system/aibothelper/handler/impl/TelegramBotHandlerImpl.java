package by.system.aibothelper.handler.impl;

import by.system.aibothelper.client.TelegramClient;
import by.system.aibothelper.dto.LegendResponseDto;
import by.system.aibothelper.dto.Message;
import by.system.aibothelper.handler.TelegramBotHandler;
import by.system.aibothelper.service.review.LegendService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Component
@Slf4j
public class TelegramBotHandlerImpl implements TelegramBotHandler {

    LegendService legendService;
    TelegramClient telegramClient;

    public void handle(Message message) {

        var chatId = message.chat().id();
        var text = message.text();

        log.info("Incoming message: {}", text);

        var response = this.legendService.review(text);

        this.telegramClient.sendMessage(chatId, this.format(response));
    }

    private String format(LegendResponseDto response) {
        return """
        🔍 Проблемы:
        %s

        💡 Улучшения:
        %s

        ✍️ Переписанная версия:
        %s
        """.formatted(
                String.join("\n", response.problems()),
                String.join("\n", response.improvements()),
                response.rewritten()
        );
    }
}

