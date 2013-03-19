package com.jacknife.dubdroid;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.jacknife.Utils;

public class MainActivity extends Activity implements
		OnAudioFocusChangeListener, OnClickListener, OnLongClickListener,
		OnSeekBarChangeListener {
	
	//MAX number of loops in the app
	//private final int MAX = 8;	
	
	//WakeLock to keep the screen on
	protected PowerManager.WakeLock mWakeLock;
	
	// MediaPlayers...one for each track
	private MediaPlayer[] loopMan = new MediaPlayer[8];
	
	// Button objects
	private Button but01, but02, but03, but04, but05, but06, but07, but08;
	private Button butStop, butSync;
	
	//sLoop contains the loop number associated with current button press
	private byte sLoop = -1;
	
	//SeekBar
	SeekBar sbar; 
	
	// Reset value for seekTo()
	final private int mStart = 0;
	
	// Our very own Candi takes care of audio management!
	public AudioManager mCandi;
	
	// flag array to check if button is on or off.used for color change
	private boolean bFlag[] = { false, false, false, false, false, false,
			false, false };

	
	//onCreate function over ride.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Gain the wakelock
		final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();
		
		// Setup the audio manager
		mCandi = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		// attempt to gain audioFocus
		int result = mCandi.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
				AudioManager.AUDIOFOCUS_GAIN);

		if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
			// could not get audio focus.
		}

		// Init the media Players
		loopMan[0] = MediaPlayer.create(this, R.raw.loop1);
		loopMan[0].setLooping(true);
		loopMan[1] = MediaPlayer.create(this, R.raw.loop2);
		loopMan[1].setLooping(true);
		loopMan[2] = MediaPlayer.create(this, R.raw.loop3);
		loopMan[2].setLooping(true);
		loopMan[3] = MediaPlayer.create(this, R.raw.loop4);
		loopMan[3].setLooping(true);
		loopMan[4] = MediaPlayer.create(this, R.raw.loop5);
		loopMan[4].setLooping(true);
		loopMan[5] = MediaPlayer.create(this, R.raw.loop6);
		loopMan[5].setLooping(true);
		loopMan[6] = MediaPlayer.create(this, R.raw.loop7);
		loopMan[6].setLooping(true);
		loopMan[7] = MediaPlayer.create(this, R.raw.loop8);
		loopMan[7].setLooping(true);

		// Setup the activities listener as default for all buttons
		setupListeners(this);

	}

	// Method to setup the Listeners.what else?
	private void setupListeners(MainActivity mainActivity) {
		but01 = (Button) findViewById(R.id.loop1);
		but02 = (Button) findViewById(R.id.loop2);
		but03 = (Button) findViewById(R.id.loop3);
		but04 = (Button) findViewById(R.id.loop4);
		but05 = (Button)findViewById(R.id.loop5);
		but06 = (Button)findViewById(R.id.loop6);
		but07 = (Button)findViewById(R.id.loop7);
		but08 = (Button)findViewById(R.id.loop8);
		butStop = (Button) findViewById(R.id.stopButton);
		butSync = (Button) findViewById(R.id.syncButton);
		but01.setOnClickListener(this);
		but02.setOnClickListener(this);
		but03.setOnClickListener(this);
		but04.setOnClickListener(this);
		but05.setOnClickListener(this);
		but06.setOnClickListener(this);
		but07.setOnClickListener(this);
		but08.setOnClickListener(this);
		but01.setOnLongClickListener(this);
		but02.setOnLongClickListener(this);
		but03.setOnLongClickListener(this);
		but04.setOnLongClickListener(this);
		but05.setOnLongClickListener(this);
		but06.setOnLongClickListener(this);
		but07.setOnLongClickListener(this);
		but08.setOnLongClickListener(this);
		butStop.setOnClickListener(this);
		butSync.setOnClickListener(this);
		sbar = (SeekBar)findViewById(R.id.seekBar1);
		sbar.setOnSeekBarChangeListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Compliance with the Force!
	@Override
	public void onAudioFocusChange(int focusState) {
		// TODO Auto-generated method stub
		switch (focusState) {
		case AudioManager.AUDIOFOCUS_GAIN:
			// TODO
			break;
		case AudioManager.AUDIOFOCUS_LOSS:
			// TODO
			break;
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
			// TODO
			break;
		case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
			// TODO
			break;
		case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
			// TODO
			break;
		case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
			// TODO
			break;
		default:
			System.out.println("How the fuck did this happen!!!");
			break;
		}
	}

	// Override the onClick method to do our bidding :)
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loop1:
			toggleLoop(0);
			toggleHiLite(0);
			break;
		case R.id.loop2:
			toggleLoop(1);
			toggleHiLite(1);
			break;
		case R.id.loop3:
			toggleLoop(2);
			toggleHiLite(2);
			break;
		case R.id.loop4:
			toggleLoop(3);
			toggleHiLite(3);
			break;
		case R.id.loop5:
			toggleLoop(4);
			toggleHiLite(4);
			break;
		case R.id.loop6:
			toggleLoop(5);
			toggleHiLite(5);
			break;
		case R.id.loop7:
			toggleLoop(6);
			toggleHiLite(6);
			break;
		case R.id.loop8:
			toggleLoop(7);
			toggleHiLite(7);
			break;
		case R.id.stopButton:
			stopAll();
			break;
		case R.id.syncButton:
			// Utils.makeToast("No cookie here :(", v.getContext());
			synchLoops();
			break;
		}
	}

	private void synchLoops() {
		for (int i = 0; i < 8; i++) {
			if (bFlag[i]) {
				stopLoop(i);
			}
		}
		for (int i = 0; i < 8; i++) {
			if (bFlag[i]) {
				playLoop(i);
			}
		}

	}

	private void playLoop(int i) {
		if(i>=0 || i<=7)
			loopMan[i].start();
		else
			System.out.println("Invalid loop number at playLoop()"); 
	}

	private void toggleHiLite(int i) {
		switch (i) {
		case 0:
			if (bFlag[0])
				but01.setBackgroundColor(0xF1FF5555);
			else
				but01.setBackgroundColor(color.holo_blue_light);
			break;
		case 1:
			if (bFlag[1])
				but02.setBackgroundColor(0xF1FF5555);
			else
				but02.setBackgroundColor(color.holo_blue_light);
			break;
		case 2:
			if (bFlag[2])
				but03.setBackgroundColor(0xF1FF5555);
			else
				but03.setBackgroundColor(color.holo_blue_light);
			break;
		case 3:
			if (bFlag[3])
				but04.setBackgroundColor(0xF1FF5555);
			else
				but04.setBackgroundColor(color.holo_blue_light);
			break;
		case 4:
			if (bFlag[4])
				but05.setBackgroundColor(0xF1FF5555);
			else
				but05.setBackgroundColor(color.holo_blue_light);
			break;
		case 5:
			if (bFlag[5])
				but06.setBackgroundColor(0xF1FF5555);
			else
				but06.setBackgroundColor(color.holo_blue_light);
			break;
		case 6:
			if (bFlag[6])
				but07.setBackgroundColor(0xF1FF5555);
			else
				but07.setBackgroundColor(color.holo_blue_light);
			break;
		case 7:
			if (bFlag[7])
				but08.setBackgroundColor(0xF1FF5555);
			else
				but08.setBackgroundColor(color.holo_blue_light);
			break;
		default:
			System.out.println("Again with the damn errors!!! "+i);
			break;
		}

	}

	// Function to stop all the current playing loops dead in their tracks
	// XD
	private void stopAll() {
		try {
			for(int i=0;i<8;i++){
				if (loopMan[i].isPlaying()) {
					loopMan[i].stop();
					loopMan[i].prepare();
					loopMan[i].seekTo(mStart);
					}
				}
			} catch (Exception e) {
			System.out.println("IOException in stopALl()");
			e.printStackTrace();
		}
		for (int j = 0; j < 8; j++) {
			if (bFlag[j]) {
				bFlag[j] = !bFlag[j];
				toggleHiLite(j);
			}
		}
		Utils.makeToast("Stopping...", this);
	}

	// Helper function to toggle the loops on and off
	private void toggleLoop(int i) {
			if (!bFlag[i]) {
				bFlag[i] = !bFlag[i];
				loopMan[i].start();
			} else {
				bFlag[i] = !bFlag[i];
				stopLoop(i);
			}
	}

	// This function basically toggles of the loop
	// The loop number is specified by i
	// I guess :P
	private void stopLoop(int i) {
		try {
				loopMan[i].stop();
				loopMan[i].prepare();
				loopMan[i].seekTo(mStart);
		} catch (Exception e) {
			System.out.println("Fault in toggleLoop");
		}
	}

	@Override
	public void onBackPressed() {
		for(int i=0;i<8;i++){
			loopMan[i].release();
		}
		super.onBackPressed();
		Utils.makeToast("Have fun.Keep it sick!", this);
		this.finish();
	}

	@SuppressLint("Wakelock")
	public void onDestroy(){
		this.mWakeLock.release();
		super.onDestroy();
	}

	public void onPause(){
		this.stopAll();
		super.onPause();
	}
	
	@Override
	public boolean onLongClick(View v) {
		sLoop = (byte)getSelectedSound(v);
		sbar.setProgress(50);
		LinearLayout l1 = (LinearLayout) findViewById(R.id.linearLayout1);
		LinearLayout l2 = (LinearLayout) findViewById(R.id.linearLayout2);
		l1.setFocusable(false);
		l1.setClickable(false);
		l2.setFocusable(false);
		l2.setClickable(false);
		l1.setVisibility(View.GONE);
		l2.setVisibility(View.GONE);
		FrameLayout volume = (FrameLayout) findViewById(R.id.FrameLayout1);
		volume.setClickable(true);
		volume.setFocusable(true);
		volume.setVisibility(View.VISIBLE);
		return true;
	}

	//Returns integer value corresponding to loop number attached to 
	//the view being passed to it
	private int getSelectedSound(View v) {
		switch(v.getId()){
		case R.id.loop1	:	return 0;	
		case R.id.loop2	:	return 1;	
		case R.id.loop3	:	return 2;	
		case R.id.loop4	:	return 3;	
		case R.id.loop5	:	return 4;	
		case R.id.loop6	:	return 5;	
		case R.id.loop7	:	return 6;	
		case R.id.loop8	:	return 7;	
		default			: System.out.println("Buggy Sound selection");
							return -1;
		}
	}


	
	@Override
	public void onProgressChanged(SeekBar arg0, int level, boolean arg1) {
		if(arg1){
			Utils.makeToast(level, this);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// First change the sound volume
		int level = sbar.getProgress();
		loopMan[sLoop].setVolume(level/2,level/2);
		FrameLayout volume = (FrameLayout) findViewById(R.id.FrameLayout1);
		volume.setClickable(false);
		volume.setFocusable(false);
		volume.setVisibility(View.GONE);
		LinearLayout l1 = (LinearLayout) findViewById(R.id.linearLayout1);
		LinearLayout l2 = (LinearLayout) findViewById(R.id.linearLayout2);
		l1.setFocusable(true);
		l1.setClickable(true);
		l2.setFocusable(true);
		l2.setClickable(true);
		l1.setVisibility(View.VISIBLE);
		l2.setVisibility(View.VISIBLE);
		Utils.makeToast(seekBar.getProgress(), this);
	}
	
}
