package com.simran.sockettest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.UnknownHostException;

import ca.mobicartel.axcess.network.EzTask;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	public static final String TAG = "SocketTest";
	public static final String IP_ADDRESS = "10.0.1.52";
	
	private Socket socket;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		EzTask ez_task = new EzTask();
		Method network, display;
		try {
			network = this.getClass().getMethod("connectToSocket");
			display = this.getClass().getMethod("display"); 
			ez_task.run(network, display, this);
		} catch (SecurityException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void connectToSocket() {
		Log.d(TAG, "Connecting to IP " + IP_ADDRESS + "...");
		
		try {
			socket = new Socket(IP_ADDRESS, 8000);
			
			if(socket != null) {
				
				BufferedReader in = null;
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				StringBuffer sb = new StringBuffer("");
		        String line = "";
		        String NL = System.getProperty("line.separator");
		        while ((line = in.readLine()) != null) {
		        	
		        	Log.d("Content", "line: " + line);
		            sb.append(line + NL);
		        }
		        in.close();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void display() {
		
	}

}
