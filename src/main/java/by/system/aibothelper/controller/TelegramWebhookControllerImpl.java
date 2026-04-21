package by.system.aibothelper.controller;

import by.system.aibothelper.dto.TelegramUpdateDto;
import by.system.aibothelper.service.facade.tg.TelegramFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TelegramWebhookControllerImpl implements TelegramWebhookController {

    private final TelegramFacade facade;

    @Override
    public ResponseEntity<Void> onUpdate(TelegramUpdateDto update) {
        facade.process(update);
        return ResponseEntity.ok().build();
    }
}