package pbartz.games.deject.components;

import android.graphics.Color;
import android.graphics.Paint;
import pbartz.games.deject.core.Component;
import pbartz.games.deject.core.Interpolation;

public class ColorInterpolationComponent extends Component {
	
	Paint startPaint;
	Paint endPaint;
	
	Paint currentPaint;
	
	float speed;
	int type;
	float time = 0;
	
	public ColorInterpolationComponent(Paint oldPaint, Paint endPaint, float speed, int type) {
	
		startPaint = oldPaint;
		
		this.endPaint = endPaint;
		
		this.speed = speed * 1000;
		this.type = type;		
		
		
		this.currentPaint = new Paint();
	}
	
	
	public void increaseTime(float diff) {
		this.time += diff;
	}

	public boolean isCompleted() {
		if (this.time >= this.speed) return true;
		return false;
	}
	
	public Paint getCurrentPaint() {
		
		float t = time;
		float d = speed;
		
		int startColor = startPaint.getColor();
		int endColor = endPaint.getColor();
		
		float b = Color.alpha(startColor);
		float c = (Color.alpha(endColor) - b);
		
		int cA = (int) Interpolation.calculateCurrentValue(type, t, b, c, d);

		b = Color.red(startColor);
		c = (Color.red(endColor) - b);
		
		int cR = (int) Interpolation.calculateCurrentValue(type, t, b, c, d);
		
		b = Color.green(startColor);
		c = (Color.green(endColor) - b);
		
		int cG = (int) Interpolation.calculateCurrentValue(type, t, b, c, d);
		
		b = Color.blue(startColor);
		c = (Color.blue(endColor) - b);
		
		int cB = (int) Interpolation.calculateCurrentValue(type, t, b, c, d);
		
		currentPaint.setARGB(Math.max(0, Math.min(cA, 255)), Math.max(0, Math.min(cR, 255)), Math.max(0, Math.min(cG, 255)), Math.max(0, Math.min(cB, 255)));
		
		return currentPaint;
	}
	
}
