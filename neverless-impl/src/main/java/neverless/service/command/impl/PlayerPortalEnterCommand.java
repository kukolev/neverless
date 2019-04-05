package neverless.service.command.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.service.command.AbstractCommand;
import neverless.context.EventContext;
import neverless.domain.entity.behavior.BehaviorState;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.portal.AbstractPortal;

@Data
@Accessors(chain = true)
public class PlayerPortalEnterCommand extends AbstractCommand {

    private Player player;
    private EventContext eventContext;
    private AbstractPortal portal;

    @Override
    public BehaviorState execute() {
        player
                .setLocation(portal.getDestination())
                .setX(portal.getDestX())
                .setY(portal.getDestY());
        eventContext.addPortalEnterEvent(player.getUniqueName(), portal.getDestination().getTitle());
        return BehaviorState.MOVE;
    }

    @Override
    public boolean checkFinished() {
        return false;
    }
}
