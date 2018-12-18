package neverless.util;

import org.springframework.stereotype.Component;

import static neverless.Constants.CURRENT_SESSION_ID;

@Component
public class SessionUtil {

    /**
     * Returns identifier of the current session.
     * In server mode should return session id related to concrete authorized game instance for authorized user.
     */
    public String getGameId() {
        // todo: instead constant we should return id of player has been logon.
        return CURRENT_SESSION_ID;
    }
}