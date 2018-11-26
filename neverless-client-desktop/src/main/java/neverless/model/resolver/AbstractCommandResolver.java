package neverless.model.resolver;

import neverless.dto.screendata.player.ResponseDto;
import neverless.model.command.AbstractCommand;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractCommandResolver<T extends AbstractCommand> {

    public abstract ResponseDto resolve(T command);
}
