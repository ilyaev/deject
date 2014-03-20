package pbartz.games.deject.systems.renderer;

import pbartz.games.deject.components.ScoreComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.systems.IteratingSystem;

public class HudRenderingSystem extends IteratingSystem {
	
	ScoreComponent score = null;

	@SuppressWarnings("unchecked")
	public HudRenderingSystem() {
		super(Family.getFamilyFor(ScoreComponent.class));
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		score = entity.getComponent(ScoreComponent.class);
		
		
		
	}

}
