/*
 WiFi Direct File Transfer is an open source application that will enable sharing 
 of data between Android devices running Android 4.0 or higher using a WiFi direct
 connection without the use of a separate WiFi access point.This will enable data 
 transfer between devices without relying on any existing network infrastructure. 
 This application is intended to provide a much higher speed alternative to Bluetooth
 file transfers. 

 Copyright (C) 2012  Teja R. Pitla
 Contact: teja.pitla@gmail.com

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package edu.pdx.cs410.wifi.direct.file.transfer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
	
	public ServerService() {
		super("ServerService");
		serviceEnabled = true;
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		

		
		port = ((Integer) intent.getExtras().get("port")).intValue();	
		saveLocation = (File) intent.getExtras().get("saveLocation");
		serverResult = (ResultReceiver) intent.getExtras().get("serverResult");	
		
		
		//signalActivity("Starting to download");
		 
		
		String fileName = "";
		
        ServerSocket welcomeSocket = null;
        Socket socket = null;
                      
		try {
			

			
				welcomeSocket = new ServerSocket(port);
				
				while(true && serviceEnabled)
				{
				
				//Listen for incoming connections on specified port
				//Block thread until someone connects 
				socket = welcomeSocket.accept();
				
				//signalActivity("TCP Connection Established: " + socket.toString() + " Starting file transfer");
				
				
				
				
				
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);			
				
				OutputStream os = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(os);
				
				
				String inputData = "";
				
				
				
				signalActivity("About to start handshake");
				//Client-Server handshake
				
				/*
				String test = "Y";
				test = test + br.readLine() + test;
		
				
				signalActivity(test);
				 */
				
				/*
				inputData = br.readLine();
				
				if(!inputData.equals("wdft_client_hello"))
				{
					throw new IOException("Invalid WDFT protocol message");
					
				}
				
				pw.println("wdft_server_hello");
				
				
				inputData = br.readLine();
				
				
				if(inputData == null)
				{
					throw new IOException("File name was null");
					
				}
				
				
				fileName = inputData;
				
				pw.println("wdft_server_ready");
	
				*/
				
				//signalActivity("Handshake complete, getting file: " + fileName);
	
				String savedAs = "WDFL_File_" + System.currentTimeMillis();
			    File file = new File(saveLocation, savedAs);
			    
			    byte[] buffer = new byte[4096];
			    int bytesRead;
			    
			    FileOutputStream fos = new FileOutputStream(file);
			    BufferedOutputStream bos = new BufferedOutputStream(fos);
			    
			    while(true)
			    {
				    bytesRead = is.read(buffer, 0, buffer.length);
				    if(bytesRead == -1)
				    {
				    	break;
				    }			    
				    bos.write(buffer, 0, bytesRead);
				    bos.flush();
	
			    }
			    		    
	
			    /*
			    fos.close();
			    bos.close();
			    
			    br.close();
			    isr.close();
			    is.close();
			    
			    pw.close();
			    os.close();
			    		    
			    socket.close();
			    */
			    
			    bos.close();
			    socket.close();
	
			    
			    signalActivity("File Transfer Complete, saved as: " + savedAs);
			    //Start writing to file

			}
			
	    
		} catch (IOException e) {
			signalActivity(e.getMessage());
			
			
		}
		catch(Exception e)
		{
			signalActivity(e.getMessage());

		}
			
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
