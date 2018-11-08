package neverless.domain.entity.mapobject;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.entity.AbstractGameObject;

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

     /** Returns default value for object width. */
     public int getWidth() {
          return 1;
     }

     /** Returns default value for object height. */
     public int getHeight() {
         return 1;
     }

     /** Returns identifier, specified for graphic render. */
     public abstract String getSignature();

     /**
      * Returns true if terrain is passable.
      *
      * @return true/false.
      */
     public boolean isPassable() {
          return false;
     }
}