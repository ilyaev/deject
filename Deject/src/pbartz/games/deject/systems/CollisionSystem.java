package pbartz.games.deject.systems;

import java.util.Random;

import pbartz.games.deject.components.MovementComponent;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;

public class CollisionSystem extends IteratingSystem {
	
	PositionComponent position;
	MovementComponent movement;
	
	@SuppressWarnings("unchecked")
	public CollisionSystem() {
		super(Family.getFamilyFor(PositionComponent.class, MovementComponent.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		position = entity.getComponent(PositionComponent.class);
		movement = entity.getComponent(MovementComponent.class);
		
		Random r = new Random();
		
		if (position.x < 0) {
			movement.velocityX = r.nextInt(100);
		} else if (position.x > 480) {
			movement.velocityX = -r.nextInt(100);
		}
		
		if (position.y < 0) {
			movement.velocityY = r.nextInt(100);
		} else if (position.y > 800) {
			movement.velocityY = -r.nextInt(100);
		}
		
	}

}