package by.system.aibothelper.service.facade.tg;

import by.system.aibothelper.client.tg.TelegramClient;
import by.system.aibothelper.common.UpdateDispatcher;
import by.system.aibothelper.dto.TelegramUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramFacadeImpl implements TelegramFacade {

    private final UpdateDispatcher dispatcher;
    private final TelegramClient telegramClient;

    @Async
    @Override
    public void process(TelegramUpdateDto update) {
        if (update.message() == null) {
            return;
        }

        var message = update.message();
        var chatId = message.getChatId();
        var text = message.getText();

        if (text != null && text.startsWith("/")) {
            telegramClient.sendChatAction(chatId, "typing");
        }

        if (message.hasDocument()) {
            telegramClient.sendChatAction(chatId, "upload_document");
        }

        dispatcher.dispatch(update);
    }
}
