package neverless.context;

import neverless.domain.dialog.Dialog;
import neverless.domain.dialog.NpcPhrase;
import org.springframework.stereotype.Component;

/**
 * Context of current dialog.
 * Stores dialog state for game session.
 */
@Component
public class DialogContext {

    private DialogPack pack;

    /**
     * Adds dialog to context.
     *
     * @param dialog dialog.
     */
    public void add(Dialog dialog) {
        DialogPack pack = getOrCreatePack();
        pack.setDialog(dialog);
    }

    /**
     * Adds npc phrase to context
     *
     * @param npcPhrase npc phrase.
     */
    public void add(NpcPhrase npcPhrase) {
        DialogPack pack = getOrCreatePack();
        pack.setNpcPhrase(npcPhrase);
    }

    /**
     * Adds couple of dialog and npc phrase to context.
     *
     * @param dialog    dialog.
     * @param npcPhrase npc phrase.
     */
    public void add(Dialog dialog, NpcPhrase npcPhrase) {
        DialogPack pack = getOrCreatePack();
        pack
                .setDialog(dialog)
                .setNpcPhrase(npcPhrase);
    }

    /**
     * Returns dialog from context
     */
    public Dialog getDialog() {
        DialogPack pack = getOrCreatePack();
        return pack.getDialog();
    }

    /**
     * Returns npc phrase from context
     */
    public NpcPhrase getNpcPhrase() {
        DialogPack pack = getOrCreatePack();
        return pack.getNpcPhrase();
    }

    /**
     * Clears dialog
     */
    public void clearDialog() {
        DialogPack pack = getOrCreatePack();
        pack.setDialog(null);
    }

    /**
     * Clears npc phrase
     */
    public void clearNpcPhrase() {
        DialogPack pack = getOrCreatePack();
        pack.setNpcPhrase(null);
    }

    /**
     * Returns dialog pack from cache. Creates the pack if absent.
     */
    private DialogPack getOrCreatePack() {
        if (pack == null) {
            pack = new DialogPack();
        }
        return pack;
    }
}