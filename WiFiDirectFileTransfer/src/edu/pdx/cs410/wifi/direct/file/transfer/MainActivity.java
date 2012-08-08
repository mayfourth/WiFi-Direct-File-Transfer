package edu.pdx.cs410.wifi.direct.file.transfer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	//Intent used to start server service
	private Intent serverServiceIntent; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Block auto opening keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        //Initially set to null
        serverServiceIntent = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
       
    public void startFileBrowseActivity(View view) {

    }
    
    public void startServerService(View view) {
    	   	   	
    	//Stop any running service first
    	stopServerService(null);
    	
    	EditText filePathEditText = (EditText) findViewById(R.id.server_file_path);
    	String filePath = filePathEditText.getText().toString();

    	serverServiceIntent = new Intent(this, ServerService.class);
    	serverServiceIntent.putExtra("filepath", filePath);
        startService(serverServiceIntent);

    	//Set status to running
    	TextView serverServiceStatus = (TextView) findViewById(R.id.server_service_status_text);
    	serverServiceStatus.setText(R.string.server_service_running);

    }
    
    public void stopServerService(View view) {
    	//If the intent isn't null, the service may be running. Attempt to stop. 
    	if(serverServiceIntent != null)
    	{
    		stopService(serverServiceIntent);
    	}
    	
    	//set status to stopped
    	TextView serverServiceStatus = (TextView) findViewById(R.id.server_service_status_text);
    	serverServiceStatus.setText(R.string.server_service_stopped);

    }
    
    public void searchForServers(View view) {

    }   
    
}
