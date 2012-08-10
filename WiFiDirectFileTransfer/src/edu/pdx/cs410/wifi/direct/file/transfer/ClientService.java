package edu.pdx.cs410.wifi.direct.file.transfer;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
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
	private WifiP2pInfo wifiInfo;
	
	public ClientService() {
		super("ClientService");
		serviceEnabled = true;
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		port = ((Integer) intent.getExtras().get("port")).intValue();	
		fileToSend = (File) intent.getExtras().get("fileToSend");
		clientResult = (ResultReceiver) intent.getExtras().get("clientResult");	
		//targetDevice = (WifiP2pDevice) intent.getExtras().get("targetDevice");	
		wifiInfo = (WifiP2pInfo) intent.getExtras().get("wifiInfo");	
		
		if(!wifiInfo.isGroupOwner)
		{	
			//targetDevice.
			signalActivity(wifiInfo.isGroupOwner + " Transfering file " + fileToSend.getName() + " to " + wifiInfo.groupOwnerAddress.toString()  + " on TCP Port: " + port );
			
			InetAddress targetIP = wifiInfo.groupOwnerAddress;
			
			Socket clientSocket = null;
			OutputStream os = null;
			 
			try {
				
				clientSocket = new Socket(targetIP, port);
				os = clientSocket.getOutputStream();
				
				
				
				
				
				
				
				
				

			} catch (IOException e) {
				signalActivity(e.getMessage());
			}		
		}
		else
		{
			signalActivity("This device is a group owner, therefore the IP address of the " +
					"target device cannot be determined. File transfer cannot continue");
		}
	
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