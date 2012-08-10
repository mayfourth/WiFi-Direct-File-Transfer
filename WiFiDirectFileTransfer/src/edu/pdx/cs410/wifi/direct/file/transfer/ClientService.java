package edu.pdx.cs410.wifi.direct.file.transfer;


import java.io.File;

import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;



import android.app.IntentService;
import android.content.Intent;
import android.os.ResultReceiver;

public class ClientService extends IntentService {

	private boolean serviceEnabled;
	
	private int port;
	private File fileToSend;
	private ResultReceiver clientResult;
	private WifiP2pDevice targetDevice;
	
	public ClientService() {
		super("ClientService");
		serviceEnabled = true;
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		port = ((Integer) intent.getExtras().get("port")).intValue();	
		fileToSend = (File) intent.getExtras().get("fileToSend");
		clientResult = (ResultReceiver) intent.getExtras().get("clientResult");	
		targetDevice = (WifiP2pDevice) intent.getExtras().get("targetDevice");	
		
		//targetDevice.
		signalActivity("Starting to upload");
		 
		
		
		while(true && serviceEnabled)
		{
			
			//When a client is found, start file transfer in new thread 
		}
		
		
		
		//Socket code here
		
		//signalActivity("past the loop");

		
		
		//Signal that operation is complete
		clientResult.send(port, null);
	}
	

	public void signalActivity(String message)
	{
		Bundle b = new Bundle();
		b.putString("message", message);
		clientResult.send(port, b);
	}
	
	
	public void onDestroy()
	{
		serviceEnabled = false;
		
		//Signal that the service was stopped 
		//serverResult.send(port, new Bundle());
		
		stopSelf();
	}

}