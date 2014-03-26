package pbartz.games.deject.components;

import android.graphics.Bitmap;
import pbartz.games.deject.core.Component;
import pbartz.games.deject.utils.Pool.Poolable;

public class BitmapComponent extends Component implements Poolable {
	
	Bitmap bitmap = null;
	
	public void init(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub		
	}	
	

}
