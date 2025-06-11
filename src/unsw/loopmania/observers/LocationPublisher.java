package unsw.loopmania.observers;

import unsw.loopmania.entity.MovingEntity;

public interface LocationPublisher<T extends MovingEntity> {
	/**
	 * Subscribe an observer to be notified of changes from this publisher.
	 * @param observer
	 */
	public void subscribe(LocationObserver<T> observer);

	/**
	 * Unsubscribe observers from this publisher.
	 * @param observer
	 */
    public void unsubscribe(LocationObserver<T> observer);

	/**
	 * Notify all subscribed observers about this entity's change in position.
	 */
    public void notifyObservers();
}
