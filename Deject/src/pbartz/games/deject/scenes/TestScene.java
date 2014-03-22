package pbartz.games.deject.scenes;

import java.util.Random;

import pbartz.games.deject.BitmapLibrary;
import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.EntityFactory;
import pbartz.games.deject.components.ColorComponent;
import pbartz.games.deject.components.PositionComponent;
import pbartz.games.deject.components.dimension.RectDimensionComponent;
import pbartz.games.deject.config.GameConfig;
import pbartz.games.deject.config.LevelConfig;
import pbartz.games.deject.core.Entity;
import pbartz.games.deject.systems.ColorInterpolationSystem;
import pbartz.games.deject.systems.ExpireSystem;
import pbartz.games.deject.systems.InterpolationSystem;
import pbartz.games.deject.systems.RectInterpolationSystem;
import pbartz.games.deject.systems.RotateInterpolationSystem;
import pbartz.games.deject.systems.ZoomInterpolationSystem;
import pbartz.games.deject.systems.renderer.DimensionRenderingSystem;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class TestScene extends BasicScene {
	
	Bitmap bm = null;
	private Paint whitePaint;

	public TestScene(DejectSurface surface) {
		super(surface);

	}

	@Override
	public void initScene() {
			
		whitePaint = new Paint();
		whitePaint.setARGB(255, 255, 255, 255);
		
		// interpolation
		engine.addSystem(new InterpolationSystem());
		engine.addSystem(new ColorInterpolationSystem());
		engine.addSystem(new RotateInterpolationSystem());
		engine.addSystem(new ZoomInterpolationSystem());
		engine.addSystem(new RectInterpolationSystem());
		
		// expiration
		engine.addSystem(new ExpireSystem());
		
		// rendering
		engine.addSystem(new DimensionRenderingSystem(this.surface));

		
	}

	

	@Override
	public void update(Canvas canvas, float timeDiff) {
		canvas.drawARGB(255, 0, 0, 0);
		canvas.save();
		canvas.translate(surface.widthPx / 2, surface.heightPx / 2);
		engine.update(timeDiff);
		canvas.restore();
		canvas.drawText("FPS: " + Integer.toString(surface.fps), 0, 40, whitePaint);
	}

	@Override
	public void completeScene() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transitOut(BasicScene tScene) {
		// TODO Auto-generated method stub
		
	}

}