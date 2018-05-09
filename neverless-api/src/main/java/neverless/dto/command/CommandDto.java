package neverless.dto.command;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class CommandDto {

    private String name;
    private Map<String, String> bundle = new HashMap<>();
}