package pbartz.games.deject.systems;

import pbartz.games.deject.components.ZoomComponent;
import pbartz.games.deject.components.ZoomInterpolationComponent;
import pbartz.games.deject.components.dimension.RectDimensionComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;

public class ZoomInterpolationSystem extends IteratingSystem {
	
	RectDimensionComponent dimension;
	ZoomInterpolationComponent interpolation;

	@SuppressWarnings("unchecked")
	public ZoomInterpolationSystem() {
		super(Family.getFamilyFor(RectDimensionComponent.class, ZoomInterpolationComponent.class, ZoomComponent.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		dimension = entity.getComponent(RectDimensionComponent.class);
		interpolation = entity.getComponent(ZoomInterpolationComponent.class);
		
		interpolation.increaseTime(deltaTime);
		
		dimension.setDimension(interpolation.getCurrentX(), interpolation.getCurrentY());
		
		if (interpolation.isCompleted()) {
			
			dimension.setDimension(interpolation.destX, interpolation.destY);
			
			entity.remove(ZoomInterpolationComponent.class);
		}
		
	}

}