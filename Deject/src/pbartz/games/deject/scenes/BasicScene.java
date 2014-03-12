package pbartz.games.deject.scenes;

import pbartz.games.deject.DejectSurface;
import pbartz.games.deject.core.PooledEngine;
import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class BasicScene {

	protected PooledEngine engine = null;
	protected DejectSurface surface = null;
	
	public MotionEvent touchEvent = null;
	public int touchEventType = -1;
	
	public BasicScene(DejectSurface surface) {
		this.surface = surface;
		this.engine = new PooledEngine();
	}
	
	protected boolean isInited = false;
	
	public abstract void initScene();
	
	public abstract void update(Canvas canvas, float timeDiff);

	public boolean isInited() {
		return isInited;
	}

	public void setInited(boolean isInited) {
		this.isInited = isInited;
	}
	
	public abstract void completeScene();
	
}