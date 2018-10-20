package neverless.service.command;

import lombok.Getter;
import neverless.domain.event.AbstractEvent;
import neverless.util.EventFactory;
import neverless.service.core.EventContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class AbstractCommand<T extends AbstractCommandParams> {

    @Autowired
    protected EventFactory eventFactory;
    @Autowired
    private EventContext eventContext;
    @Getter
    private List<AbstractEvent> events = new ArrayList<>();

    /**
     * Executes command. Registers event, raised in concrete subclass Command (should be overloaded).
     */
    public abstract void execute(T params);


    /**
     * Registers new event in service. Method may be used for register more then one event per command execution
     *
     * @param event
     */
    protected void registerEvent(AbstractEvent event) {
        eventContext.getEvents().add(event);
    }
}