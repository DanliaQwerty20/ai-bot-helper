package by.system.aibothelper.controller;

import by.system.aibothelper.dto.TelegramUpdateDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Telegram Bot", description = "Webhook для Telegram")
@RequestMapping("/api/v1/telegram")
public interface TelegramWebhookController {

    @PostMapping("/webhook")
    ResponseEntity<Void> onUpdate(@RequestBody @Valid TelegramUpdateDto update);
}