package pbartz.games.deject.systems;

import pbartz.games.deject.components.ColorComponent;
import pbartz.games.deject.components.ColorInterpolationComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;

public class ColorInterpolationSystem extends IteratingSystem {

	ColorComponent color;
	ColorInterpolationComponent interpolation;
	
	@SuppressWarnings("unchecked")
	public ColorInterpolationSystem() {
		super(Family.getFamilyFor(ColorComponent.class, ColorInterpolationComponent.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		color = entity.getComponent(ColorComponent.class);
		interpolation = entity.getComponent(ColorInterpolationComponent.class);
		
		interpolation.increaseTime(deltaTime);
		
		color.setPaint(interpolation.getCurrentPaint());
		
		if (interpolation.isCompleted()) {
			entity.remove(ColorInterpolationComponent.class);
		}
		
	}

}