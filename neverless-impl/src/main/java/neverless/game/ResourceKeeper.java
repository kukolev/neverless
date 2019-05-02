package neverless.game;

import neverless.util.Direction;
import neverless.domain.model.entity.behavior.BehaviorState;
import neverless.domain.view.Resource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static neverless.util.Direction.EAST;
import static neverless.util.Direction.NORTH;
import static neverless.util.Direction.NORTH_EAST;
import static neverless.util.Direction.NORTH_WEST;
import static neverless.util.Direction.SOUTH;
import static neverless.util.Direction.SOUTH_EAST;
import static neverless.util.Direction.SOUTH_WEST;
import static neverless.util.Direction.WEST;
import static neverless.game.Signatures.IMG_GOBLIN;
import static neverless.game.Signatures.IMG_LOOT;
import static neverless.game.Signatures.IMG_PLAYER;
import static neverless.game.Signatures.IMG_TAVERN_SMALL;
import static neverless.domain.model.entity.behavior.BehaviorState.ATTACK;
import static neverless.domain.model.entity.behavior.BehaviorState.IDLE;
import static neverless.domain.model.entity.behavior.BehaviorState.MOVE;

@Component
public class ResourceKeeper {

    private static final Resource DEFAULT_RESOURCE = new Resource("no_resource.png", 0, 0, 32, 32);

    private Map<String, Resource> map = new HashMap<>();
    {
        // Player moving
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH_WEST + "_01", new Resource("player_walking_diag.png", 0, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH_WEST + "_02", new Resource("player_walking_diag.png", 128, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH_WEST + "_03", new Resource("player_walking_diag.png", 256, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH_WEST + "_04", new Resource("player_walking_diag.png", 384, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH_WEST + "_05", new Resource("player_walking_diag.png", 512, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH_WEST + "_06", new Resource("player_walking_diag.png", 640, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH_WEST + "_07", new Resource("player_walking_diag.png", 768, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH_WEST + "_08", new Resource("player_walking_diag.png", 896, 0, 128, 128));

        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH_WEST + "_01", new Resource("player_walking_diag.png", 0, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH_WEST + "_02", new Resource("player_walking_diag.png", 128, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH_WEST + "_03", new Resource("player_walking_diag.png", 256, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH_WEST + "_04", new Resource("player_walking_diag.png", 384, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH_WEST + "_05", new Resource("player_walking_diag.png", 512, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH_WEST + "_06", new Resource("player_walking_diag.png", 640, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH_WEST + "_07", new Resource("player_walking_diag.png", 768, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH_WEST + "_08", new Resource("player_walking_diag.png", 896, 128, 128, 128));

        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH_EAST + "_01", new Resource("player_walking_diag.png", 0, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH_EAST + "_02", new Resource("player_walking_diag.png", 128, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH_EAST + "_03", new Resource("player_walking_diag.png", 256, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH_EAST + "_04", new Resource("player_walking_diag.png", 384, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH_EAST + "_05", new Resource("player_walking_diag.png", 512, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH_EAST + "_06", new Resource("player_walking_diag.png", 640, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH_EAST + "_07", new Resource("player_walking_diag.png", 768, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH_EAST + "_08", new Resource("player_walking_diag.png", 896, 256, 128, 128));

        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH_EAST + "_01", new Resource("player_walking_diag.png", 0, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH_EAST + "_02", new Resource("player_walking_diag.png", 128, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH_EAST + "_03", new Resource("player_walking_diag.png", 256, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH_EAST + "_04", new Resource("player_walking_diag.png", 384, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH_EAST + "_05", new Resource("player_walking_diag.png", 512, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH_EAST + "_06", new Resource("player_walking_diag.png", 640, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH_EAST + "_07", new Resource("player_walking_diag.png", 768, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH_EAST + "_08", new Resource("player_walking_diag.png", 896, 384, 128, 128));

        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH + "_01", new Resource("player_walking.png", 0, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH + "_02", new Resource("player_walking.png", 128, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH + "_03", new Resource("player_walking.png", 256, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH + "_04", new Resource("player_walking.png", 384, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH + "_05", new Resource("player_walking.png", 512, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH + "_06", new Resource("player_walking.png", 640, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH + "_07", new Resource("player_walking.png", 768, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + SOUTH + "_08", new Resource("player_walking.png", 896, 0, 128, 128));

        map.put(IMG_PLAYER + "_" + MOVE + "_" + WEST + "_01", new Resource("player_walking.png", 0, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + WEST + "_02", new Resource("player_walking.png", 128, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + WEST + "_03", new Resource("player_walking.png", 256, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + WEST + "_04", new Resource("player_walking.png", 384, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + WEST + "_05", new Resource("player_walking.png", 512, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + WEST + "_06", new Resource("player_walking.png", 640, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + WEST + "_07", new Resource("player_walking.png", 768, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + WEST + "_08", new Resource("player_walking.png", 896, 128, 128, 128));

        map.put(IMG_PLAYER + "_" + MOVE + "_" + EAST + "_01", new Resource("player_walking.png", 0, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + EAST + "_02", new Resource("player_walking.png", 128, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + EAST + "_03", new Resource("player_walking.png", 256, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + EAST + "_04", new Resource("player_walking.png", 384, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + EAST + "_05", new Resource("player_walking.png", 512, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + EAST + "_06", new Resource("player_walking.png", 640, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + EAST + "_07", new Resource("player_walking.png", 768, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + EAST + "_08", new Resource("player_walking.png", 896, 256, 128, 128));

        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH + "_01", new Resource("player_walking.png", 0, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH + "_02", new Resource("player_walking.png", 128, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH + "_03", new Resource("player_walking.png", 256, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH + "_04", new Resource("player_walking.png", 384, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH + "_05", new Resource("player_walking.png", 512, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH + "_06", new Resource("player_walking.png", 640, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH + "_07", new Resource("player_walking.png", 768, 384, 128, 128));
        map.put(IMG_PLAYER + "_" + MOVE + "_" + NORTH + "_08", new Resource("player_walking.png", 896, 384, 128, 128));

        // Player idle
        map.put(IMG_PLAYER + "_" + IDLE + "_" + SOUTH + "_01", new Resource("player_idle.png", 0, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + IDLE + "_" + WEST + "_01", new Resource("player_idle.png", 0, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + IDLE + "_" + EAST + "_01", new Resource("player_idle.png", 0, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + IDLE + "_" + NORTH + "_01", new Resource("player_idle.png", 0, 384, 128, 128));

        map.put(IMG_PLAYER + "_" + IDLE + "_" + SOUTH_WEST + "_01", new Resource("player_idle_diag.png", 0, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + IDLE + "_" + NORTH_WEST + "_01", new Resource("player_idle_diag.png", 0, 128, 128, 128));
        map.put(IMG_PLAYER + "_" + IDLE + "_" + SOUTH_EAST + "_01", new Resource("player_idle_diag.png", 0, 256, 128, 128));
        map.put(IMG_PLAYER + "_" + IDLE + "_" + NORTH_EAST + "_01", new Resource("player_idle_diag.png", 0, 384, 128, 128));

        // Player attacking
        map.put(IMG_PLAYER + "_" + ATTACK + "_" + SOUTH + "_01", new Resource("player_attacking.png", 0, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + ATTACK + "_" + SOUTH + "_02", new Resource("player_attacking.png", 128, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + ATTACK + "_" + SOUTH + "_03", new Resource("player_attacking.png", 256, 0, 128, 128));

        map.put(IMG_PLAYER + "_" + ATTACK + "_" + WEST + "_01", new Resource("player_attacking.png", 0, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + ATTACK + "_" + WEST + "_02", new Resource("player_attacking.png", 128, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + ATTACK + "_" + WEST + "_03", new Resource("player_attacking.png", 256, 0, 128, 128));

        map.put(IMG_PLAYER + "_" + ATTACK + "_" + EAST + "_01", new Resource("player_attacking.png", 0, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + ATTACK + "_" + EAST + "_02", new Resource("player_attacking.png", 128, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + ATTACK + "_" + EAST + "_03", new Resource("player_attacking.png", 256, 0, 128, 128));

        map.put(IMG_PLAYER + "_" + ATTACK + "_" + NORTH + "_01", new Resource("player_attacking.png", 0, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + ATTACK + "_" + NORTH + "_02", new Resource("player_attacking.png", 128, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + ATTACK + "_" + NORTH + "_03", new Resource("player_attacking.png", 256, 0, 128, 128));

        map.put(IMG_PLAYER + "_" + ATTACK + "_" + SOUTH_EAST + "_01", new Resource("player_attacking.png", 0, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + ATTACK + "_" + SOUTH_EAST + "_02", new Resource("player_attacking.png", 128, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + ATTACK + "_" + SOUTH_EAST + "_03", new Resource("player_attacking.png", 256, 0, 128, 128));

        map.put(IMG_PLAYER + "_" + ATTACK + "_" + NORTH_WEST + "_01", new Resource("player_attacking.png", 0, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + ATTACK + "_" + NORTH_WEST + "_02", new Resource("player_attacking.png", 128, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + ATTACK + "_" + NORTH_WEST + "_03", new Resource("player_attacking.png", 256, 0, 128, 128));

        map.put(IMG_PLAYER + "_" + ATTACK + "_" + SOUTH_WEST + "_01", new Resource("player_attacking.png", 0, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + ATTACK + "_" + SOUTH_WEST + "_02", new Resource("player_attacking.png", 128, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + ATTACK + "_" + SOUTH_WEST + "_03", new Resource("player_attacking.png", 256, 0, 128, 128));

        map.put(IMG_PLAYER + "_" + ATTACK + "_" + NORTH_EAST + "_01", new Resource("player_attacking.png", 0, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + ATTACK + "_" + NORTH_EAST + "_02", new Resource("player_attacking.png", 128, 0, 128, 128));
        map.put(IMG_PLAYER + "_" + ATTACK + "_" + NORTH_EAST + "_03", new Resource("player_attacking.png", 256, 0, 128, 128));

        // Tavern
        map.put(IMG_TAVERN_SMALL + "_" + IDLE + "_" + NORTH + "_01", new Resource("tavern_1.png", 0, 0, 500, 813));

        // Goblin moving
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH_WEST + "_01", new Resource("wolf_walking_diag.png", 0, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH_WEST + "_02", new Resource("wolf_walking_diag.png", 96, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH_WEST + "_03", new Resource("wolf_walking_diag.png", 192, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH_WEST + "_04", new Resource("wolf_walking_diag.png", 288, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH_WEST + "_05", new Resource("wolf_walking_diag.png", 384, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH_WEST + "_06", new Resource("wolf_walking_diag.png", 480, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH_WEST + "_07", new Resource("wolf_walking_diag.png", 576, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH_WEST + "_08", new Resource("wolf_walking_diag.png", 672, 0, 96, 96));

        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH_WEST + "_01", new Resource("wolf_walking_diag.png", 0, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH_WEST + "_02", new Resource("wolf_walking_diag.png", 96, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH_WEST + "_03", new Resource("wolf_walking_diag.png", 192, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH_WEST + "_04", new Resource("wolf_walking_diag.png", 288, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH_WEST + "_05", new Resource("wolf_walking_diag.png", 384, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH_WEST + "_06", new Resource("wolf_walking_diag.png", 480, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH_WEST + "_07", new Resource("wolf_walking_diag.png", 576, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH_WEST + "_08", new Resource("wolf_walking_diag.png", 672, 96, 96, 96));

        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH_EAST + "_01", new Resource("wolf_walking_diag.png", 0, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH_EAST + "_02", new Resource("wolf_walking_diag.png", 96, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH_EAST + "_03", new Resource("wolf_walking_diag.png", 192, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH_EAST + "_04", new Resource("wolf_walking_diag.png", 288, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH_EAST + "_05", new Resource("wolf_walking_diag.png", 384, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH_EAST + "_06", new Resource("wolf_walking_diag.png", 480, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH_EAST + "_07", new Resource("wolf_walking_diag.png", 576, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH_EAST + "_08", new Resource("wolf_walking_diag.png", 672, 192, 96, 96));

        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH_EAST + "_01", new Resource("wolf_walking_diag.png", 0, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH_EAST + "_02", new Resource("wolf_walking_diag.png", 96, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH_EAST + "_03", new Resource("wolf_walking_diag.png", 192, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH_EAST + "_04", new Resource("wolf_walking_diag.png", 288, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH_EAST + "_05", new Resource("wolf_walking_diag.png", 384, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH_EAST + "_06", new Resource("wolf_walking_diag.png", 480, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH_EAST + "_07", new Resource("wolf_walking_diag.png", 576, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH_EAST + "_08", new Resource("wolf_walking_diag.png", 672, 288, 96, 96));

        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH + "_01", new Resource("wolf_walking.png", 0, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH + "_02", new Resource("wolf_walking.png", 96, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH + "_03", new Resource("wolf_walking.png", 192, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH + "_04", new Resource("wolf_walking.png", 288, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH + "_05", new Resource("wolf_walking.png", 384, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH + "_06", new Resource("wolf_walking.png", 480, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH + "_07", new Resource("wolf_walking.png", 576, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + SOUTH + "_08", new Resource("wolf_walking.png", 672, 0, 96, 96));

        map.put(IMG_GOBLIN + "_" + MOVE + "_" + WEST + "_01", new Resource("wolf_walking.png", 0, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + WEST + "_02", new Resource("wolf_walking.png", 96, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + WEST + "_03", new Resource("wolf_walking.png", 192, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + WEST + "_04", new Resource("wolf_walking.png", 288, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + WEST + "_05", new Resource("wolf_walking.png", 384, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + WEST + "_06", new Resource("wolf_walking.png", 480, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + WEST + "_07", new Resource("wolf_walking.png", 576, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + WEST + "_08", new Resource("wolf_walking.png", 672, 96, 96, 96));

        map.put(IMG_GOBLIN + "_" + MOVE + "_" + EAST + "_01", new Resource("wolf_walking.png", 0, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + EAST + "_02", new Resource("wolf_walking.png", 96, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + EAST + "_03", new Resource("wolf_walking.png", 192, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + EAST + "_04", new Resource("wolf_walking.png", 288, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + EAST + "_05", new Resource("wolf_walking.png", 384, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + EAST + "_06", new Resource("wolf_walking.png", 480, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + EAST + "_07", new Resource("wolf_walking.png", 576, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + EAST + "_08", new Resource("wolf_walking.png", 672, 192, 96, 96));

        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH + "_01", new Resource("wolf_walking.png", 0, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH + "_02", new Resource("wolf_walking.png", 96, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH + "_03", new Resource("wolf_walking.png", 192, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH + "_04", new Resource("wolf_walking.png", 288, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH + "_05", new Resource("wolf_walking.png", 384, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH + "_06", new Resource("wolf_walking.png", 480, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH + "_07", new Resource("wolf_walking.png", 576, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + MOVE + "_" + NORTH + "_08", new Resource("wolf_walking.png", 672, 288, 96, 96));

        
        // Goblin attacking
        map.put(IMG_GOBLIN + "_" + ATTACK + "_" + SOUTH + "_01", new Resource("wolf_attacking.png", 0, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + ATTACK + "_" + SOUTH + "_02", new Resource("wolf_attacking.png", 96, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + ATTACK + "_" + WEST + "_01", new Resource("wolf_attacking.png", 0, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + ATTACK + "_" + WEST + "_02", new Resource("wolf_attacking.png", 96, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + ATTACK + "_" + EAST + "_01", new Resource("wolf_attacking.png", 0, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + ATTACK + "_" + EAST + "_02", new Resource("wolf_attacking.png", 96, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + ATTACK + "_" + NORTH + "_01", new Resource("wolf_attacking.png", 0, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + ATTACK + "_" + NORTH + "_02", new Resource("wolf_attacking.png", 96, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + ATTACK + "_" + SOUTH_EAST + "_01", new Resource("wolf_attacking_diag.png", 0, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + ATTACK + "_" + SOUTH_EAST + "_02", new Resource("wolf_attacking_diag.png", 96, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + ATTACK + "_" + NORTH_WEST + "_01", new Resource("wolf_attacking_diag.png", 0, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + ATTACK + "_" + NORTH_WEST + "_02", new Resource("wolf_attacking_diag.png", 96, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + ATTACK + "_" + SOUTH_WEST + "_01", new Resource("wolf_attacking_diag.png", 0, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + ATTACK + "_" + SOUTH_WEST + "_02", new Resource("wolf_attacking_diag.png", 96, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + ATTACK + "_" + NORTH_EAST + "_01", new Resource("wolf_attacking_diag.png", 0, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + ATTACK + "_" + NORTH_EAST + "_02", new Resource("wolf_attacking_diag.png", 96, 288, 96, 96));

        // Goblin idle
        map.put(IMG_GOBLIN + "_" + IDLE + "_" + SOUTH + "_01", new Resource("wolf_idle.png", 0, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + IDLE + "_" + WEST + "_01", new Resource("wolf_idle.png", 0, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + IDLE + "_" + EAST + "_01", new Resource("wolf_idle.png", 0, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + IDLE + "_" + NORTH + "_01", new Resource("wolf_idle.png", 0, 288, 96, 96));
        map.put(IMG_GOBLIN + "_" + IDLE + "_" + SOUTH_EAST + "_01", new Resource("wolf_idle_diag.png", 0, 0, 96, 96));
        map.put(IMG_GOBLIN + "_" + IDLE + "_" + NORTH_WEST + "_01", new Resource("wolf_idle_diag.png", 0, 96, 96, 96));
        map.put(IMG_GOBLIN + "_" + IDLE + "_" + SOUTH_WEST + "_01", new Resource("wolf_idle_diag.png", 0, 192, 96, 96));
        map.put(IMG_GOBLIN + "_" + IDLE + "_" + NORTH_EAST + "_01", new Resource("wolf_idle_diag.png", 0, 288, 96, 96));

        // Loot
        map.put(IMG_LOOT + "_" + IDLE + "_" + NORTH + "_01", new Resource("loot.png", 0, 0, 32, 32));
    }

    /**
     * Returns true if resource exists in map.
     *
     * @param signature     object signature.
     * @param state         behavior state.
     * @param direction     direction determines orientation of object.
     * @param imageNumber   number of frame.
     */
    public boolean isResourceExists(String signature, BehaviorState state, Direction direction, int imageNumber) {
        String key = calcKey(signature, state, direction, imageNumber);
        return map.containsKey(key);
    }

    /**
     * Returns resource for object.
     *
     * @param signature     object signature.
     * @param state         behavior state.
     * @param direction     direction determines orientation of object.
     * @param imageNumber   number of frame.
     */
    public Resource getResource(String signature, BehaviorState state, Direction direction, int imageNumber) {
        String key = calcKey(signature, state, direction, imageNumber);
        Resource result = map.get(key);
        if (result == null) {
            result = getDefault();
        }
        return result;
    }

    /**
     * Returns default resource.
     */
    public Resource getDefault() {
        return DEFAULT_RESOURCE;
    }

    /**
     * Calculates and returns key for map.
     *
     * @param signature     object signature.
     * @param state         behavior state.
     * @param direction     direction determines orientation of object.
     * @param imageNumber   number of frame.
     */
    private String calcKey(String signature, BehaviorState state, Direction direction, int imageNumber) {
        String numb = String.valueOf(imageNumber);
        if (imageNumber < 10) {
            numb = "0" + numb;
        }
        return signature + "_" + state + "_" + direction + "_" + numb;
    }
}