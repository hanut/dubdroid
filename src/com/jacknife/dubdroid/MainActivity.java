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
	private final int MAX = 16;	
	
	// Reset value for seekTo()
	final private int mStart = 0;

	//sLoop contains the loop number associated with current button press
		private byte sLoop = -1;
		
	//WakeLock to keep the screen on
	protected PowerManager.WakeLock mWakeLock;
	
	// MediaPlayers...one for each track
	private MediaPlayer[] loopMan = new MediaPlayer[MAX];
	
	// Button objects
	private Button[] bHolder = new Button[MAX];
	private Button butStop, butSync;
	
	//SeekBar
	SeekBar sbar; 
	
	// Our very own Candi takes care of audio management!
	public AudioManager mCandi;
	
	// flag array to check if button is on or off.used for color change
	private boolean bFlag[] = new boolean[MAX];

	
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

		// Calling the function to initialise media players
		initMPlayers();
		
		// Setup the activities listener as default for all buttons
		setupListeners(this);
	}

	//Method to setup the media players for each track
	private void initMPlayers() {
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
		loopMan[8] = MediaPlayer.create(this, R.raw.loop9);
		loopMan[8].setLooping(true);
		loopMan[9] = MediaPlayer.create(this, R.raw.loop10);
		loopMan[9].setLooping(true);
		loopMan[10] = MediaPlayer.create(this, R.raw.loop11);
		loopMan[10].setLooping(true);
		loopMan[11] = MediaPlayer.create(this, R.raw.loop12);
		loopMan[11].setLooping(true);
		loopMan[12] = MediaPlayer.create(this, R.raw.loop13);
		loopMan[12].setLooping(true);
		loopMan[13] = MediaPlayer.create(this, R.raw.loop14);
		loopMan[13].setLooping(true);
		loopMan[14] = MediaPlayer.create(this, R.raw.loop15);
		loopMan[14].setLooping(true);
		loopMan[15] = MediaPlayer.create(this, R.raw.loop16);
		loopMan[15].setLooping(true);
		//set all the bFlag values to false indicating no loop is on
		for(int i=0;i<MAX;i++){
			bFlag[i] = false;
		}
		
	}

	// Method to setup the Listeners.what else?
	private void setupListeners(MainActivity mainActivity) {
		bHolder[0] = (Button) findViewById(R.id.loop1);
		bHolder[1] = (Button) findViewById(R.id.loop2);
		bHolder[2] = (Button) findViewById(R.id.loop3);
		bHolder[3] = (Button) findViewById(R.id.loop4);
		bHolder[4] = (Button)findViewById(R.id.loop5);
		bHolder[5] = (Button)findViewById(R.id.loop6);
		bHolder[6] = (Button)findViewById(R.id.loop7);
		bHolder[7] = (Button)findViewById(R.id.loop8);
		bHolder[8] = (Button)findViewById(R.id.loop9);
		bHolder[9] = (Button)findViewById(R.id.loop10);
		bHolder[10] = (Button)findViewById(R.id.loop11);
		bHolder[11] = (Button)findViewById(R.id.loop12);
		bHolder[12] = (Button)findViewById(R.id.loop13);
		bHolder[13] = (Button)findViewById(R.id.loop14);
		bHolder[14] = (Button)findViewById(R.id.loop15);
		bHolder[15] = (Button)findViewById(R.id.loop16);
		butStop = (Button) findViewById(R.id.stopButton);
		butSync = (Button) findViewById(R.id.syncButton);
		for(int i=0;i<MAX;i++){
			bHolder[i].setOnClickListener(this);
			bHolder[i].setOnLongClickListener(this);
		}
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
		case R.id.loop9:
			toggleLoop(8);
			toggleHiLite(8);
			break;
		case R.id.loop10:
			toggleLoop(9);
			toggleHiLite(9);
			break;
		case R.id.loop11:
			toggleLoop(10);
			toggleHiLite(10);
			break;
		case R.id.loop12:
			toggleLoop(11);
			toggleHiLite(11);
			break;
		case R.id.loop13:
			toggleLoop(12);
			toggleHiLite(12);
			break;
		case R.id.loop14:
			toggleLoop(13);
			toggleHiLite(13);
			break;
		case R.id.loop15:
			toggleLoop(14);
			toggleHiLite(14);
			break;
		case R.id.loop16:
			toggleLoop(15);
			toggleHiLite(15);
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
		for (int i = 0; i < MAX; i++) {
			if (bFlag[i]) {
				stopLoop(i);
			}
		}
		for (int i = 0; i < MAX; i++) {
			if (bFlag[i]) {
				playLoop(i);
			}
		}

	}

	private void playLoop(int i) {
		if(i>=0 || i<MAX)
			loopMan[i].start();
		else
			System.out.println("Invalid loop number at playLoop()"); 
	}

	private void toggleHiLite(int i) {
			if (bFlag[i])
				bHolder[i].setBackgroundColor(0xF1FF5555);
			else
				bHolder[i].setBackgroundColor(color.holo_blue_light);

	}

	// Function to stop all the current playing loops dead in their tracks
	// XD
	private void stopAll() {
		try {
			for(int i=0;i<MAX;i++){
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
		for (int j = 0; j < MAX; j++) {
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
		for(int i=0;i<MAX;i++){
			loopMan[i].release();
		}
		super.onBackPressed();
		Utils.makeToast("Have fun.Keep it sick!", this);
		this.finish();
	}

	@SuppressLint("Wakelock")
	public void onDestroy(){
		this.mWakeLock.release();
		for(int i=0;i<MAX;i++){
			loopMan[i].release();
		}
		super.onDestroy();
	}

	public void onPause(){
		this.stopAll();
		for(int i=0;i<MAX;i++){
			loopMan[i].release();
		}
		super.onPause();
	}
	
	@Override
	public boolean onLongClick(View v) {
		sLoop = (byte)getSelectedSound(v);
		sbar.setProgress(50);
		LinearLayout l1 = (LinearLayout) findViewById(R.id.linearLayout1);
		LinearLayout l2 = (LinearLayout) findViewById(R.id.linearLayout2);
		LinearLayout l3 = (LinearLayout) findViewById(R.id.LinearLayout03);
		l1.setFocusable(false);
		l1.setClickable(false);
		l2.setFocusable(false);
		l2.setClickable(false);
		l3.setFocusable(false);
		l3.setClickable(false);
		l1.setVisibility(View.GONE);
		l2.setVisibility(View.GONE);
		l3.setVisibility(View.GONE);
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
		case R.id.loop8	:	return 8;
		case R.id.loop9	:	return 9;
		case R.id.loop10	:	return 10;
		case R.id.loop11	:	return 11;
		case R.id.loop12	:	return 12;
		case R.id.loop13	:	return 13;
		case R.id.loop14	:	return 14;
		case R.id.loop15	:	return 15;
		case R.id.loop16	:	return 16;
		default			: System.out.println("Buggy Sound selection");
							return -1;
		}
	}


	
	@Override
	public void onProgressChanged(SeekBar arg0, int level, boolean arg1) {
		if(arg1){
			Utils.makeToast(level, this);
			loopMan[sLoop].setVolume(level/2,level/2);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// First change the sound volume
		//int level = sbar.getProgress();
		//loopMan[sLoop].setVolume(level/2,level/2);
		FrameLayout volume = (FrameLayout) findViewById(R.id.FrameLayout1);
		volume.setClickable(false);
		volume.setFocusable(false);
		volume.setVisibility(View.GONE);
		LinearLayout l1 = (LinearLayout) findViewById(R.id.linearLayout1);
		LinearLayout l2 = (LinearLayout) findViewById(R.id.linearLayout2);
		LinearLayout l3 = (LinearLayout) findViewById(R.id.LinearLayout03);
		l1.setFocusable(true);
		l1.setClickable(true);
		l2.setFocusable(true);
		l2.setClickable(true);
		l3.setFocusable(true);
		l3.setClickable(true);
		l1.setVisibility(View.VISIBLE);
		l2.setVisibility(View.VISIBLE);
		l3.setVisibility(View.VISIBLE);
		//Utils.makeToast(seekBar.getProgress(), this);
	}
	
}
