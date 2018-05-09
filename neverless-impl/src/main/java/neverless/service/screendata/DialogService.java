package neverless.service.screendata;

import lombok.AllArgsConstructor;
import neverless.domain.dialog.Dialog;
import neverless.domain.dialog.NpcPhrase;
import neverless.domain.dialog.PlayerPhrase;
import neverless.domain.mapobject.Player;
import neverless.repository.PlayerRepository;
import neverless.dto.screendata.DialogScreenDataDto;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DialogService {

    private PlayerRepository playerRepository;

    public NpcPhrase getStartPhrase(Dialog dialog) {
        // todo: throw real exception
        return dialog.getRootNpcPhrases()
                .stream()
                .filter(phrase -> phrase.getActivator().isCurrent())
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public DialogScreenDataDto getScreenData() {
        Player player = playerRepository.get();
        NpcPhrase npcPhrase = player.getNpcPhrase();
        if (npcPhrase == null) {
            return new DialogScreenDataDto();
        }
        return new DialogScreenDataDto()
                .setNpcPhrase(npcPhrase.getPhraseText())
                .setAnswers(npcPhrase.getAnswers()
                .stream()
                .map(PlayerPhrase::getPhraseText)
                .collect(Collectors.toList()));
    }

    public void selectPhrase(int phraseNumber) {
        Player player = playerRepository.get();
        NpcPhrase npcPhrase = player.getNpcPhrase();
        if (npcPhrase == null) {
            return;
        }
        PlayerPhrase playerPhrase = npcPhrase.getAnswers().get(phraseNumber);
        playerPhrase.getAnswerEvent().execute();
        NpcPhrase nextNpcPhrase = playerPhrase.getNextNpcPhrase();
        if (nextNpcPhrase != null) {
            player.setNpcPhrase(nextNpcPhrase);
        } else {
            player.setDialog(null);
            player.setNpcPhrase(null);
        }
    }
}
