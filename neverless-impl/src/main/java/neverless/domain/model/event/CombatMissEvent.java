package neverless.domain.model.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CombatMissEvent extends AbstractEvent {

    private String defenderId;
}