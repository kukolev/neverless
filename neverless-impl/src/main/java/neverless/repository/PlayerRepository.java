package neverless.repository;

import neverless.domain.mapobject.Player;
import neverless.repository.util.InjectionUtil;
import org.springframework.stereotype.Repository;

import static neverless.Constants.PLAYER_ID;

@Repository
public interface PlayerRepository extends AbstractRepository<Player>, InjectionUtil {
    default Player get() {
        return simpleGet(PLAYER_ID);
    }
}