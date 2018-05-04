package neverless.service.bot;

import neverless.domain.Command;
import neverless.service.CommandRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
public class ClientBot {

    @Autowired
    private CommandRouter router;
    @Autowired
    private ResourcePatternResolver resourceResolver;
    @Value("${script}")
    private String script;

    public void run() {
        List<String> lines = loadFromFile(script);
        lines.stream()
                .map(String::trim)
                .forEach(this::sendCommand);
    }

    private List<String> loadFromFile(String script) {
        return Arrays.asList(script.split(";"));
    }

    public void sendCommand(String textCommand) {
        List<String> words = Arrays.asList(textCommand.split(" "));
        String CommandName = words.get(0);
        List<String> vals;
        if (words.size() > 1) {
            vals = words.subList(1, words.size());
        } else {
            vals = new ArrayList<>();
        }

        Command command = Command.findByShortName(CommandName);

        Map<String, String> bundle = new HashMap<>();
        String[] params = command.getParams();
        for (int i = 0; i < vals.size(); i++) {
            bundle.put(params[i], vals.get(i));
        }
        router.route(command, bundle);

        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
