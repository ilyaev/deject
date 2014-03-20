package pbartz.games.deject.systems;

import pbartz.games.deject.components.MovementComponent;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;

public class MovementSystem extends IteratingSystem {
	PositionComponent position;
	MovementComponent movement;

	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(Family.getFamilyFor(PositionComponent.class, MovementComponent.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		position = entity.getComponent(PositionComponent.class);
		movement = entity.getComponent(MovementComponent.class);

		position.x += movement.velocityX * deltaTime;
		position.y += movement.velocityY * deltaTime;
		

	}

}