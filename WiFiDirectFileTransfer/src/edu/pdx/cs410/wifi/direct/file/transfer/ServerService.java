package edu.pdx.cs410.wifi.direct.file.transfer;

import java.io.File;
import android.os.Bundle;



import android.app.IntentService;
import android.content.Intent;
import android.os.ResultReceiver;
import android.view.View;

public class ServerService extends IntentService {

	private boolean serviceEnabled;
	
	private int port;
	private File saveLocation;
	private ResultReceiver serverResult;
	private View updateArea;
	
	public ServerService() {
		super("ServerService");
		serviceEnabled = true;
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		port = ((Integer) intent.getExtras().get("port")).intValue();	
		saveLocation = (File) intent.getExtras().get("saveLocation");
		serverResult = (ResultReceiver) intent.getExtras().get("serverResult");	
		//updateArea = (View) intent.getExtras().get("serverResult");	
		
		
		signalActivity("Starting to download");
		 
		
		
		while(true && serviceEnabled)
		{
			
			//When a client is found, start file transfer in new thread 
		}
		
		
		
		//Socket code here
		
		//signalActivity("past the loop");

		
		
		//Signal that operation is complete
		serverResult.send(port, null);
	}
	

	public void signalActivity(String message)
	{
		Bundle b = new Bundle();
		b.putString("message", message);
		serverResult.send(port, b);
	}
	
	
	public void onDestroy()
	{
		serviceEnabled = false;
		
		//Signal that the service was stopped 
		//serverResult.send(port, new Bundle());
		
		stopSelf();
	}

}
