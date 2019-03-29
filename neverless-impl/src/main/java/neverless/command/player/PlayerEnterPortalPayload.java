package neverless.command.player;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.command.CommandPayload;
import neverless.domain.entity.mapobject.portal.AbstractPortal;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PlayerEnterPortalPayload extends CommandPayload {
    private AbstractPortal portal;
}
