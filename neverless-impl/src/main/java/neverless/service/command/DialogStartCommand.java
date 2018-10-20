package neverless.service.command;

import neverless.domain.dialog.Dialog;
import neverless.domain.dialog.NpcPhrase;
import neverless.domain.mapobject.Player;
import neverless.domain.mapobject.npc.AbstractNpc;
import neverless.repository.MapObjectsRepository;
import neverless.repository.PlayerRepository;
import neverless.service.screendata.DialogService;
import neverless.service.MapObjectsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DialogStartCommand extends AbstractCommand<DialogStartParams>{

    @Autowired
    private MapObjectsRepository mapObjectsRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private DialogService dialogService;
    @Autowired
    private MapObjectsHelper npcService;

    @Override
    public void execute(DialogStartParams params){
        // find NPC
        AbstractNpc npc = npcService.getNpcAtPosition(params.getNpcX(), params.getNpcY());

        // get NPC's dialog
        Dialog dialog = npc.getDialog();

        // search for NPC's phrase (use predicates)
        NpcPhrase npcPhrase = dialogService.getStartPhrase(dialog);

        // set data to Player
        Player player = playerRepository.get();
        player.setDialog(dialog);
        player.setNpcPhrase(npcPhrase);

        registerEvent(eventFactory.createDialogStartEvent(npc.getUniqueName()));
    }
}
