package pbartz.games.deject.systems;

import pbartz.games.deject.components.ExpireComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.signals.Signal;

public class ExpireSystem extends IteratingSystem {
	
	ExpireComponent expire;
	
	public Signal<Entity> entityExpired = new Signal<Entity>();
	
	@SuppressWarnings("unchecked")
	public ExpireSystem() {
		super(Family.getFamilyFor(ExpireComponent.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		expire = entity.getComponent(ExpireComponent.class);
		
		expire.tick(deltaTime);
		
		if (expire.isExpired()) {
			
			entityExpired.dispatch(entity);
			engine.removeEntity(entity);
			
		}
		
	}

}