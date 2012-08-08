package edu.pdx.cs410.wifi.direct.file.transfer;

import android.app.IntentService;
import android.content.Intent;

public class ServerService extends IntentService {

	private boolean serviceEnabled;
	
	public ServerService() {
		super("ServerService");
		serviceEnabled = true;
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		//Code to listen for wifi direct connections
		while(true && serviceEnabled)
		{
			
			//When a client is found, start file transfer in new thread 
		}
		
	}
	
	
	public void onDestroy()
	{
		serviceEnabled = false;
		stopSelf();
	}

}
