package neverless.domain.entity.mapobject;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Utility class for platformCoordinates
 */
@Data
@Entity
@Accessors(chain = true)
public class Coordinate {

    @Id
    @GeneratedValue(generator = "generic-uuid")
    @GenericGenerator(name = "generic-uuid", strategy = "uuid")
    private String id;

    @Column
    private int x;

    @Column
    private int y;
}