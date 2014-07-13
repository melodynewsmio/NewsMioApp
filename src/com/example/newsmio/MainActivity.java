package com.example.newsmio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	//4 secs: String uri = "/storage/sdcard0/DCIM/Camera/20140712_155102.mp4";
	//14 secs: String uri = "/storage/sdcard0/DCIM/Camera/20140712_154920.mp4";
	//32 secs: String uri = "/storage/sdcard0/DCIM/Camera/20140712_190038.mp4";
	String uri = "/storage/sdcard0/DCIM/Camera/20140712_190038.mp4";
	ArrayList<ImageView> views = new ArrayList<ImageView>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView textView = (TextView)findViewById(R.id.textView1);
		MediaMetadataRetriever video = new MediaMetadataRetriever();
		video.setDataSource(uri);

		ImageView image1 = (ImageView)findViewById(R.id.imageView1);
		views.add(image1);
		ImageView image2 = (ImageView)findViewById(R.id.imageView2);
		views.add(image2);
		ImageView image3 = (ImageView)findViewById(R.id.imageView3);
		views.add(image3);
		ImageView image4 = (ImageView)findViewById(R.id.imageView4);
		views.add(image4);
		ImageView image5 = (ImageView)findViewById(R.id.imageView5);
		views.add(image5);
		ImageView image6 = (ImageView)findViewById(R.id.imageView6);
		views.add(image6);
		
		int framePosition = 0;
		long videoLength = getVideoLength(uri);
		textView.setText(uri + " (" + String.valueOf(videoLength / 1000) + " s)");

		int i = 0;
		while (framePosition < (videoLength * 1000))
		{
			if (i == views.size())
				break;
			ImageView view = views.get(i++);
			Bitmap bmFrame = video.getFrameAtTime(framePosition); //unit in microsecond
			view.setImageBitmap(bmFrame);
			framePosition += 5000000;
		}

	}

	private long getVideoLength(String uriPath)
	{
		MediaPlayer mp = new MediaPlayer();
		FileInputStream stream;
		long duration = 0;
		try {
			stream = new FileInputStream(uriPath);
			mp.setDataSource(stream.getFD());
			stream.close();
			mp.prepare();
			duration = mp.getDuration();
			mp.release();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return duration;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
