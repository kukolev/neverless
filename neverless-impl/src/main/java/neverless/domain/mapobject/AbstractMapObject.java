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

     private int zOrder = 1;
     private String location;

     public int getWidth() {
          return 1;
     }

     public int getHeight() {
         return 1;
     }

     public abstract String getSignature();

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