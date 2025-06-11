package unsw.loopmania.observers;

import unsw.loopmania.entity.MovingEntity;

public interface LocationObserver<T extends MovingEntity> {
	/**
	 * Make changes to the publisher entity once the observer is notified of their location change.
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * Checks whether this observer should exist in the game model.
	 * @return
	 */
	public boolean shouldObserverExist();
}
