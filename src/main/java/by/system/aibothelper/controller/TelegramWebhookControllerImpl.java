package by.system.aibothelper.controller;

import by.system.aibothelper.dto.TelegramUpdateDto;
import by.system.aibothelper.handler.TelegramBotHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
public class TelegramWebhookControllerImpl implements TelegramWebhookController {

    TelegramBotHandler handler;

    @Override
    public ResponseEntity<Void> onUpdate(TelegramUpdateDto update) {
        this.handler.handle(update.message());
        return ResponseEntity.ok().build();
    }
}