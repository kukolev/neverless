package neverless.service.screendata;

import neverless.domain.event.AbstractEvent;
import neverless.service.core.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractService {

    @Autowired
    private RequestContext requestContext;

    /**
     * Registers new event in service. Method may be used for register more then one event per command execution
     *
     * @param event
     */
    protected void registerEvent(AbstractEvent event) {
        requestContext.getEvents().add(event);
    }
}
