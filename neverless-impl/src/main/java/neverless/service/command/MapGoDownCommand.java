package neverless.service.command;

import neverless.dto.command.Direction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MapGoDownCommand extends AbstractMapGoCommand {

    public MapGoDownCommand() {
        this.direction = Direction.DOWN;
    }
}
