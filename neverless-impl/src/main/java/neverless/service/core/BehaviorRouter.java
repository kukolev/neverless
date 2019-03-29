package neverless.service.core;

import neverless.domain.entity.Game;
import neverless.repository.cache.GameCache;
import neverless.service.behavior.AbstractBehaviorService;
import neverless.service.behavior.EnemyBehaviorService;
import neverless.service.behavior.PlayerBehaviorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class BehaviorRouter {

    @Autowired
    private GameCache gameCache;
    @Autowired
    private PlayerBehaviorService playerService;
    @Autowired
    private EnemyBehaviorService enemyService;

    private List<AbstractBehaviorService> services = new ArrayList<>();

    @PostConstruct
    private void init() {
        services.add(playerService);
        services.add(enemyService);
    }

    public void processObjects() {

        Game game = gameCache.getGame();
        game.getLocations().forEach(location ->
                location.getObjects().forEach(object ->
                        services.forEach(service -> {
                            if (service.canProcessObject(object)) {
                                service.processObject(object);
                            }
                        })));
    }
}
