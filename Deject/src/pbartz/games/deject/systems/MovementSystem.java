package pbartz.games.deject.systems;

import pbartz.games.deject.components.MovementComponent;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;

public class MovementSystem extends IteratingSystem {
	PositionComponent position;
	MovementComponent movement;
	
	private float gravity = 2500;

	@SuppressWarnings("unchecked")
	public MovementSystem(float gravity) {
		super(Family.getFamilyFor(PositionComponent.class, MovementComponent.class));
		this.gravity = gravity;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		position = entity.getComponent(PositionComponent.class);
		movement = entity.getComponent(MovementComponent.class);

		position.x += movement.velocityX * (deltaTime / 1000);
		position.y += movement.velocityY * (deltaTime / 1000);
		
		movement.velocityY += gravity * (deltaTime / 1000);
		

	}

}