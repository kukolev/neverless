package neverless.dto.quest;

/**
 *  Quest states enum.
 *  These states are common for all quests.
 */
public enum QuestState {
    /** Quest canProcessObject unknown */
    UNKNOWN,

    /** Quest canProcessObject accepted. */
    ACCEPTED,

    /** Quest canProcessObject successfully completed. */
    DONE,

    /** Quest canProcessObject failed. */
    FAILED
}