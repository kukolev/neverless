package neverless.service.command;

import lombok.Getter;
import neverless.domain.event.Event;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MapStartFightCommand extends AbstractCommand {

    @Getter
    private Integer npcX;
    @Getter
    private Integer npcY;

    @Override
    protected Event onExecute() {
        return null;
    }
}
