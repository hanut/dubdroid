package com.jacknife.dubdroid;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.jacknife.Utils;

public class MainActivity extends Activity implements
		OnAudioFocusChangeListener, OnClickListener {

	// MediaPlayers...one for each track
	private MediaPlayer sound01;
	private MediaPlayer sound02;
	private MediaPlayer sound03;
	private MediaPlayer sound04;
	// Button objects
	private Button but01, but02, but03, but04, but05, but06, but07, but08;
	private Button butStop, butSync;
	// Reset value for seekTo()
	final private int mStart = 0;
	// Our very own Candi takes care of audio management!
	public AudioManager mCandi;
	// flag array to check if button is on or off.used for color change
	private boolean bFlag[] = { false, false, false, false, false, false,
			false, false };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Setup the audio manager
		mCandi = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		// attempt to gain audioFocus
		int result = mCandi.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
				AudioManager.AUDIOFOCUS_GAIN);

		if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
			// could not get audio focus.
		}

		// Init the media Players
		sound01 = MediaPlayer.create(this, R.raw.loop1);
		sound01.setLooping(true);
		sound02 = MediaPlayer.create(this, R.raw.loop2);
		sound02.setLooping(true);
		sound03 = MediaPlayer.create(this, R.raw.loop3);
		sound03.setLooping(true);
		sound04 = MediaPlayer.create(this, R.raw.loop4);
		sound04.setLooping(true);

		// Setup the activities listener as default for all buttons
		setupListeners(this);

	}

	//Method to setup the Listeners.what else?
	private void setupListeners(MainActivity mainActivity) {
		but01 = (Button) findViewById(R.id.loop1);
		but02 = (Button) findViewById(R.id.loop2);
		but03 = (Button) findViewById(R.id.loop3);
		but04 = (Button) findViewById(R.id.loop4);
		//but05 = (Button)findViewById(R.id.loop5);
		//but06 = (Button)findViewById(R.id.loop6);
		//but07 = (Button)findViewById(R.id.loop7);
		//but08 = (Button)findViewById(R.id.loop8);
		butStop = (Button) findViewById(R.id.stopButton);
		butSync = (Button) findViewById(R.id.syncButton);
		but01.setOnClickListener(this);
		but02.setOnClickListener(this);
		but03.setOnClickListener(this);
		but04.setOnClickListener(this);
		// but05.setOnClickListener(this);
		// but06.setOnClickListener(this);
		// but07.setOnClickListener(this);
		// but08.setOnClickListener(this);
		butStop.setOnClickListener(this);
		butSync.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

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
			toggleLoop(1);
			toggleHiLite(1);
			break;
		case R.id.loop2:
			toggleLoop(2);
			toggleHiLite(2);
			break;
		case R.id.loop3:
			toggleLoop(3);
			toggleHiLite(3);
			break;
		case R.id.loop4:
			toggleLoop(4);
			toggleHiLite(4);
			break;
		case R.id.loop5:
			toggleLoop(5);
			toggleHiLite(5);
			break;
		case R.id.loop6:
			toggleLoop(6);
			toggleHiLite(6);
			break;
		case R.id.loop7:
			toggleLoop(7);
			toggleHiLite(7);
			break;
		case R.id.loop8:
			toggleLoop(8);
			toggleHiLite(8);
			break;
		case R.id.stopButton:
			stopAll();
			break;
		case R.id.syncButton:
			Utils.makeToast("No cookie here :(", v.getContext());
			break;
		}
	}

	private void toggleHiLite(int i) {
		switch (i) {
		case 1:
			if (bFlag[0])
				but01.setBackgroundColor(0xF1FF5555);
			else
				but01.setBackgroundColor(color.holo_blue_light);
			break;
		case 2:
			if (bFlag[1])
				but02.setBackgroundColor(0xF1FF5555);
			else
				but02.setBackgroundColor(color.holo_blue_light);
			break;
		case 3:
			if (bFlag[2])
				but03.setBackgroundColor(0xF1FF5555);
			else
				but03.setBackgroundColor(color.holo_blue_light);
			break;
		case 4:
			if (bFlag[3])
				but04.setBackgroundColor(0xF1FF5555);
			else
				but04.setBackgroundColor(color.holo_blue_light);
			break;
		case 5:
			if (bFlag[4])
				but05.setBackgroundColor(0xF1FF5555);
			else
				but05.setBackgroundColor(color.holo_blue_light);
			break;
		case 6:
			if (bFlag[5])
				but06.setBackgroundColor(0xF1FF5555);
			else
				but06.setBackgroundColor(color.holo_blue_light);
			break;
		case 7:
			if (bFlag[6])
				but07.setBackgroundColor(0xF1FF5555);
			else
				but07.setBackgroundColor(color.holo_blue_light);
			break;
		case 8:
			if (bFlag[7])
				but08.setBackgroundColor(0xF1FF5555);
			else
				but08.setBackgroundColor(color.holo_blue_light);
			break;
		default:
			System.out.println("Again with the damn errors!!!");
			break;
		}

	}

	// Function to stop all the current playing loops dead in their tracks
	// XD
	private void stopAll() {
		try {
			// stop all the active sounds
			if (sound01.isPlaying()) {
				sound01.stop();
				sound01.prepare();
				sound01.seekTo(mStart);
			}
			if (sound02.isPlaying()) {
				sound02.stop();
				sound02.prepare();
				sound02.seekTo(mStart);
			}
			if (sound03.isPlaying()) {
				sound03.stop();
				sound03.prepare();
				sound03.seekTo(mStart);
			}
			if (sound04.isPlaying()) {
				sound04.stop();
				sound04.prepare();
				sound04.seekTo(mStart);
			}
		} catch (Exception e) {
			System.out.println("IOException");
		}
		for(int j=0;j<4;j++){
			if(bFlag[j]){
				bFlag[j] = !bFlag[j];
				toggleHiLite(j+1);
			}
		}

		Utils.makeToast("Stopping...", this);
	}

	// Helper function to toggle the loops on and off
	private void toggleLoop(int i) {
		switch (i) {
		case 1:
			if (!bFlag[0]) {
				bFlag[0] = !bFlag[0];
				sound01.start();
			} else {
				bFlag[0] = !bFlag[0];
				stopLoop(1);
			}
			break;
		case 2:
			if (!bFlag[1]) {
				bFlag[1] = !bFlag[1];
				sound02.start();
			} else {
				bFlag[1] = !bFlag[1];
				stopLoop(2);
			}
			break;
		case 3:
			if (!bFlag[2]) {
				bFlag[2] = !bFlag[2];
				sound03.start();
			} else {
				bFlag[2] = !bFlag[2];
				stopLoop(3);
			}
			break;
		case 4:
			if (!bFlag[3]) {
				bFlag[3] = !bFlag[3];
				sound04.start();
			} else {
				bFlag[3] = !bFlag[3];
				stopLoop(4);
			}
			break;
		case 5:
			if (!bFlag[4]) {
				bFlag[4] = !bFlag[4];
				// sound05.start();
			} else {
				bFlag[4] = !bFlag[4];
				// stopLoop(5);
			}
			break;
		case 6:
			if (!bFlag[5]) {
				bFlag[5] = !bFlag[5];
				// sound06.start();
			} else {
				bFlag[5] = !bFlag[5];
				// stopLoop(6);
			}
			break;
		case 7:
			if (!bFlag[6]) {
				bFlag[6] = !bFlag[6];
				// sound07.start();
			} else {
				bFlag[6] = !bFlag[6];
				// stopLoop(7);
			}
			break;
		case 8:
			if (!bFlag[7]) {
				bFlag[7] = !bFlag[7];
				// sound08.start();
			} else {
				bFlag[7] = !bFlag[7];
				// stopLoop(8);
			}
			break;
		default:
			System.out.println("Fucking pixies!!!");
			break;
		}
	}

	// This function basically toggles of the loop
	// The loop number is specified by i
	// I guess :P
	private void stopLoop(int i) {
		try {
			switch (i) {
			case 1:
				sound01.stop();
				sound01.prepare();
				sound01.seekTo(mStart);
				break;
			case 2:
				sound02.stop();
				sound02.prepare();
				sound02.seekTo(mStart);
				break;
			case 3:
				sound03.stop();
				sound03.prepare();
				sound03.seekTo(mStart);
				break;
			case 4:
				sound04.stop();
				sound04.prepare();
				sound04.seekTo(mStart);
				break;
			case 5:
				// sound05.stop();
				// sound05.prepare();
				// sound05.seekTo(mStart);
				break;
			case 6:
				// sound06.stop();
				// sound06.prepare();
				// sound06.seekTo(mStart);
				break;
			case 7:
				// sound07.stop();
				// sound07.prepare();
				// sound07.seekTo(mStart);
				break;
			case 8:
				// sound08.stop();
				// sound08.prepare();
				// sound08.seekTo(mStart);
				break;
			default:
				System.out.println("Thats it! Im getting my gun...");
				break;
			}
		} catch (Exception e) {
			System.out.println("Fault in toggleLoop");
		}
	}

}
