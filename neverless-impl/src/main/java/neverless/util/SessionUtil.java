package neverless.util;

import neverless.domain.entity.GameObjectId;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static neverless.Constants.CURRENT_SESSION_ID;

@Component
public class SessionUtil {

    public String getCurrentSessionId() {
        // todo: instead constant we should return id of player has been logon.
        return CURRENT_SESSION_ID;
    }

    public GameObjectId createId(String uniqueName) {
        return new GameObjectId(uniqueName, getCurrentSessionId());
    }

    public GameObjectId createId() {
        return new GameObjectId(UUID.randomUUID().toString(), getCurrentSessionId());
    }

}
