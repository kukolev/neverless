package neverless.service.screendata;

import neverless.domain.dialog.Dialog;
import neverless.domain.dialog.NpcPhrase;
import neverless.domain.dialog.PlayerPhrase;
import neverless.domain.entity.mapobject.Player;
import neverless.domain.entity.mapobject.npc.AbstractNpc;
import neverless.repository.MapObjectsRepository;
import neverless.repository.PlayerRepository;
import neverless.dto.screendata.DialogScreenDataDto;
import neverless.service.core.DialogContext;
import neverless.service.core.EventContext;
import neverless.service.core.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DialogService extends AbstractService {

    @Autowired
    private MapObjectsRepository mapObjectsRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private NpcService npcService;
    @Autowired
    private EventContext eventContext;
    @Autowired
    private DialogContext dialogContext;

    private AbstractNpc nnn;

    public void dialogStart(int npcX, int npcY) {
        Player player = playerRepository.get();

        // find NPC
        AbstractNpc npc = npcService.getNpcAtPosition(npcX, npcY, player.getLocation());
        nnn = npc;

        // simpleGet NPC's dialog
        Dialog dialog = npc.getDialog();

        // search for NPC's phrase (use predicates)
        NpcPhrase npcPhrase = getStartPhrase(dialog);

        // set data to Player
        dialogContext.add(dialog);
        dialogContext.add(npcPhrase);

        eventContext.addDialogStartEvent(npc.getId().getUniqueName(), npcX, npcY);
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
        NpcPhrase npcPhrase = dialogContext.getNpcPhrase();
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
        NpcPhrase npcPhrase = dialogContext.getNpcPhrase();
        if (npcPhrase == null) {
            return;
        }
        PlayerPhrase playerPhrase = npcPhrase.getAnswers().get(phraseNumber);
        playerPhrase.getAnswerEvent().execute();
        mapObjectsRepository.save(nnn);
        NpcPhrase nextNpcPhrase = playerPhrase.getNextNpcPhrase();
        if (nextNpcPhrase != null) {
            dialogContext.add(nextNpcPhrase);
        } else {
            dialogContext.clearDialog();
            dialogContext.clearNpcPhrase();
        }
        eventContext.addDialogSelectPhraseEvent(phraseNumber);
    }
}
