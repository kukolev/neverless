package neverless.service;

import neverless.service.reader.CommandMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ClientCore {

    private static String EXIT_COMMAND = "exit";

    @Autowired
    private CommandService commandService;
    @Autowired
    private RenderService renderService;

    public void run() throws IOException {
        String shortCommandName = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (!shortCommandName.equals(EXIT_COMMAND)) {
            System.out.print(">> ");
            shortCommandName = br.readLine();

            CommandMapping commandMapping = CommandMapping.findByShortName(shortCommandName);
            if (commandMapping == null) {
                System.out.println("CommandMapping not found, try again");
                continue;
            }
            System.out.println("Game CommandMapping = " + commandMapping.getShortName());

            switch (commandMapping) {
                case CLIENT_EXIT: System.exit(0);
                case CLIENT_VIEW_LOCAL_MAP: render(Screen.LOCAL_MAP); break;
                case CLIENT_VIEW_INVENTORY: render(Screen.INVENTORY); break;
                case CLIENT_VIEW_JOURNAL: render(Screen.JOURNAL); break;
                case CLIENT_VIEW_DIALOG: render(Screen.DIALOG); break;
                case CLIENT_VIEW_EVENTS: render(Screen.EVENTS); break;
                case CLIENT_VIEW_MANUAL: render(Screen.MANUAL); break;
                case CLIENT_VIEW_RESPONSE: render(Screen.RESPONSE); break;
                default: commandService.execute(commandMapping);
            }
        }
        System.exit(0);
    }

    private void render(Screen screen) {
        renderService.setScreen(screen);
        renderService.render();
    }
}