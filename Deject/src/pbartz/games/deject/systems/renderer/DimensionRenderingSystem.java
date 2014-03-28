package pbartz.games.deject.systems.renderer;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.components.BitmapComponent;
import pbartz.games.deject.components.ColorComponent;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.components.RotateComponent;
import pbartz.games.deject.components.TextComponent;
import pbartz.games.deject.components.ZoomComponent;
import pbartz.games.deject.components.dimension.RectDimensionComponent;
import pbartz.games.deject.core.Component;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.core.Family;
import pbartz.games.deject.systems.IteratingSystem;
import pbartz.games.deject.systems.OrderedIteratingSystem;
import pbartz.games.deject.utils.Array;
import pbartz.games.deject.utils.ImmutableArray;

public class DimensionRenderingSystem extends OrderedIteratingSystem {

	public Canvas canvas = null;
	
	DejectSurface surface;
	
	RectDimensionComponent dimension;
	PositionComponent position;
	ColorComponent color;
	TextComponent text;
	BitmapComponent bitmap;
    RotateComponent rotate = null;
    ZoomComponent zoom = null;
    
    private ImmutableArray<Component> allComponents = new Array<Component>();
	
	@SuppressWarnings("unchecked")
	public DimensionRenderingSystem(DejectSurface surface) {
		super(Family.getFamilyFor(PositionComponent.class, RectDimensionComponent.class, ColorComponent.class));
		
		this.surface = surface;		
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		
		bitmap = null;
		rotate = null;
		text = null;
		
		allComponents = entity.getComponents();
		
		Class<? extends Component> compClass = null;
		
		int size = allComponents.getSize();
		
		for(int i = 0 ; i < size ; i++) {
			
			Component comp = allComponents.get(i);
			
			compClass = comp.getClass();
			
			if (compClass == PositionComponent.class) {
				
				position = (PositionComponent) comp;
				continue;
				
			} else if (compClass == RectDimensionComponent.class) {
				
				dimension = (RectDimensionComponent) comp;
				continue;
				
			} else if (compClass == ColorComponent.class) {
				
				color = (ColorComponent) comp;
				continue;
				
			} else if (compClass == BitmapComponent.class) {
				
				bitmap = (BitmapComponent) comp;
				continue;
				
			} else if (compClass == RotateComponent.class) {
				
				rotate = (RotateComponent) comp;
				continue;
				
			} else if (compClass == TextComponent.class) {
				
				text = (TextComponent) comp;
				continue;
				
			}
 
			
		}
		
		canvas = surface.getCanvas();
		
		if (bitmap != null) {

			renderBitmap();
			
			if (text != null) {
				
				renderText();
			
			}
			
		} else if (text != null) {

			renderText();
		
		} else {			
			
			renderRect();
		}
		
		
		
	}

	private void renderBitmap() {
		
		if (bitmap.getBitmap() == null) return;
		
 		Rect rect = dimension.getZeroRect();
		canvas.save();
		canvas.translate(position.x, position.y);
		
		if (rotate != null) {
			canvas.rotate(rotate.getAngle());
		}
		
		canvas.scale((float)dimension.getWidth() / (float)bitmap.getBitmap().getWidth(), (float)dimension.getHeight() / (float)bitmap.getBitmap().getHeight(), rect.left, rect.top);

		canvas.drawBitmap(bitmap.getBitmap(), rect.left, rect.top, color.getPaint().getAlpha() == 255 ? null : color.getPaint());
		canvas.restore();
	}

	private void renderText() {
		
		color.getPaint().setTextSize(text.getHeight() > 0 ? text.getHeight() : dimension.getHeight());
		canvas.drawText(text.getText(), position.x - (dimension.getWidth() / 2), position.y + (dimension.getHeight() / 2), color.getPaint());		
	
	}

	private void renderRect() {
		
		Rect tmpRect = dimension.getZeroRect();
		
		canvas.save();
		canvas.translate(position.x, position.y);
		if (rotate != null) {			
			canvas.rotate(rotate.getAngle());
		}
		
		if (color.getBorderPaint() == null) {
			
			if (zoom != null) {
				canvas.scale(zoom.getZoomX(), zoom.getZoomY(), tmpRect.left, tmpRect.top);
			}
			
			canvas.drawRect(tmpRect, color.getPaint());
			
		} else {
			
			int shift = surface.dp2px(1f);
			
			tmpRect.set(tmpRect.left + shift, tmpRect.top + shift, tmpRect.right - shift, tmpRect.bottom - shift);

			canvas.drawRect(((RectDimensionComponent) dimension).getZeroRect(), color.getBorderPaint());
			canvas.drawRect(tmpRect, color.getPaint());
		}
		
		canvas.restore();
	}


}