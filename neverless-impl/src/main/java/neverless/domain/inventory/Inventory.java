package neverless.domain.inventory;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

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
