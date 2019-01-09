package neverless.service.ai;

import neverless.service.screendata.EnemyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    @Autowired
    private EnemyService enemyService;

    public void handleEvents() {
        // todo: implement reaction on executed command
        enemyService.processBehavior();
        enemyService.respawn();
    }
}
