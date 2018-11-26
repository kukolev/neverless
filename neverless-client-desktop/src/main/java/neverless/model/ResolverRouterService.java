package neverless.model;

import neverless.dto.screendata.player.ResponseDto;
import neverless.model.command.AbstractCommand;
import neverless.model.command.StartNewGameCommand;
import neverless.model.resolver.AbstractCommandResolver;
import neverless.model.resolver.StartNewGameResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ResolverRouterService {

    @Autowired
    private StartNewGameResolver startNewGameResolver;

    private Map<Class, AbstractCommandResolver> map = new ConcurrentHashMap<>();

    /**
     * Initializes map for pairs "command class -> command resolver".
     */
    @PostConstruct
    private void init() {
        map.put(StartNewGameCommand.class, startNewGameResolver);
    }

    /**
     * Resolves command via mapped resolver. Returns ResponseDto.
     *
     * @param command   command that should be resolved.
     */
    public ResponseDto resolve(AbstractCommand command) {
        AbstractCommandResolver<AbstractCommand> resolver = map.get(command.getClass());
        return resolver.resolve(command);
    }
}