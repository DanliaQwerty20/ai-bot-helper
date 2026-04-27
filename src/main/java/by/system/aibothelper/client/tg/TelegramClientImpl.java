package by.system.aibothelper.client.tg;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                "text", text
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

    @Override
    public byte[] downloadFile(String fileId) {
        try {
            String getFileUrl = "https://api.telegram.org/bot" + botToken + "/getFile";
            Map<String, Object> request = Map.of("file_id", fileId);

            ResponseEntity<Map> response = restTemplate.postForEntity(getFileUrl, request, Map.class);
            Map<String, Object> body = response.getBody();

            if (body == null || !(boolean) body.getOrDefault("ok", false)) {
                throw new RuntimeException("Failed to get file info: " + body);
            }

            Map<String, Object> result = (Map<String, Object>) body.get("result");
            String filePath = (String) result.get("file_path");

            String downloadUrl = "https://api.telegram.org/file/bot" + botToken + "/" + filePath;

            ResponseEntity<byte[]> fileResponse = restTemplate.getForEntity(downloadUrl, byte[].class);

            if (fileResponse.getStatusCode() == HttpStatus.OK && fileResponse.getBody() != null) {
                return fileResponse.getBody();
            }

            throw new RuntimeException("Failed to download file, status: " + fileResponse.getStatusCode());

        } catch (Exception e) {
            log.error("Failed to download file with id: {}", fileId, e);
            throw new RuntimeException("Failed to download file: " + e.getMessage());
        }
    }
}