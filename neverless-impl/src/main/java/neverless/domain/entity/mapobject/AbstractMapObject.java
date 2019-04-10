package neverless.domain.entity.mapobject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import neverless.Direction;
import neverless.PlatformShape;
import neverless.service.command.AbstractCommand;
import neverless.domain.entity.behavior.Behavior;
import neverless.domain.entity.Location;
import neverless.domain.entity.AbstractGameObject;
import neverless.MapObjectMetaType;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractMapObject extends AbstractGameObject {

    private Integer x;
    private Integer y;
    private Integer height = 32;
    private Integer width = 32;
    private Location location;
    private Direction direction = Direction.NORTH;
    private Behavior behavior = new Behavior();
    private AbstractCommand command;

    /**
     * Returns default value for
     */
    public PlatformShape getPlatformShape() {
        return PlatformShape.ELLIPSE;
    }
    private List<Coordinate> platformCoordinates = new ArrayList<>();

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

    /**
     * Returns meta-type of object. Meta-type describe main behaviorState of the object from Player perspective.
     */
    public abstract MapObjectMetaType getMetaType();

    /**
     * Returns true if terrain canProcessObject passable.
     *
     * @return true/false.
     */
    public boolean isPassable() {
        return false;
    }
}