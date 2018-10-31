package neverless.service.screendata;

import neverless.domain.dialog.Dialog;
import neverless.domain.dialog.NpcPhrase;
import neverless.domain.dialog.PlayerPhrase;
import neverless.domain.mapobject.Player;
import neverless.domain.mapobject.npc.AbstractNpc;
import neverless.repository.MapObjectsRepository;
import neverless.repository.PlayerRepository;
import neverless.dto.screendata.DialogScreenDataDto;
import neverless.service.core.EventContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DialogService extends AbstractService {

    @Autowired
    private MapObjectsRepository mapObjectsRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private NpcService npcService;
    @Autowired
    private EventContext eventContext;

    public void dialogStart(int npcX, int npcY) {
        Player player = playerRepository.get();

        // find NPC
        AbstractNpc npc = npcService.getNpcAtPosition(npcX, npcY, player.getLocation());

        // get NPC's dialog
        Dialog dialog = npc.getDialog();

        // search for NPC's phrase (use predicates)
        NpcPhrase npcPhrase = getStartPhrase(dialog);

        // set data to Player
        player.setDialog(dialog);
        player.setNpcPhrase(npcPhrase);

        eventContext.addDialogStartEvent(npc.getUniqueName(), npcX, npcY);
    }

    private NpcPhrase getStartPhrase(Dialog dialog) {
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
        eventContext.addDialogSelectPhraseEvent(phraseNumber);
    }
}
