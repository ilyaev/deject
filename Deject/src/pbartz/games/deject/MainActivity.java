package pbartz.games.deject;

import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity implements OnTouchListener {

	private DejectSurface aSurfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		aSurfaceView = new DejectSurface(this);
		aSurfaceView.setOnTouchListener(this);
		
		setContentView(aSurfaceView);
	}
	
	@Override
    protected void onStart() {
        super.onStart();
    }
	
	@Override
	protected void onPause() {
		super.onPause();
		aSurfaceView.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		aSurfaceView.resume();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		aSurfaceView.processTouch(event.getAction(), event);
		return true;
	}

}