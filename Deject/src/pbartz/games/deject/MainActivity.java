package pbartz.games.deject;

import pbartz.games.deject.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends BaseGameActivity implements OnTouchListener {

	private DejectSurface aSurfaceView;
	private String nextAction = "";

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

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		
		if (nextAction == "leaderboard") {
			nextAction = "";
			showLeaderBoard();
		}
		
	}

	public void showLeaderBoard() {
		if (this.isSignedIn()) {			
			
			runOnUiThread(new Runnable() {
	    	    public void run() 
	    	    {
	    	    	if (Storage.getIntValue("high_score", 0) > 0) {
	    				getGamesClient().submitScore(getString(R.string.leaderboard_id), Storage.getIntValue("high_score", 0));
	    			}
	    			startActivityForResult(getGamesClient().getLeaderboardIntent(getString(R.string.leaderboard_id)), 1); 
	    	    }
	    	});
			
			
		} else {
			nextAction = "leaderboard";
			beginUserInitiatedSignIn();
		}
		
	}

	public void uploadScore() {
		if (this.isSignedIn()) {
			
			runOnUiThread(new Runnable() {
	    	    public void run() 
	    	    {
	    	    	if (Storage.getIntValue("high_score", 0) > 0) {
	    				getGamesClient().submitScore(getString(R.string.leaderboard_id), Storage.getIntValue("high_score", 0));
	    			} 
	    	    }
	    	});		
			
		}
		
	}

}