package neverless.service;

import lombok.Getter;
import lombok.Setter;
import neverless.service.reader.CommandMapping;
import neverless.dto.screendata.player.ResponseDto;
import neverless.dto.screendata.DialogScreenDataDto;
import neverless.dto.screendata.LocalMapScreenDataDto;
import neverless.dto.screendata.quest.QuestInfoDto;
import neverless.dto.screendata.quest.QuestScreenDataDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

import static neverless.util.ConsoleCleaner.cleanConsole;

@Service
public class RenderService {

    @Getter
    private ResponseDto curResponse;
    @Setter
    private Screen screen;

    public void render() {
        cleanConsole();
        renderCommands();
        switch (screen) {
            case LOCAL_MAP: renderLocalMap(); break;
            case DIALOG: renderDialog(); break;
            case JOURNAL: renderQuestJournal(); break;
            case EVENTS: renderEvents(); break;
            case INVENTORY: renderInventory(); break;
            case MANUAL: renderManual(); break;
            case RESPONSE: renderResponse(); break;
        }
    }

    public void setCurResponse(ResponseDto responseDto) {
        this.curResponse = responseDto;
        detectScreen();
    }

    private void detectScreen() {
        if (StringUtils.isEmpty(curResponse.getDialogScreenDataDto().getNpcPhrase())) {
            screen = Screen.LOCAL_MAP;
        } else {
            screen = Screen.DIALOG;
        }
    }

    private void renderCommands() {
        System.out.println("[1 - Local map]  [2 - Inventory]  [3 - Journal]  [7 - Dialog]  [8 - Events]  [9 - Manual]  [0 - Last Response]");
        System.out.println();
    }

    private void renderManual() {
        System.out.println("Available commands: ");
        Arrays.stream(CommandMapping.values())
                .map(CommandMapping::getShortName)
                .forEach(s ->  System.out.print(" [" + s + "]"));
        System.out.println();
    }

    private void renderLocalMap() {
        LocalMapScreenDataDto data = curResponse.getLocalMapScreenData();
        int width = data.getWidth();
        int height = data.getHeight();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(" " + data.getLocalMap()[j][i].charAt(0) + " ");
            }
            System.out.println("");
        }
    }

    private void renderDialog() {
        DialogScreenDataDto dialogScreenDataDto = curResponse.getDialogScreenDataDto();
        if (dialogScreenDataDto != null) {
            System.out.println("NPC:\n  " + dialogScreenDataDto.getNpcPhrase());
            System.out.println("You:");
            dialogScreenDataDto.getAnswers()
                    .stream()
                    .forEach(s -> System.out.println("  - " + s));
        }
    }

    private void renderEvents() {
        System.out.println("Raised events:");
        curResponse.getEventsScreenDataDto().getEvents()
                .forEach(evt -> {
                    System.out.println(evt);
                });
        if (curResponse.getEventsScreenDataDto().getEvents().isEmpty()) {
            System.out.println("--no events--");
        }
    }

    private void renderQuestJournal() {
        QuestScreenDataDto screenDataDto = curResponse.getQuestScreenDataDto();
        renderQuestList("In Progress", screenDataDto.getInProgress());
        renderQuestList("Done", screenDataDto.getDone());
        renderQuestList("Failed", screenDataDto.getFailed());
    }

    private void renderQuestList(String status, List<QuestInfoDto> quests) {
        System.out.println(status + " quests:");
        quests.forEach(quest -> {
            System.out.println(quest.getTitle());
            quest.getJournal().forEach(str -> System.out.println("  - " + str));
        });
        if (quests.isEmpty()) {
            System.out.println("--empty--");
        }
    }

    private void renderResponse() {
        System.out.println(curResponse);
    }

    private void renderInventory() {
        System.out.println(curResponse.getInventoryScreenDataDto());
    }
}
