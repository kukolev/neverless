package neverless.domain.game.statistic;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.game.AbstractGameObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class GameStatistic extends AbstractGameObject {

    private int turnNumber;
}
