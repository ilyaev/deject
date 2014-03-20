package pbartz.games.deject.systems;

import pbartz.games.deject.components.RectInterpolationComponent;
import pbartz.games.deject.components.dimension.RectDimensionComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;

public class RectInterpolationSystem extends IteratingSystem {
	
	RectDimensionComponent dimension = null;
	RectInterpolationComponent interpolation = null;

	@SuppressWarnings("unchecked")
	public RectInterpolationSystem() {
		super(Family.getFamilyFor(RectDimensionComponent.class, RectInterpolationComponent.class));		
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		interpolation = entity.getComponent(RectInterpolationComponent.class);
		dimension = entity.getComponent(RectDimensionComponent.class);		

		interpolation.increaseTime(deltaTime);
		
		dimension.setDimension(interpolation.getCurrentWidth(), interpolation.getCurrentHeight());
		
		if (interpolation.isCompleted()) {
			
			dimension.setDimension(interpolation.getEndWidth(), interpolation.getEndHeight());

			entity.remove(RectInterpolationComponent.class);
		}

	}

}
