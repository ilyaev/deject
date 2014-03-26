package pbartz.games.deject.components;

import pbartz.games.deject.core.Component;
import pbartz.games.deject.utils.Pool.Poolable;
import android.graphics.Paint;

public class ColorComponent extends Component implements Poolable{
	
	Paint paint;
	Paint borderPaint = null;
	
	int r;
	int g;
	int b;
	int a;
	
	public void init(int a, int r, int g, int b) {
		this.a = a;
		this.r = r;
		this.g = g;
		this.b = b;
		this.paint = new Paint();
		this.updatePaint();
	}

	private void updatePaint() {
		this.paint.setARGB(a, r, g, b);
	}
	
	public void setARGB(int a, int r, int g, int b) {
		this.a = a;
		this.r = r;
		this.g = g;
		this.b = b;
		this.updatePaint();
	}
	
	public void setAlpha(int a) {
		this.a = a;
		this.updatePaint();
	}

	public Paint getPaint() {
		return paint;
	}

	public Paint getBorderPaint() {
		return borderPaint;
	}

	public void setBorderPaint(Paint borderPaint) {
		this.borderPaint = borderPaint;
	}

	public void setPaint(Paint currentPaint) {
		paint = currentPaint;		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		borderPaint = null;
	}	
	
}
