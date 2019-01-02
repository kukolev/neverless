package neverless.dto.quest;

/**
 *  Quest states enum.
 *  These states are common for all quests.
 */
public enum QuestState {
    /** Quest is unknown */
    UNKNOWN,

    /** Quest is accepted. */
    ACCEPTED,

    /** Quest is successfully completed. */
    DONE,

    /** Quest is failed. */
    FAILED
}