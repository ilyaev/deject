package pbartz.games.deject.systems;

import pbartz.games.deject.components.FontSizeInterpolationComponent;
import pbartz.games.deject.components.MultiplierInterpolationComponent;
import pbartz.games.deject.components.TextComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.core.Interpolation;

public class FontSizeInterpolationSystem extends IteratingSystem {
	
	TextComponent text;
	FontSizeInterpolationComponent interpolation;

	@SuppressWarnings("unchecked")
	public FontSizeInterpolationSystem() {
		super(Family.getFamilyFor(TextComponent.class, FontSizeInterpolationComponent.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		text = entity.getComponent(TextComponent.class);
		interpolation = entity.getComponent(FontSizeInterpolationComponent.class);
		
		interpolation.increaseTime(deltaTime);
		
		float t = interpolation.getTime();
		float b = interpolation.getStartSize();
		float c = (interpolation.getEndSize() - interpolation.getStartSize());
		float d = interpolation.getSpeed();
		
		text.setHeight(Interpolation.calculateCurrentValue(interpolation.getType(), t, b, c, d));
		
		if (interpolation.isCompleted()) {
			
			text.setHeight(interpolation.getEndSize());
			
			entity.remove(FontSizeInterpolationComponent.class);
			
		}
		
	}

}