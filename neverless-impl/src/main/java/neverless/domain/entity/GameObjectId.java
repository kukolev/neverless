package neverless.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@Embeddable
public class GameObjectId implements Serializable {

    private String uniqueName;
    private String session;

    public GameObjectId() {

    }
}
