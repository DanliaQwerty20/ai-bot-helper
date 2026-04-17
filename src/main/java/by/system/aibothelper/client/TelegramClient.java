package by.system.aibothelper.client;

import by.system.aibothelper.config.properties.TelegramProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Component
public class TelegramClient {

    RestTemplate restTemplate;
    TelegramProperties telegramProperties;

    public void sendMessage(Long chatId, String text) {

        var url = "https://api.telegram.org/bot%s/sendMessage"
                .formatted(telegramProperties.getToken());

        var body = Map.of(
                "chat_id", chatId,
                "text", text
        );

        this.restTemplate.postForObject(url, body, String.class);
    }
}
