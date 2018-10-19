package neverless.service;

import neverless.domain.Command;
import neverless.domain.CommandMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ClientCore {

    private static String EXIT_COMMAND = "exit";

    @Autowired
    private CommandService commandService;

    public void run() throws IOException {
        String shortCommandName = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (!shortCommandName.equals(EXIT_COMMAND)) {
            System.out.print(">> ");
            shortCommandName = br.readLine();

            CommandMapping commandMapping = CommandMapping.findByShortName(shortCommandName);
            System.out.println("Game CommandMapping = " + commandMapping.getShortName());
            if (commandMapping == null) {
                System.out.println("CommandMapping not found, try again");
                continue;
            }
            if (commandMapping == CommandMapping.CLIENT_EXIT) {
                System.exit(0);
            }
            commandService.execute(commandMapping);
        }
        System.exit(0);
    }
}