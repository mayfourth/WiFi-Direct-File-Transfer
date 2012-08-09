package edu.pdx.cs410.wifi.direct.file.transfer;

import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	WifiP2pManager wifiManager;
	Channel wifichannel;
	BroadcastReceiver wifiServerReceiver;

	IntentFilter wifiServerReceiverIntentFilter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Block auto opening keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        
        wifiManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        wifichannel = wifiManager.initialize(this, getMainLooper(), null);
        wifiServerReceiver = new WiFiServerBroadcastReceiver(wifiManager, wifichannel, this);
        
        
        wifiServerReceiverIntentFilter = new IntentFilter();;
        wifiServerReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        wifiServerReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        wifiServerReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        wifiServerReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
       
    public void startFileBrowseActivity(View view) {
    	
        Intent clientStartIntent = new Intent(this, FileBrowser.class);
        startActivity(clientStartIntent);  

    }
    
    public void startServer(View view) {
   
    	registerReceiver(wifiServerReceiver,wifiServerReceiverIntentFilter);
    	

    	//Set status to running
    	TextView serverServiceStatus = (TextView) findViewById(R.id.server_status_text);
    	serverServiceStatus.setText(R.string.server_running);

    }
    
    public void stopServer(View view) {
    		
    	try
    	{
    	unregisterReceiver(wifiServerReceiver);
    	}
    	catch(IllegalArgumentException e)
    	{
    		//This will happen if the server was never running and the stop button was pressed.
    		//Do nothing in this case.
    	}

    	//set status to stopped
    	TextView serverServiceStatus = (TextView) findViewById(R.id.server_status_text);
    	serverServiceStatus.setText(R.string.server_stopped);

    }
    
    public void startClientActivity(View view) {
    	
        Intent clientStartIntent = new Intent(this, ClientActivity.class);
        startActivity(clientStartIntent);    		
    }   
    
    
    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver(wifiServerReceiver, wifiServerReceiverIntentFilter);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        stopServer(null);
        //unregisterReceiver(wifiServerReceiver);
    }
    
    
    
    public void setStatus(String message)
    {
    	TextView connectionStatusText = (TextView) findViewById(R.id.connection_status_text);
    	connectionStatusText.setText(message);	
    }
      
    
}
