package pbartz.games.deject.components;

import android.graphics.Bitmap;
import pbartz.games.deject.core.Component;

public class BitmapComponent extends Component {
	
	Bitmap bitmap = null;
	
	public BitmapComponent(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}	
	

}
