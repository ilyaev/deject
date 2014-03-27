package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;
import pbartz.games.deject.utils.Pool.Poolable;

public class ExpireComponent extends Component implements Poolable {
	
	float timeToLive = 0;
	float alreadyLived = 0;
	
	public void init(float ttl) {
		timeToLive = ttl * 1000;
	}
	
	public boolean isExpired() {
		return alreadyLived >= timeToLive;
	}
	
	public void tick(float diff) {
		alreadyLived += diff;
	}

	public void forceExpire() {
		alreadyLived = timeToLive;		
	}

	@Override
	public void reset() {
		alreadyLived = 0;		
	}
	
	
}
