package neverless.service.command;

import lombok.Setter;
import neverless.domain.event.Event;
import neverless.domain.game.dialog.Dialog;
import neverless.domain.game.dialog.NpcPhrase;
import neverless.domain.game.mapobject.Player;
import neverless.domain.game.mapobject.npc.AbstractNpc;
import neverless.domain.repository.MapObjectsRepository;
import neverless.domain.repository.PlayerRepository;
import neverless.service.DialogService;
import neverless.service.NpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DialogStartCommand extends AbstractCommand{

    @Autowired
    private MapObjectsRepository mapObjectsRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private DialogService dialogService;
    @Autowired
    private NpcService npcService;

    @Setter
    private Integer npcX;
    @Setter
    private Integer npcY;

    @Override
    public Event onExecute(){
        // find NPC
        AbstractNpc npc = npcService.getNpcAtPosition(npcX, npcY);

        // get NPC's dialog
        Dialog dialog = npc.getDialog();

        // search for NPC's phrase (use predicates)
        NpcPhrase npcPhrase = dialogService.getStartPhrase(dialog);

        // set data to Player
        Player player = playerRepository.get();
        player.setDialog(dialog);
        player.setNpcPhrase(npcPhrase);

        return eventFactory.createDialogStartedEvent(npc.getUniqueName());
    }
}
