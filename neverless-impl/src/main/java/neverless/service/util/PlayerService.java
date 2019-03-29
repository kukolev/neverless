package neverless.service.util;

import neverless.command.Command;
import neverless.command.CommandType;
import neverless.domain.entity.mapobject.Player;
import neverless.repository.cache.GameCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private GameCache gameCache;

    public void setCommand(Command command) {
        if (command.getCommandType() != CommandType.PLAYER_CONTINUE) {
            Player player = gameCache.getPlayer();
            player.setCommand(command);
        }
    }
}
