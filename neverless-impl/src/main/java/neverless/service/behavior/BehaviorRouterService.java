package neverless.service.behavior;

import neverless.domain.entity.Game;
import neverless.service.util.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class BehaviorRouterService {

    @Autowired
    private GameService gameService;
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

        Game game = gameService.getGame();
        game.getLocations().forEach(l -> {
            l.getObjects().forEach(o -> {
                services.forEach(s -> {
                    if (s.canProcessObject(o)) {
                        s.processObject(o);
                    }
                } );
            });
        });
    }
}
