package neverless.view.renderer;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.dto.screendata.player.GameStateDto;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class Frame {
    private GameStateDto gameState;
    private List<Sprite> sprites = new ArrayList<>();
}
