package neverless.service;

import neverless.domain.Command;
import neverless.dto.ResponseDto;
import neverless.dto.screendata.DialogScreenDataDto;
import neverless.dto.screendata.LocalMapScreenDataDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static neverless.util.ConsoleCleaner.cleanConsole;

@Service
public class RenderService {

    public void render(ResponseDto responseDto) {
        cleanConsole();
        renderCommands();
        renderLocalMap(responseDto);
        renderEvents(responseDto);
        renderDialog(responseDto);
    }

    private void renderCommands() {
        System.out.println("Available commands: ");
        Arrays.stream(Command.values())
                .map(Command::getShortName)
                .forEach(s ->  System.out.print(" [" + s + "]"));
        System.out.println();
    }

    private void renderLocalMap(ResponseDto responseDto) {
        LocalMapScreenDataDto data = responseDto.getLocalMapScreenData();
        int width = data.getWidth();
        int height = data.getHeight();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(" " + data.getLocalMap()[j][i].charAt(0) + " ");
            }
            System.out.println("");
        }
    }

    private void renderDialog(ResponseDto responseDto) {
        DialogScreenDataDto dialogScreenDataDto = responseDto.getDialogScreenDataDto();
        if (dialogScreenDataDto != null) {
            System.out.println("NPC:\n  " + dialogScreenDataDto.getNpcPhrase());
            System.out.println("You:");
            dialogScreenDataDto.getAnswers()
                    .stream()
                    .forEach(s -> System.out.println("  - " + s));
        }
    }

    private void renderEvents(ResponseDto responseDto) {
        System.out.println("Raised events:");
        responseDto.getEventsScreenDataDto().getEvents()
                .forEach(evt -> {
                    System.out.println(" - " + evt.getType());
                });
    }
}
