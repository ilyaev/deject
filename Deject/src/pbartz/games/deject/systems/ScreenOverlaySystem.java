package pbartz.games.deject.systems;

import android.graphics.Canvas;
import android.graphics.Rect;
import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.components.ColorComponent;
import pbartz.games.deject.components.ScreenOverlayComponent;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;

public class ScreenOverlaySystem extends IteratingSystem {
	
	ScreenOverlayComponent overlay = null;
	ColorComponent color = null;
	DejectSurface surface = null;
	
	Rect screenRect = null;

	@SuppressWarnings("unchecked")
	public ScreenOverlaySystem(DejectSurface surface) {
		super(Family.getFamilyFor(ScreenOverlayComponent.class, ColorComponent.class));
		
		this.surface = surface;		
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		color = entity.getComponent(ColorComponent.class);
		
		if (screenRect == null) {
			screenRect = new Rect();
			screenRect.set(0, 0, (int)surface.widthPx, (int)surface.heightPx);
		}
		
		surface.getCanvas().drawRect(screenRect, color.getPaint());		
		
	}

}