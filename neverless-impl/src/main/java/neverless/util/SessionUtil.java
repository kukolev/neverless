package neverless.util;

import neverless.domain.entity.GameObjectId;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static neverless.Constants.CURRENT_SESSION_ID;

@Component
public class SessionUtil {

    /**
     * Returns identifier of the current session.
     * In server mode should return session id related to concrete authorized game instance for authorized user.
     */
    public String getCurrentSessionId() {
        // todo: instead constant we should return id of player has been logon.
        return CURRENT_SESSION_ID;
    }

    /**
     * Creates and returns GameObjectId for some unique name.
     *
     * @param uniqueName    unique name of entity which id should be returned.
     */
    public GameObjectId createId(String uniqueName) {
        return new GameObjectId(uniqueName, getCurrentSessionId());
    }

    /**
     * Creates and returns GameObjectId with randomly generated unique name.
     */
    public GameObjectId createId() {
        return new GameObjectId(UUID.randomUUID().toString(), getCurrentSessionId());
    }
}