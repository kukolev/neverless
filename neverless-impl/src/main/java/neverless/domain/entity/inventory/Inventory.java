package neverless.domain.entity.inventory;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Accessors(chain = true)
@Entity
public class Inventory {

    @Id
    private String id;

    @OneToOne
    private Bag bag;

    @OneToOne
    private Equipment equipment = new Equipment();
}
