package neverless.domain.entity.mapobject;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.PlatformShape;
import neverless.domain.entity.AbstractGameObject;
import neverless.MapObjectMetaType;

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

     /** Returns default value for  */
     public PlatformShape getPlatformShape() {
         return PlatformShape.ELLIPSE;
     }

     /** Returns default value for object width. */
     public int getWidth() {
          return 32;
     }

     /** Returns default value for object height. */
     public int getHeight() {
         return 16;
     }

     /** Returns identifier, specified for graphic render. */
     public abstract String getSignature();

     /** Returns meta-type of object. Meta-type describe main behavior of the object from Player perspective. */
     public abstract MapObjectMetaType getMetaType();

     /**
      * Returns true if terrain is passable.
      *
      * @return true/false.
      */
     public boolean isPassable() {
          return false;
     }
}