package pbartz.games.deject.systems;

import pbartz.games.deject.components.RadialPositionComponent;
import pbartz.games.deject.components.RadialPositionInterpolationComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.core.Interpolation;

public class RadialPositionInterpolationSystem extends IteratingSystem {
	
	RadialPositionComponent radial = null;
	RadialPositionInterpolationComponent interpolation = null;

	@SuppressWarnings("unchecked")
	public RadialPositionInterpolationSystem() {
		super(Family.getFamilyFor(RadialPositionComponent.class, RadialPositionInterpolationComponent.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		radial = entity.getComponent(RadialPositionComponent.class);
		interpolation = entity.getComponent(RadialPositionInterpolationComponent.class);
		
		interpolation.increaseTime(deltaTime);
		
		float t = interpolation.getTime();
		float b = interpolation.getStartDistance();
		float c = (interpolation.getEndDistance() - interpolation.getStartDistance());
		float d = interpolation.getSpeed();
		
		radial.setDistance(Interpolation.calculateCurrentValue(interpolation.getType(), t, b, c, d));
		
		b = interpolation.getStartAngle();
		c = (interpolation.getEndAngle() - interpolation.getStartAngle());
		
		radial.setAngle(Interpolation.calculateCurrentValue(interpolation.getType(), t, b, c, d));
		
		if (interpolation.isCompleted()) {
			
			radial.setAngle(interpolation.getEndAngle());
			radial.setDistance(interpolation.getEndDistance());
			
			entity.remove(RadialPositionInterpolationComponent.class);
			engine.removeEntity(entity);
			
		}

	}

}
