package pbartz.games.deject;

import java.io.IOException;
import java.io.InputStream;

import pbartz.games.deject.utils.ObjectMap;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapLibrary {
	
	static ObjectMap<String, Bitmap> bitmaps = new ObjectMap<String, Bitmap>();	
	static boolean isAssetsLoaded = false;
	
	public static void loadAssets(DejectSurface surface) {
		
		if (isAssetsLoaded) {
			return;
		}
		
		AssetManager assets = surface.context.getAssets();
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		
		try {
			
			String[] files = assets.list("images");
			
			for(int i = 0 ; i < files.length ; i++) {
				
				String fileName = files[i];
				String fileTag = fileName.replaceAll(".png", "");
				
				InputStream is = assets.open("images/" + fileName);
				
				Bitmap bmp = BitmapFactory.decodeStream(is, null, options);
				
				bitmaps.put(fileTag, bmp);

			}
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		isAssetsLoaded = true;
		
	}
	
	public static Bitmap getBitmap(String tag) {
		return bitmaps.get(tag);
	}

}