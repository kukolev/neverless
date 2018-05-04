package neverless.domain.repository;

import neverless.domain.game.mapobject.Player;
import org.springframework.stereotype.Repository;

import static neverless.Constants.PLAYER_ID;

@Repository
public class PlayerRepository extends AbstractRepository<Player> {
    public Player get() {
        return super.get(PLAYER_ID);
    }
}