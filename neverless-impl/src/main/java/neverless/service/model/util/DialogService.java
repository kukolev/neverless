package neverless.service.model.util;

import neverless.domain.model.dialog.Dialog;
import neverless.domain.model.dialog.NpcPhrase;
import neverless.domain.model.dialog.PlayerPhrase;
import neverless.domain.model.entity.mapobject.Player;
import neverless.domain.model.entity.mapobject.npc.AbstractNpc;
import neverless.context.DialogContext;
import neverless.context.EventContext;
import neverless.context.GameContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DialogService {

    @Autowired
    private GameContext gameContext;
    @Autowired
    private NpcService npcService;
    @Autowired
    private EventContext eventContext;
    @Autowired
    private DialogContext dialogContext;

    public void dialogStart(AbstractNpc npc) {
        Player player = gameContext.getPlayer();

        // simpleGet NPC's dialog
        Dialog dialog = npc.getDialog();

        // search for NPC's phrase (use predicates)
        NpcPhrase npcPhrase = getStartPhrase(dialog);

        // set data to Player
        dialogContext.add(dialog);
        dialogContext.add(npcPhrase);

        eventContext.addDialogStartEvent(player.getUniqueName(), npc.getUniqueName());
    }

    private NpcPhrase getStartPhrase(Dialog dialog) {
        // todo: throw real exception
        return dialog.getRootNpcPhrases()
                .stream()
                .filter(phrase -> phrase.getActivator().isCurrent())
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public void selectPhrase(int phraseNumber) {
        NpcPhrase npcPhrase = dialogContext.getNpcPhrase();
        if (npcPhrase == null) {
            return;
        }
        PlayerPhrase playerPhrase = npcPhrase.getAnswers().get(phraseNumber);
        playerPhrase.getAnswerEvent().execute();
        NpcPhrase nextNpcPhrase = playerPhrase.getNextNpcPhrase();
        if (nextNpcPhrase != null) {
            dialogContext.add(nextNpcPhrase);
        } else {
            dialogContext.clearDialog();
            dialogContext.clearNpcPhrase();
        }
        Player player = gameContext.getPlayer();
        eventContext.addDialogSelectPhraseEvent(player.getUniqueName(), phraseNumber);
    }
}
