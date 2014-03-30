package pbartz.games.deject.systems;

import pbartz.games.deject.components.MultiplierComponent;
import pbartz.games.deject.components.MultiplierInterpolationComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.core.Interpolation;

public class MultiplierInterpolationSystem extends IteratingSystem {
	
	MultiplierComponent multiplier;
	
	MultiplierInterpolationComponent interpolation;

	@SuppressWarnings("unchecked")
	public MultiplierInterpolationSystem() {
		super(Family.getFamilyFor(MultiplierComponent.class, MultiplierInterpolationComponent.class));		
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		multiplier = entity.getComponent(MultiplierComponent.class);
		interpolation = entity.getComponent(MultiplierInterpolationComponent.class);
		
		interpolation.increaseTime(engine.lastRealTimeDiff);
		
		float t = interpolation.getTime();
		float b = interpolation.getStart_multiplier();
		float c = (interpolation.getEnd_multiplier() - interpolation.getStart_multiplier());
		float d = interpolation.getSpeed();
		
		multiplier.setMultiplier(Interpolation.calculateCurrentValue(interpolation.getType(), t, b, c, d));
		
		if (interpolation.isCompleted()) {
			
			multiplier.setMultiplier(interpolation.getEnd_multiplier());
			
			entity.remove(MultiplierInterpolationComponent.class);
			
		}

	}

}
