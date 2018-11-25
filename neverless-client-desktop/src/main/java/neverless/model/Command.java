package neverless.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class Command {

    private String s = UUID.randomUUID().toString();
}
