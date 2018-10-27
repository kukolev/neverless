package neverless.service.screendata;

import neverless.domain.dialog.quest.AbstractQuest;
import neverless.domain.event.JournalUpdateEvent;
import neverless.game.quest.QuestContainer;
import neverless.dto.screendata.quest.QuestInfoDto;
import neverless.dto.screendata.quest.QuestScreenDataDto;
import neverless.service.core.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestService extends AbstractService {

    @Autowired
    private QuestContainer repository;
    @Autowired
    private RequestContext requestContext;

    public QuestScreenDataDto getScreenData() {
        QuestScreenDataDto screenDataDto = new QuestScreenDataDto();
        repository.findAll().stream()
                .forEach(quest -> addQuestToList(screenDataDto, quest));
        return screenDataDto;
    }

    public void generateQuestEvents() {
        requestContext.findUpdatedQuests()
                .forEach(id -> {
                    AbstractQuest quest = repository.finaById(id);
                    JournalUpdateEvent event = new JournalUpdateEvent()
                            .setQuestTitle(quest.getTitle())
                            .setState(quest.getState());
                    registerEvent(event);
                });
    }

    private void addQuestToList(QuestScreenDataDto screenDataDto, AbstractQuest quest) {
        QuestInfoDto dto = mapToDto(quest);
        List<QuestInfoDto> questList;
        switch (dto.getState()) {
            case ACCEPTED: questList = screenDataDto.getInProgress(); break;
            case DONE: questList = screenDataDto.getDone(); break;
            case FAILED: questList = screenDataDto.getFailed(); break;
            default: return;
        }
        questList.add(dto);
    }

    private QuestInfoDto mapToDto(AbstractQuest quest) {
        QuestInfoDto dto = new QuestInfoDto();
        dto.setTitle(quest.getTitle());
        dto.setJournal(quest.getJournal());
        dto.setState(quest.getState());
        return dto;
    }
}
