package neverless.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public abstract class AbstractGameObject {

    private String uniqueName = UUID.randomUUID().toString();
}