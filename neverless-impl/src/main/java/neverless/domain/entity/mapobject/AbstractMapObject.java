package neverless.domain.entity.mapobject;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.PlatformShape;
import neverless.domain.Location;
import neverless.domain.entity.AbstractGameObject;
import neverless.MapObjectMetaType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractMapObject extends AbstractGameObject {

     @Column
     private Integer x;

     @Column
     private Integer y;

     @ManyToOne
     private Location location;

     /** Returns default value for  */
     public PlatformShape getPlatformShape() {
         return PlatformShape.CUSTOM;
     }

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Coordinate> platformCoordinates = new ArrayList<>();

     public int getPlatformCenterX() {
         return 16;
     }

    public int getPlatformCenterY() {
        return 32;
    }


    /** Returns default value for object platformWidth. */
     public int getPlatformWidth() {
          return 32;
     }

     /** Returns default value for object platformHeight. */
     public int getPlatformHeight() {
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