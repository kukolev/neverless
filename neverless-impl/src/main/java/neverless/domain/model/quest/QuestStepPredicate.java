package neverless.domain.model.quest;

@FunctionalInterface
public interface QuestStepPredicate {

    Boolean isAvailable();
}
