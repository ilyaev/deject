package pbartz.games.deject.systems;

import java.util.Random;

import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.components.PositionShakeComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;

public class PositionShakeSystem extends IteratingSystem {
	
	PositionComponent position = null;
	PositionShakeComponent shake = null;
	
	Random r = new Random();

	@SuppressWarnings("unchecked")
	public PositionShakeSystem() {
		super(Family.getFamilyFor(PositionComponent.class, PositionShakeComponent.class));		
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		position = entity.getComponent(PositionComponent.class);
		shake = entity.getComponent(PositionShakeComponent.class);		
		
		shake.increaseTime(deltaTime);
		
		
		float newX = position.originalX + (r.nextFloat() * shake.getShiftX()*2 - shake.getShiftX() );
		float newY = position.originalY + (r.nextFloat() * shake.getShiftY()*2 - shake.getShiftY() );
		
		
		if (shake.isCompleted()) {
			
			entity.remove(PositionShakeComponent.class);
			position.setPosition(position.getOriginalX(), position.getOriginalY());
			
		} else {
			
			position.setPosition(newX,  newY);
			
		}

	}

}