package neverless.service.core;

import lombok.Getter;
import neverless.domain.event.AbstractEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EventContext {

    @Getter
    private List<AbstractEvent> events = new ArrayList<>();
}
