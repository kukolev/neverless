package neverless.domain.mapobject;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.AbstractGameObject;
import neverless.repository.MapObjectsRepository;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractMapObject extends AbstractGameObject {

     @Column
     private Integer x;

     @Column
     private Integer y;

     @Column
     private int zOrder = 1;

     @Column
     private String location;

     public int getWidth() {
          return 1;
     }

     public int getHeight() {
         return 1;
     }

     public abstract String getSignature();

     // todo: delete this overhead.
     public final void register(MapObjectsRepository repository) {
          repository.simpleSave(this);
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