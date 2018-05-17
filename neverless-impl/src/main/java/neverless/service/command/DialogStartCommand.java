package neverless.service.command;

import lombok.Setter;
import neverless.domain.event.Event;
import neverless.domain.dialog.Dialog;
import neverless.domain.dialog.NpcPhrase;
import neverless.domain.mapobject.Player;
import neverless.domain.mapobject.npc.AbstractNpc;
import neverless.repository.MapObjectsRepository;
import neverless.repository.PlayerRepository;
import neverless.service.screendata.DialogService;
import neverless.service.MapObjectsHelper;
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
    private MapObjectsHelper npcService;

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
