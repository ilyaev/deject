package pbartz.games.deject.systems;

import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.EntityFactory;
import pbartz.games.deject.components.LevelInfoComponent;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.components.PositionInterpolationComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.core.Interpolation;

public class LevelInfoSystem extends IteratingSystem {
	
	LevelInfoComponent levelInfo = null;
	
	DejectSurface surface = null;

	@SuppressWarnings("unchecked")
	public LevelInfoSystem(DejectSurface surface) {
		super(Family.getFamilyFor(LevelInfoComponent.class));
		this.surface = surface;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		levelInfo = entity.getComponent(LevelInfoComponent.class);
		
		if (levelInfo.getState() != LevelInfoComponent.STATE_WAITING && levelInfo.isNextEvent(deltaTime)) {
			
			if (levelInfo.getState() == LevelInfoComponent.STATE_NOT_INITED) {
				
				PositionComponent posComp = engine.createComponent(PositionComponent.class);
				posComp.init(surface.dp2px(EntityFactory.levelPanelX),
						surface.dp2px(EntityFactory.levelPanelY - EntityFactory.levelPanelHeight * 2));
			
				entity.add(EntityFactory.getPositionInterpolationComponent(engine, 
					posComp,
					surface.dp2px(EntityFactory.levelPanelX),
					surface.dp2px(EntityFactory.levelPanelY),
					levelInfo.getSpeedUp(),
					Interpolation.EASE_IN						
				));
				
				levelInfo.setState(LevelInfoComponent.STATE_GOING_UP);
				
			} else if (levelInfo.getState() == LevelInfoComponent.STATE_GOING_UP) {
				
				levelInfo.setState(LevelInfoComponent.STATE_WAITING);
				
			} else if (levelInfo.getState() == LevelInfoComponent.STATE_GO_DOWN) {
				
				PositionComponent posComp2 = engine.createComponent(PositionComponent.class);
				posComp2.init(surface.dp2px(EntityFactory.levelPanelX),
						surface.dp2px(EntityFactory.levelPanelY));
				
				entity.add(EntityFactory.getPositionInterpolationComponent(engine, 
					posComp2,
					surface.dp2px(EntityFactory.levelPanelX),
					surface.dp2px(EntityFactory.levelPanelY - EntityFactory.levelPanelHeight * 2),
					levelInfo.getSpeedUp(),
					Interpolation.EASE_OUT					
				));
					
				levelInfo.setState(LevelInfoComponent.STATE_GOING_DOWN);
				
			} else if (levelInfo.getState() == LevelInfoComponent.STATE_GOING_DOWN) {
				
				levelInfo.setState(LevelInfoComponent.STATE_FORCE_REMOVE);
				
			} else if (levelInfo.getState() == LevelInfoComponent.STATE_FORCE_REMOVE) {
				
				engine.removeEntity(entity);
				engine.getSystem(AISystem.class).levelInfo = null;
				
			}
			
		}
		
	}

	public LevelInfoComponent getLevelInfo() {
		return levelInfo;
	}

	public void setLevelInfo(LevelInfoComponent levelInfo) {
		this.levelInfo = levelInfo;
	}
	
	

}
