package by.system.aibothelper.dto;

import lombok.Builder;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record TelegramUpdateDto(
        @Valid Message message,
        @Valid Update update
) {
    public boolean hasDocument() {
        return message != null && message.hasDocument();
    }

    public Document getDocument() {
        return message != null ? message.getDocument() : null;
    }

    public Long getChatId() {
        return message != null && message.getChat() != null
                ? message.getChat().getId()
                : null;
    }

    public String getText() {
        return message != null ? message.getText() : null;
    }
}