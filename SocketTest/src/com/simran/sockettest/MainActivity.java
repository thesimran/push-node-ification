package com.simran.sockettest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.UnknownHostException;

import ca.mobicartel.axcess.network.EzTask;
import de.neofonie.mobile.app.android.widget.crouton.Crouton;
import de.neofonie.mobile.app.android.widget.crouton.Style;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	public static final String TAG = "SocketTest";
	public static final String IP_ADDRESS = "192.168.2.30";
	
	private Button btnConnect;
	private EditText editTextIp;
	private TextView txtViewResponse;
	
	private String ipAddress;
	private String response;
	
	private Socket socket;
	private SocketHandler socketHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnConnect = (Button)findViewById(R.id.btn_connect);
		editTextIp = (EditText)findViewById(R.id.edittext_ip);
		txtViewResponse = (TextView)findViewById(R.id.textview_response);
		
		editTextIp.setText(IP_ADDRESS);
		socketHandler = new SocketHandler(this);
		
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		
		if(socket.isConnected()) {
			try {
				socket.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	class SocketHandler extends Handler {
		@Override
        public void handleMessage(Message msg) {
			String m = msg.getData().getString("message");
			Crouton.makeText(MainActivity.this, m, Style.INFO).show();
            
        }
	};
	
	public void connectBtnListener(View v) {
	
		ipAddress = editTextIp.getText().toString();
		ipAddress = ipAddress.trim();
		if(!ipAddress.equals("")) {
			
			
			new Thread() {
				public void run() {
					
					Log.d(TAG, "Connecting to IP " + ipAddress + "...");
					
					try {
						socket = new Socket(ipAddress, 8000);
						
						if(socket != null) {
							//Crouton.makeText(MainActivity.this, "Connected to TCP server!", Style.INFO).show();
							//TODO post from here to main thread...
							socketHandler.sendMessage(new Message
							BufferedReader in = null;
							in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
							//StringBuffer sb = new StringBuffer("");
					        String line = "";
					        String NL = System.getProperty("line.separator");
					        while ((line = in.readLine()) != null) {
					        	/*
					        	 *  This loop will go on forever because the inputStream
					        	 *  will remain open until the server closes the socket.
					        	 */
					        	//Log.d("Content", "line: " + line);
					        	
					            Crouton.makeText(MainActivity.this, line, Style.INFO).show();
					        }
					        in.close();
					        //response = sb.toString();
					        
						}
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			}.start();
			
			/*
			EzTask ez_task = new EzTask();
			Method network, display;
			try {
				network = this.getClass().getMethod("connectToSocket");
				display = this.getClass().getMethod("blankMethod"); 
				ez_task.run(network, display, this);
			} catch (SecurityException e) {
				Log.e(TAG, e.getMessage());
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				Log.e(TAG, e.getMessage());
				e.printStackTrace();
			}
			*/
		}
		
	}
	
	public void connectToSocket() {
		
	}
	
	public void blankMethod() {		
	}
	

}
