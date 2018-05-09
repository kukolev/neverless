package neverless.service.command;

import neverless.dto.command.CommandName;
import neverless.domain.event.Event;
import neverless.domain.event.EventFactory;
import neverless.exception.InvalidCommandException;
import neverless.service.screendata.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class AbstractCommand {

    @Autowired
    private EventService eventService;
    @Autowired
    protected EventFactory eventFactory;

    private CommandName commandName;

    public void init(Map<String, String> bundle) {
        Class clazz = this.getClass();
        Arrays.stream(clazz.getDeclaredFields())
                .forEach(f -> {
                    f.setAccessible(true);
                    String value = bundle.get(f.getName());
                    setValue(f, value);
                    f.setAccessible(false);
                });
    }

    private void setValue(Field field, String value) {
        try {
            if (field.getType().equals(String.class)) {
                field.set(this, value);
            } else if (field.getType().equals(Integer.class)) {
                field.set(this, new Integer(value));
            }
        } catch (IllegalAccessException e) {
            throw new InvalidCommandException();
        }
    }

    /**
     * Executes command. Registers event, raised in concrete subclass Command.
     */
    public final void execute() {
        Event event = onExecute();
        if (event != null) {
            eventService.add(event);
        }
    }

    /**
     * Command execution handler (should be overloaded)
     *
     * @return  some event that happened due execution.
     */
    protected abstract Event onExecute();

    /**
     * Registers new event in service. Method may be used for register more then one event per command execution
     *
     * @param event
     */
    protected void registerEvent(Event event) {
        eventService.add(event);
    }
}
