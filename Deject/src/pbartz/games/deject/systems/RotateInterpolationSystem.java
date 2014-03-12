package pbartz.games.deject.systems;

import pbartz.games.deject.components.RotateComponent;
import pbartz.games.deject.components.RotateInterpolationComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;

public class RotateInterpolationSystem extends IteratingSystem {

	RotateInterpolationComponent rotate_interpolation = null;
	RotateComponent rotate = null;
	
	@SuppressWarnings("unchecked")
	public RotateInterpolationSystem() {
		super(Family.getFamilyFor(RotateInterpolationComponent.class));
		
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		rotate = entity.getComponent(RotateComponent.class);
		rotate_interpolation = entity.getComponent(RotateInterpolationComponent.class);
		
		rotate_interpolation.increaseTime(deltaTime);
		
		rotate.setAngle(rotate_interpolation.getCurrentAngle());
		
		if (rotate_interpolation.isCompleted()) {
			
			rotate.setAngle(rotate_interpolation.getEndAngle());
			
			entity.remove(RotateInterpolationComponent.class);
			
		}
		
	}

}
