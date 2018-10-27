package neverless.service.ai;

import neverless.service.core.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    @Autowired
    private RequestContext requestContext;

    public void handleEvents() {
        // todo: implement reaction on executed command
    }
}
