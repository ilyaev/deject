package pbartz.games.deject;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

@SuppressLint("UseSparseArrays")
public class Sound {
	
	public static final int CLICK1 = R.raw.click1;
	public static final int PICKUP_COIN = R.raw.pickup_coin;
	public static final int TIMEFREEZE = R.raw.timefreeze;
	public static final int HIT_MISS = R.raw.hit_miss;
	public static final int HIT = R.raw.hit2;
	public static final int EXPLOSION = R.raw.explosion;
	public static final int HEAL = R.raw.heal;
	
	private static SoundPool soundPool;
	private static HashMap<Integer, Integer> soundPoolMap;
	
	public static void initSounds(Context context) {
		
		soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 100);
		soundPoolMap = new HashMap<Integer, Integer>(7); 
		
		soundPoolMap.put(CLICK1, soundPool.load(context, R.raw.click1, 1));
		soundPoolMap.put(PICKUP_COIN, soundPool.load(context, R.raw.pickup_coin, 1));
		soundPoolMap.put(TIMEFREEZE, soundPool.load(context, R.raw.timefreeze, 1));
		soundPoolMap.put(HIT, soundPool.load(context, R.raw.hit2, 1));
		soundPoolMap.put(HIT_MISS, soundPool.load(context, R.raw.hit_miss, 1));
		soundPoolMap.put(EXPLOSION, soundPool.load(context, R.raw.explosion, 1));
		soundPoolMap.put(HEAL, soundPool.load(context, R.raw.heal, 1));
		
	}
	
	public static void playSound(Context context, int soundId) {
		
		if(soundPool == null || soundPoolMap == null) return;
		
		float volume = 1;
		
		if (soundId != CLICK1 && soundId != HIT) {
			volume = 0.5f;
		}
		
		soundPool.play(soundPoolMap.get(soundId), volume, volume, 1, 0, 1f);
		
	}

}
