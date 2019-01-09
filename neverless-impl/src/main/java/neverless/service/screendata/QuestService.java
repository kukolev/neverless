package neverless.service.screendata;

import neverless.domain.quest.AbstractQuest;
import neverless.domain.quest.QuestContainer;
import neverless.dto.quest.QuestInfoDto;
import neverless.dto.quest.QuestScreenDataDto;
import neverless.context.EventContext;
import neverless.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestService {

    @Autowired
    private QuestContainer repository;
    @Autowired
    private RequestContext requestContext;
    @Autowired
    private EventContext eventContext;

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
                    eventContext.addJournalUpdateEvent(
                            quest.getTitle(),
                            quest.getState());
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
