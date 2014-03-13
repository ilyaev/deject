package pbartz.games.deject;

import pbartz.games.deject.core.Engine;
import pbartz.games.deject.scenes.BasicScene;
import pbartz.games.deject.scenes.GameScene;
import pbartz.games.deject.scenes.StartScene;
import pbartz.games.deject.scenes.TestScene;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DejectSurface extends SurfaceView implements Runnable {
	
	private final static int 	MAX_FPS = 60;
    private final static int	FRAME_PERIOD = 1000 / MAX_FPS;
    
    Engine engine = null;
    
    SurfaceHolder sHolder;
	Thread sThread = null;
	boolean isRunning = false;
	
	Context sContext = null;
	public Typeface mFace;
	
	BasicScene scene = null;
	
	BasicScene gameScene = null;
	BasicScene startScene = null; 
	
	
	public Canvas surfaceCanvas = null;
	public MotionEvent touchEvent;
	public int touchEventType = -1;
	public int fps = 0;
	
	public Context context = null;
	
	public final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
	
	public final float widthDp = displayMetrics.widthPixels / displayMetrics.density;
	public final float heightDp = displayMetrics.heightPixels / displayMetrics.density;
	public final float widthPx = displayMetrics.widthPixels;
	public final float heightPx = displayMetrics.heightPixels;
	
	private Canvas canvas;
	
	public DejectSurface(Context context) {
		super(context);
		sHolder = getHolder();
		
		this.context = context;
		
		scene = new StartScene(this);
		scene.initScene();		
		
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		
		long timeDiff = 0;		// the time it took for the cycle to execute

		int frames = 0;
		
		long laststamp = System.currentTimeMillis();
		
		while (isRunning) {
			if (!sHolder.getSurface().isValid()) {
				continue;
			}
			
			long beginTime = 0;		// the time when the cycle begun
			int sleepTime;		// ms to sleep (<0 if we're behind)

			sleepTime = 0;	
			
			Canvas canvas = null;
			
			try {
				
				beginTime = System.currentTimeMillis();
				
				if (frames == 0) {
					laststamp = beginTime;
				}
				
				if (beginTime - laststamp >= 1000) {
					fps = frames;
					frames = 0;
					laststamp = beginTime;
				}
				
				canvas = sHolder.lockCanvas();		
				
				synchronized (sHolder) {
					update(canvas, timeDiff);
				}				

			} finally {
				if (canvas != null) {
					sHolder.unlockCanvasAndPost(canvas);				
				}
				
				timeDiff = System.currentTimeMillis() - beginTime;
				
				sleepTime = (int)(FRAME_PERIOD - timeDiff);
				if (sleepTime > 0) {
					try {
						sThread.sleep(sleepTime);
					} catch (InterruptedException e) {}
				}
				
				timeDiff = System.currentTimeMillis() - beginTime;
				frames += 1;				
			}
		}
		
	}
	
	public void pause() {
		isRunning = false;
		
		while (true) {
		
			try {
				sThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			break;
			
		}

		sThread = null;
	}

	public void resume() {
		isRunning = true;
		sThread = new Thread(this);
		sThread.start();
	}
		
	private void update(Canvas canvas, long timeDiff) {
		this.surfaceCanvas = canvas;
		this.canvas = canvas;
		scene.update(canvas, timeDiff);
	}
	
	public void transitScene(BasicScene tScene) {
		
		if (scene != null) {
			
			scene.transitOut(tScene);
			
		}
		
	}

	public void setScene(BasicScene nScene) {
		
		if (scene != null) {
			scene.completeScene();
		}
		
		isRunning = false;
		
		scene = nScene;
		scene.initScene();		
		
		isRunning = true;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}

	public void processTouch(int eventType, MotionEvent event) {
		touchEvent = event;
		touchEventType = eventType;	
	}
	
	public int dp2px(float dp) {
		return (int) (dp * displayMetrics.density + 0.5f);
	}

	public void startGame() {
		
		if (gameScene == null) {
			gameScene = new GameScene(this);
		}
		
		transitScene(gameScene);
	}

}