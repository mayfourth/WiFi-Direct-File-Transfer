package edu.pdx.cs410.wifi.direct.file.transfer;

import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;


public class ClientActivity extends Activity {
	
	
	WifiP2pManager wifiManager;
	Channel wifichannel;
	BroadcastReceiver wifiClientReceiver;

	IntentFilter wifiClientReceiverIntentFilter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
             
        wifiManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        
        wifichannel = wifiManager.initialize(this, getMainLooper(), null);
        wifiClientReceiver = new WiFiClientBroadcastReceiver(wifiManager, wifichannel, this);
        
        wifiClientReceiverIntentFilter = new IntentFilter();;
        wifiClientReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        wifiClientReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        wifiClientReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        wifiClientReceiverIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_client, menu);
        
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    
    private void stopClientReceiver()
    {
        
       	try
    	{
            unregisterReceiver(wifiClientReceiver);
    	}
    	catch(IllegalArgumentException e)
    	{
    		//This will happen if the server was never running and the stop button was pressed.
    		//Do nothing in this case.
    	}

    }
    
    
    public void searchForPeers(View view) {
        registerReceiver(wifiClientReceiver, wifiClientReceiverIntentFilter);
        
        //Discover peers
        wifiManager.discoverPeers(wifichannel, null);


    }
    
    
    
    
    @Override
    protected void onResume() {
        super.onResume();
       // registerReceiver(wifiClientReceiver, wifiClientReceiverIntentFilter);
    }
    
    
    
    
    @Override
    protected void onPause() {
        super.onPause();
        //stopClientReceiver();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopClientReceiver();
    }
    
    
    public void setStatus(String message)
    {
    	TextView connectionStatusText = (TextView) findViewById(R.id.client_status_text);
    	connectionStatusText.setText(message);	
    }
    
    
    public void displayPeers(WifiP2pDeviceList peers)
    {
    	ListView peerView = (ListView) findViewById(R.id.peers_listview);
    	setStatus(peers.toString());
    }
    

}
