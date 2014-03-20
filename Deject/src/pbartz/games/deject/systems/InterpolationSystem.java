package pbartz.games.deject.systems;

import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.components.PositionInterpolationComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;

public class InterpolationSystem extends IteratingSystem {
	
	PositionInterpolationComponent positionInterpolation;
	PositionComponent position;

	@SuppressWarnings("unchecked")
	public InterpolationSystem() {
		super(Family.getFamilyFor(PositionComponent.class, PositionInterpolationComponent.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		positionInterpolation = entity.getComponent(PositionInterpolationComponent.class);
		position = entity.getComponent(PositionComponent.class);		

		positionInterpolation.increaseTime(deltaTime);
		
		position.x = positionInterpolation.getCurrentX();
		position.y = positionInterpolation.getCurrentY();
		
		if (positionInterpolation.isCompleted()) {
			
			position.x = positionInterpolation.destX;
			position.y = positionInterpolation.destY;
			
			entity.remove(PositionInterpolationComponent.class);
		}
	}

}
