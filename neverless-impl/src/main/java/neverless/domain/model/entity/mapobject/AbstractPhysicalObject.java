package neverless.domain.model.entity.mapobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.util.Direction;
import neverless.util.PlatformShape;
import neverless.service.model.command.AbstractCommand;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractPhysicalObject extends AbstractMapObject {

    private Integer height = 32;
    private Integer width = 32;
    private Direction direction = Direction.NORTH;
    private AbstractCommand command;
    private Profile profile = new Profile();

    /**
     * Returns default value for
     */
    public PlatformShape getPlatformShape() {
        return PlatformShape.ELLIPSE;
    }

    /**
     * Returns default value for object platformWidth.
     */
    public int getPlatformWidth() {
        return width;
    }

    /**
     * Returns default value for object platformHeight.
     */
    public int getPlatformHeight() {
        // div by 3 just for beauty
        return getPlatformWidth() / 3;
    }

    /**
     * Returns identifier, specified for graphic render.
     */
    public abstract String getSignature();
}