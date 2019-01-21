package neverless.command;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public abstract class AbstractCommand {

    @Id
    private String id = UUID.randomUUID().toString();
}
