package neverless.service.ai;

import neverless.context.RequestContext;
import neverless.service.screendata.EnemyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AiService {

    @Autowired
    private RequestContext requestContext;
    @Autowired
    private EnemyService enemyService;

    public void handleEvents() {
        // todo: implement reaction on executed command
        enemyService.move();
        enemyService.respawn();
    }
}
