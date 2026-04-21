package by.system.aibothelper.client.tg;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@Slf4j
public class TelegramClientImpl implements TelegramClient {
    private final RestTemplate restTemplate;
    private final String botToken;

    public TelegramClientImpl(@Value("${telegram.bot.token}") String botToken) {
        this.botToken = botToken;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void sendMessage(Long chatId, String text) {
        String url = "https://api.telegram.org/bot" + this.botToken + "/sendMessage";
        Map<String, Object> request = Map.of(
                "chat_id", chatId,
                "text", text,
                "parse_mode", "Markdown"
        );

        this.restTemplate.postForObject(url, request, String.class);
    }

    @Override
    public void sendChatAction(Long chatId, String action) {
        String url = "https://api.telegram.org/bot" + this.botToken + "/sendChatAction";
        Map<String, Object> request = Map.of(
                "chat_id", chatId,
                "action", action
        );

        this.restTemplate.postForObject(url, request, String.class);
    }
}