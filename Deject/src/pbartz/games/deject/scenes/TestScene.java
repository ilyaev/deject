package pbartz.games.deject.scenes;

import pbartz.games.deject.BitmapLibrary;
import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.EntityFactory;
import pbartz.games.deject.config.GameConfig;
import pbartz.games.deject.config.LevelConfig;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class TestScene extends BasicScene {
	
	Bitmap bm = null;
	private Paint bmPaint = EntityFactory.createPaint(255, 255, 0, 0);
	
	private Bitmap drawBitmap = null;
	public TestScene(DejectSurface surface) {
		super(surface);

		bmPaint.setAntiAlias(false);
		bmPaint.setFilterBitmap(false);
		
		drawBitmap = Bitmap.createBitmap((int)(surface.widthPx / 4), (int)(surface.heightPx / 4), Config.RGB_565);
		new Canvas(drawBitmap);
	}

	@Override
	public void initScene() {
		
		
		GameConfig.loadConfig(surface);
		BitmapLibrary.loadAssets(surface);
		
		bm = BitmapLibrary.getBitmap("hammer");
		
		LevelConfig level = GameConfig.getLevelConfig(1);
		
		Log.v("LEV", level.toString());
		
	}

	@Override
	public void update(Canvas canvas, float timeDiff) {
//		drawCanvas.drawARGB(255, 255, 224, 153);
//		drawCanvas.drawBitmap(bm, 0, 0, bmPaint);
//		drawCanvas.drawCircle(50, 50, 20, bmPaint);
//		
//		
//		canvas.save();
//		canvas.scale(surface.widthPx / drawBitmap.getWidth(), surface.heightPx / drawBitmap.getHeight());
//		canvas.drawBitmap(drawBitmap, 0, 0, bmPaint);
//		canvas.restore();
		
		canvas.drawARGB(255, 255, 224, 153);
		canvas.save();
		canvas.translate(50f, 50f);
//		canvas.scale(8, 8);
		canvas.drawBitmap(bm, 0, 0 , bmPaint);
		canvas.restore();
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