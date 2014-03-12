package pbartz.games.deject.systems;

import pbartz.games.deject.components.ZoomComponent;
import pbartz.games.deject.components.ZoomInterpolationComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;

public class ZoomInterpolationSystem extends IteratingSystem {
	
	ZoomComponent zoom;
	ZoomInterpolationComponent interpolation;

	@SuppressWarnings("unchecked")
	public ZoomInterpolationSystem() {
		super(Family.getFamilyFor(ZoomComponent.class, ZoomInterpolationComponent.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		zoom = entity.getComponent(ZoomComponent.class);
		interpolation = entity.getComponent(ZoomInterpolationComponent.class);
		
		interpolation.increaseTime(deltaTime);
		
		zoom.setZoomX(interpolation.getCurrentX());
		zoom.setZoomY(interpolation.getCurrentY());
		
		if (interpolation.isCompleted()) {
			
			
			zoom.setZoomX(interpolation.destX);
			zoom.setZoomY(interpolation.destY);
			
			entity.remove(ZoomInterpolationComponent.class);
		}
		
	}

}