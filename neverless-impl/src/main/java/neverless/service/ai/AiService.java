package neverless.service.ai;

import lombok.AllArgsConstructor;
import neverless.domain.event.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AiService {

    public void handleEvents(List<Event> events) {
        // todo: implement reaction on executed command
    }
}
