package neverless;

import neverless.service.ClientCore;
import neverless.service.bot.ClientBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import static neverless.util.ConsoleCleaner.cleanConsole;

@SpringBootApplication
public class Client {

    @Autowired
    private ClientCore clCore;

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Client.class, args);
        cleanConsole();
        ClientCore clientCore = (ClientCore) context.getBean("clientCore");
        clientCore.run();

        //ClientBot clientBot = (ClientBot) context.getBean("clientBot");
        //clientBot.run();
    }
}
