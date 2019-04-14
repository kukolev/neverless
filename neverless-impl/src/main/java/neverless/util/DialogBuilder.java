package neverless.util;

import neverless.domain.dialog.Dialog;
import neverless.domain.dialog.NpcPhrase;
import neverless.domain.dialog.PlayerPhrase;
import neverless.domain.dialog.event.AbstractDialogEvent;
import neverless.domain.dialog.predicate.AvailablePlayerPhrasePredicate;
import neverless.domain.dialog.predicate.NpcStartingPhrasePredicate;

import java.util.ArrayList;
import java.util.List;

public class DialogBuilder {

    public static final AvailablePlayerPhrasePredicate ALWAYS_AVAILABLE = () -> true;
    public static final AbstractDialogEvent EMPTY_EVENT = () -> {};
    public static final NpcStartingPhrasePredicate ALWAYS_START = () -> true;

    private NpcPhrase phrase;
    private Dialog dialog;

    public DialogBuilder(Dialog dialog) {
        this.dialog = dialog;
    }

    /**
     * Creates root NPC's phrase.
     *
     * @param text          NPC's root phrase text.
     * @param activator     predicates for consider of should we use this phrase as starting
     *                      phrase in dialog with Player.
     * @return              reference to this.
     */
    public DialogBuilder create(String text,
                                NpcStartingPhrasePredicate activator) {
        phrase = new NpcPhrase()
                .setPhraseText(text)
                .setActivator(activator);
        dialog.getRootNpcPhrases().add(phrase);
        return this;
    }

    /**
     * Creates and adds player answer to npc's phrase at some concrete level.
     *
     * @param level         level in which npc's phrase places.
     * @param text          Player's phrase text.
     * @param activator     predicate for consider of could this phrase able to be selected by Player.
     * @param event         event, that should happened if Player will select this phrase.
     * @return              reference to this.
     */
    public DialogBuilder addPlayerAnswer(int level,
                                         String text,
                                         AvailablePlayerPhrasePredicate activator,
                                         AbstractDialogEvent event) {
        NpcPhrase parentPhrase = getNpcPhraseAtLevel(level);
        List<PlayerPhrase> answers = parentPhrase.getAnswers();
        if (answers == null) {
            answers = new ArrayList<>();
            parentPhrase.setAnswers(answers);
        }

        parentPhrase.getAnswers().add(
                new PlayerPhrase()
                        .setPhraseText(text)
                        .setActivator(activator)
                        .setAnswerEvent(event));
        return this;
    }

    public DialogBuilder addPlayerAnswer(int level,
                                         String text) {
        return addPlayerAnswer(level, text, ALWAYS_AVAILABLE, EMPTY_EVENT);
    }

    /**
     * Creates and adds NPC's phrase at some concrete level.
     *
     * @param level         level of Player's parent answer.
     * @param text          NPC's root phrase text.
     */
    public DialogBuilder addNpcAnswer(int level,
                                      String text) {
        PlayerPhrase parentPhrase = getPlayerPhraseAtLevel(level);
        parentPhrase.setNextNpcPhrase(
                new NpcPhrase()
                        .setPhraseText(text)
                        .setActivator(() -> false));
        return this;
    }

    private NpcPhrase getNpcPhraseAtLevel(int level) {
        NpcPhrase curPhrase = phrase;
        for(int i = 2; i <= level; i+= 2 ) {
            int ansCount = curPhrase.getAnswers().size();
            curPhrase = curPhrase.getAnswers().get(ansCount - 1).getNextNpcPhrase();
        }
        return curPhrase;
    }

    private PlayerPhrase getPlayerPhraseAtLevel(int level) {
        NpcPhrase parentPhrase = getNpcPhraseAtLevel(level - 1);
        int ansCount = parentPhrase.getAnswers().size();
        return parentPhrase.getAnswers().get(ansCount - 1);
    }

    public NpcPhrase get() {
        return phrase;
    }
}
