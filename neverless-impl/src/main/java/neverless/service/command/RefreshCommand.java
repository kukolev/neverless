package neverless.service.command;

import neverless.domain.event.Event;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RefreshCommand extends AbstractCommand {

    @Override
    public Event onExecute() {
        // do nothing here.
        return null;
    }
}
