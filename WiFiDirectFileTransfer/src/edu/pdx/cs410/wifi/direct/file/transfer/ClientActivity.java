package edu.pdx.cs410.wifi.direct.file.transfer;

import java.util.ArrayList;

import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.widget.AdapterView.OnItemClickListener;



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
        
        //Discover peers, no call back method given
        wifiManager.discoverPeers(wifichannel, null);

    }
    
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(wifiClientReceiver, wifiClientReceiverIntentFilter);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        //Continue to listen for wifi related system broadcasts even when paused
        //stopClientReceiver();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        //Unregister broadcast receiver
        stopClientReceiver();
    }
    
    
    public void setClientWifiStatus(String message)
    {
    	TextView connectionStatusText = (TextView) findViewById(R.id.client_wifi_status_text);
    	connectionStatusText.setText(message);	
    }
    
    public void setClientStatus(String message)
    {
    	TextView connectionStatusText = (TextView) findViewById(R.id.client_status_text);
    	connectionStatusText.setText(message);	
    }
    
    
    public void displayPeers(final WifiP2pDeviceList peers)
    {
    	//Dialog to show errors/status
		final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("WiFi Direct File Transfer");
		
		//Get list view
    	ListView peerView = (ListView) findViewById(R.id.peers_listview);
    	
    	//Make array list
    	ArrayList<String> peersStringArrayList = new ArrayList<String>();
    	
    	//Fill array list with strings of peer names
    	for(WifiP2pDevice wd : peers.getDeviceList())
    	{
    		peersStringArrayList.add(wd.deviceName);
    	}
    	
    	//Set list view as clickable
    	peerView.setClickable(true);
    	   
    	//Make adapter to connect peer data to list view
    	ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, peersStringArrayList.toArray());    			
    	
    	//Show peer data in listview
    	peerView.setAdapter(arrayAdapter);
    		
    	
		peerView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int arg2,long arg3) {
				
				//Get string from textview
				TextView tv = (TextView) view;
				
				WifiP2pDevice targetDevice = null;
				
				//Search all known peers for matching name
		    	for(WifiP2pDevice wd : peers.getDeviceList())
		    	{
		    		if(wd.deviceName.equals(tv.getText()))
		    				targetDevice = wd;		    			
		    	}
				
				if(targetDevice != null)
				{
					//Connect to selected peer
					connectToPeer(targetDevice);
										
				}
				else
				{
					dialog.setMessage("Failed");
					dialog.show();
										
				}							
			}			
				// TODO Auto-generated method stub				
			});
  	
    }
        
    public void connectToPeer(final WifiP2pDevice targetDevice)
    {
    	WifiP2pConfig config = new WifiP2pConfig();
    	config.deviceAddress = targetDevice.deviceAddress;
    	wifiManager.connect(wifichannel, config, new WifiP2pManager.ActionListener()  {
    	    public void onSuccess() {
    	    	//setClientStatus("Connection to " + targetDevice.deviceName + " sucessful");
    	    }

    	    public void onFailure(int reason) {
    	    	//setClientStatus("Connection to " + targetDevice.deviceName + " failed");

    	    }
    	});    	
    
    }

}
