package neverless.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.command.AbstractCommand;
import neverless.domain.entity.mapobject.BehaviorStage;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.util.UUID;

@Data
@Accessors(chain = true)
@Entity
public abstract class AbstractGameObject {

    @Id
    private String uniqueName = UUID.randomUUID().toString();

    @OneToOne
    private AbstractCommand command;

    @Transient
    private BehaviorStage behaviorStage;
}
