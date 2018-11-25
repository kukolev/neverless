package neverless.view.renderer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class Frame {

    private List<Sprite> sprites = new ArrayList<>();
}
