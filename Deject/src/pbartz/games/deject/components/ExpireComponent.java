package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;

public class ExpireComponent extends Component {
	
	float timeToLive = 0;
	float alreadyLived = 0;
	
	public ExpireComponent(float ttl) {
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
	
	
}
