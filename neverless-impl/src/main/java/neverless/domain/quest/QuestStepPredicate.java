package neverless.domain.quest;

@FunctionalInterface
public interface QuestStepPredicate {

    Boolean isAvailable();
}
