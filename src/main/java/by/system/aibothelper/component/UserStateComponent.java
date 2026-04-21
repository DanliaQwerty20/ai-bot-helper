package by.system.aibothelper.component;

import by.system.aibothelper.enums.UserState;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class UserStateComponent {

    Map<Long, UserState> states = new ConcurrentHashMap<>();

    public UserState getState(Long chatId) {
        return states.getOrDefault(chatId, UserState.IDLE);
    }

    public void setState(Long chatId, UserState state) {
        states.put(chatId, state);
    }

    public void clear(Long chatId) {
        states.remove(chatId);
    }
}
