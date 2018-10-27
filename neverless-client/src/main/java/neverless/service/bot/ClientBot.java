package neverless.service.bot;

import neverless.service.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ClientBot {

    @Autowired
    private CommandService commandService;
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
//        List<String> words = Arrays.asList(textCommand.split(" "));
//        String CommandName = words.get(0);
//        List<String> vals;
//        if (words.size() > 1) {
//            vals = words.subList(1, words.size());
//        } else {
//            vals = new ArrayList<>();
//        }
//
//        CommandMapping commandMapping = CommandMapping.findByShortName(CommandName);
//
//        Map<String, String> bundle = new HashMap<>();
//        String[] params = commandMapping.getParams();
//        for (int i = 0; i < vals.size(); i++) {
//            bundle.put(params[i], vals.get(i));
//        }
//        commandService.execute(commandMapping, bundle);
//
//        try {
//            Thread.currentThread().sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
