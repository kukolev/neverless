package neverless.domain.dialog.quest;

@FunctionalInterface
public interface QuestStepPredicate {

    Boolean isAvailable();
}
