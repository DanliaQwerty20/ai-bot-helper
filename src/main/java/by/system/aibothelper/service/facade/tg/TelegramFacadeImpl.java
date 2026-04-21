package by.system.aibothelper.service.facade.tg;

import by.system.aibothelper.common.UpdateDispatcher;
import by.system.aibothelper.dto.TelegramUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TelegramFacadeImpl implements TelegramFacade {

    private final UpdateDispatcher dispatcher;

    @Async
    @Override
    public void process(TelegramUpdateDto update) {
        if (update.message() != null) {
            dispatcher.dispatch(update.message());
        }
    }
}
