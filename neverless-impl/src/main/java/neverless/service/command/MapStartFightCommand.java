package neverless.service.command;

import lombok.Getter;
import neverless.domain.EmptyParams;
import neverless.domain.event.Event;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class MapStartFightCommand extends AbstractCommand<EmptyParams> {

    @Getter
    private Integer npcX;
    @Getter
    private Integer npcY;

    @Override
    public void execute(EmptyParams params) {
        // todo: implement it;
    }
}
