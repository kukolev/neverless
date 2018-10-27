package neverless.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import neverless.domain.event.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventMapper {

    private ObjectMapper objectMapper = new ObjectMapper();

    public EventMapper() {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public  Map<String, Object> map(AbstractEvent event) {
        Map<String, Object> result = objectMapper.convertValue(event, Map.class);
        return result;
    }
}