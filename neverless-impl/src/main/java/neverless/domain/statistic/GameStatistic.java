package neverless.domain.statistic;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.AbstractGameObject;

@Data
@Accessors(chain = true)
public class GameStatistic extends AbstractGameObject {

    private int turnNumber;
}
