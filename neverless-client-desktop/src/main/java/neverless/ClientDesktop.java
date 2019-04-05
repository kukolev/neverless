package neverless;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import neverless.view.RootPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

import static neverless.util.Constants.WINDOW_HEIGHT;
import static neverless.util.Constants.WINDOW_WIDTH;

@SpringBootApplication
@EnableCaching
public class ClientDesktop extends Application {

    protected ConfigurableApplicationContext context;

    private static String[] savedArgs;

    @Autowired
    private RootPane rootPane;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(rootPane));
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);

        stage.show();
    }

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(getClass(), savedArgs);
        context.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        context.close();
    }

    public static void main(String[] args) {
        savedArgs = args;
        Application.launch(ClientDesktop.class, args);
    }
}