package neverless.service.core;

import neverless.domain.dialog.Dialog;
import neverless.domain.dialog.NpcPhrase;
import neverless.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DialogContext {

    @Autowired
    private SessionUtil sessionUtil;

    private Map<String, DialogPack> packs = new ConcurrentHashMap<>();

    public void add(Dialog dialog) {
        DialogPack pack = getOrCreatePack();
        pack.setDialog(dialog);
    }

    public void add(NpcPhrase npcPhrase) {
        DialogPack pack = getOrCreatePack();
        pack.setNpcPhrase(npcPhrase);
    }

    public void add(Dialog dialog, NpcPhrase npcPhrase) {
        DialogPack pack = getOrCreatePack();
        pack
                .setDialog(dialog)
                .setNpcPhrase(npcPhrase);
    }

    public Dialog getDialog() {
        DialogPack pack = getOrCreatePack();
        return pack.getDialog();
    }

    public NpcPhrase getNpcPhrase() {
        DialogPack pack = getOrCreatePack();
        return pack.getNpcPhrase();
    }

    public void clearDialog() {
        DialogPack pack = getOrCreatePack();
        pack.setDialog(null);
    }

    public void clearNpcPhrase() {
        DialogPack pack = getOrCreatePack();
        pack.setNpcPhrase(null);
    }

    private DialogPack getOrCreatePack() {
        String key = sessionUtil.getCurrentSessionId();
        DialogPack pack = packs.get(key);
        if (pack == null) {
            pack = new DialogPack();
            packs.put(key, pack);
        }
        return pack;
    }
}

