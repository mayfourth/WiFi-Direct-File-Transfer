package edu.pdx.cs410.wifi.direct.file.transfer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Block auto opening keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
       
    public void startFileBrowseActivity(View view) {

    }
    
    public void startServerService(View view) {
    	TextView serverServiceStatus = (TextView) findViewById(R.id.server_service_status_text);
    	serverServiceStatus.setText(R.string.server_service_running);

    }
    
    public void stopServerService(View view) {
    	TextView serverServiceStatus = (TextView) findViewById(R.id.server_service_status_text);
    	serverServiceStatus.setText(R.string.server_service_stopped);

    }
    
    public void searchForServers(View view) {

    }   
    
}
