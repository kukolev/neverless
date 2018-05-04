package neverless.service;

import neverless.domain.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static neverless.util.ConsoleCleaner.cleanConsole;

@Service
public class ClientCore {

    private static String EXIT_COMMAND = "exit";

    @Autowired
    private CommandRouter router;

    public void run() throws IOException {
        String shortCommandName = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (!shortCommandName.equals(EXIT_COMMAND)) {
            System.out.print(">> ");
            shortCommandName = br.readLine();

            Command command = Command.findByShortName(shortCommandName);
            if (command == null) {
                System.out.println("Command not found, try again");
                continue;
            }

            Map<String, String> bundle = new HashMap<>();
            if (command.getParams() != null) {
                Arrays.stream(command.getParams())
                        .forEach(param -> {
                            System.out.println(">> " + param + " == ");
                            try {
                                String paramVal = br.readLine();
                                bundle.put(param, paramVal);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

            }
            router.route(command, bundle);
        }
        System.exit(0);
    }
}