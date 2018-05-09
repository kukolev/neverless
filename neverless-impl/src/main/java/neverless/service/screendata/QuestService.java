package neverless.service.screendata;

import neverless.domain.dialog.quest.AbstractQuest;
import neverless.game.quest.QuestContainer;
import neverless.dto.screendata.quest.QuestInfoDto;
import neverless.dto.screendata.quest.QuestScreenDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestService {

    @Autowired
    private QuestContainer repository;

    public QuestScreenDataDto getScreenData() {
        QuestScreenDataDto screenDataDto = new QuestScreenDataDto();
        repository.findAll().stream()
                .forEach(quest -> addQuestToList(screenDataDto, quest));
        return screenDataDto;
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
