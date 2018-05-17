package neverless.domain.mapobject;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.AbstractGameObject;
import neverless.repository.MapObjectsRepository;

@Data
@Accessors(chain = true)
public abstract class AbstractMapObject extends AbstractGameObject {

     private int x;
     private int y;
     private int width = 1;
     private int height = 1;
     private int zOrder = 1;
     private String signature;

     public final void register(MapObjectsRepository repository) {
          repository.save(this);
     }

     /**
      * Returns true if terrain is passable.
      *
      * @return true/false.
      */
     public boolean isPassable() {
          return false;
     }
}