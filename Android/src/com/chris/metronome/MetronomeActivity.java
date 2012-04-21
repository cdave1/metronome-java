package com.chris.metronome;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;

public class MetronomeActivity extends Activity {
	private static SoundPool soundPool;
	private static HashMap<String, Integer> soundPoolMap;
	static int mod = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		soundPoolMap = new HashMap<String, Integer>();

		new Thread(new Runnable()
		{
			public void run()
			{
				StartMetronome();
			}
		}).start();
	}


	public void StartMetronome()
	{
		try
		{
			// InputStream in = new FileInputStream("sounds/pop.wav");
			// AudioStream as = new AudioStream(in);
			// AudioPlayer.player.start(as);

			SetBPM(140);

			while(true)
			{
				if (MetronomeWillPlay() == true)
				{
					playSound("pop.wav");
				}
			}
		}
		catch (Exception ex)
		{

		}
	}

	public void SetBPM(int bpm)
	{
		if (bpm == 0) 
		{
			mod = 1000;
		}
		else 
		{
			mod = 60000 / bpm;
		}
	}

	public boolean MetronomeWillPlay()
	{
		if ((System.currentTimeMillis() % mod) == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void playSound(final String filePath)
	{
		//new Thread(new Runnable() 
		//{ // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
		//	public void run() 
		//	{
				try
				{
					if (!soundPoolMap.containsKey(filePath))
					{
						AssetFileDescriptor afd = getAssets().openFd(filePath);
						if (afd == null)
						{
							System.out.println("Could not find sound " + filePath);
							return;
						}

						int id = soundPool.load(afd, 1);
						soundPoolMap.put(filePath, id);
					}
					int id = soundPoolMap.get(filePath);
					soundPool.play(id, 1.0f, 1.0f, 1, 0, 1.0f);
					System.out.println("Sound Id: " + id);
				}
				catch (Exception ex)
				{
					System.out.println(ex.getMessage() + ex.toString());
				}
			//}
		//}).start();
	}
}