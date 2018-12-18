package neverless.domain.entity.inventory;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Accessors(chain = true)
@Entity
public class Inventory {

    @Id
    @GeneratedValue(generator = "generic-uuid")
    @GenericGenerator(name = "generic-uuid", strategy = "uuid")
    private String id;

    @OneToOne
    private Bag bag;

    @OneToOne
    private Equipment equipment = new Equipment();
}
