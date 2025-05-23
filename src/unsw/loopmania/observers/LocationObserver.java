package unsw.loopmania.observers;

import unsw.loopmania.entity.MovingEntity;

public interface LocationObserver<T extends MovingEntity> {
	public void update(T entity);
}
