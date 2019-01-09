package neverless.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractGameObject implements Serializable {

    @Id
    private String uniqueName = UUID.randomUUID().toString();
}
