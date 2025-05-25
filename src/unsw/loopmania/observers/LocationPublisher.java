package unsw.loopmania.observers;

import unsw.loopmania.entity.MovingEntity;

public interface LocationPublisher<T extends MovingEntity> {
	public void subscribe(LocationObserver<T> observer);
    public void unsubscribe(LocationObserver<T> observer);
    public void notifyObservers();
}
