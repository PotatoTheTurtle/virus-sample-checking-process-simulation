package model;

/**
 * The enums for eventType that are used to control which event is happening.
 */
public enum EventType {
	/**
	 * ARR1 event type is the first arrival
	 */
	ARR1,
	/**
	 * DEP1 event type is the first departure
	 */
	DEP1,
	/**
	 * DEP2 event type is the second departure
	 */
	DEP2,
	/**
	 * DEP3 event type is the third departure
	 */
	DEP3,
	/**
	 * DEP4 event type is the fourth departure
	 */
	DEP4,
	/**
	 * SKIP is to declare that an event is skipped to the end.
	 */
	SKIP
}
