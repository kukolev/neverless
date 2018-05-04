package neverless.domain.game.mapobject;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.game.AbstractGameObject;

@Data
@Accessors(chain = true)
public abstract class AbstractMapObject extends AbstractGameObject {

     private int x;
     private int y;
     private int width = 1;
     private int height = 1;
     private int zOrder = 1;
     private String signature;
}