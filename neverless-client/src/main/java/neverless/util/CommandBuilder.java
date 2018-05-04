package neverless.util;

import neverless.domain.Command;
import neverless.dto.CommandDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommandBuilder {

    public CommandDto build(Command command, Map<String, String> bundle) {
        return new CommandDto()
                .setName(command.getServerCommand().name())
                .setBundle(bundle);
    }
}