package neverless.service.command;

import neverless.dto.command.Direction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MapGoUpCommand extends AbstractMapGoCommand {

    public MapGoUpCommand() {
        this.direction = Direction.UP;
    }
}
