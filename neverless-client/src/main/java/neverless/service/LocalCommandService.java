package neverless.service;

import neverless.domain.Command;
import org.springframework.stereotype.Service;

@Service
public class LocalCommandService {

    public void resolve(Command command) {
        System.out.println("Local Command = " + command.getShortName());
        if (command.equals(Command.CLIENT_EXIT)) {
            System.exit(0);
        }
    }
}
