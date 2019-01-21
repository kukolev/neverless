package neverless.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import neverless.domain.entity.mapobject.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class Game {

    private String id;
    private Player player;
    private List<Location> locations = new ArrayList<>();
    private Map<String, String> params = new HashMap<>();
}
