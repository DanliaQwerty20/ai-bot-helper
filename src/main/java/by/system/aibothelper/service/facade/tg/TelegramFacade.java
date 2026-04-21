package by.system.aibothelper.service.facade.tg;

import by.system.aibothelper.dto.TelegramUpdateDto;

public interface TelegramFacade {
    void process(TelegramUpdateDto update);
}
