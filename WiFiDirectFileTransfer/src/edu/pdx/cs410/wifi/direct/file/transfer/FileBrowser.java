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

import java.io.File;
import java.util.ArrayList;

import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;


/*
 Used http://android-er.blogspot.com/2010/01/implement-simple-file-explorer-in.html as a reference 
 to create this activity.
 */

public class FileBrowser extends Activity {

	private String root;
	private String currentPath;
	
	private ArrayList<String> targets;
	private ArrayList<String> paths;

	
	private File targetFile;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);
        getActionBar().setDisplayHomeAsUpEnabled(true);
              
        root = "/";
        currentPath = root;
        
        targets = null;
        paths = null;
        
        targetFile = null;

        showDir(currentPath);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_file_browser, menu);
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
    
    
    public void selectDirectory(View view) {
    	
	     File f = new File(currentPath);
	     targetFile = f;
	     
	     //Return target File to activity
		  returnTarget();


    }
    

    public void setCurrentPathText(String message)
    {
    	TextView fileTransferStatusText = (TextView) findViewById(R.id.current_path);
    	fileTransferStatusText.setText(message);	
    }


	private void showDir(String targetDirectory){
		
		setCurrentPathText("Current Directory: " + currentPath);
		
		targets = new ArrayList<String>();
		paths = new ArrayList<String>();
				
	     File f = new File(targetDirectory);
	     File[] directoryContents = f.listFiles();
	     
	     
		if (!targetDirectory.equals(root))

		{
			targets.add(root);
			paths.add(root);
			targets.add("../");
			paths.add(f.getParent());
		}
		
		for(File target: directoryContents)
		{
			paths.add(target.getPath());
			
			if(target.isDirectory())
			{
		        targets.add(target.getName() + "/");
			}
			else
			{
		        targets.add(target.getName());

			}

		}
		
		ListView fileBrowserListView = (ListView) findViewById(R.id.file_browser_listview);

	    ArrayAdapter<String> directoryData = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, targets);
	    fileBrowserListView.setAdapter(directoryData);
	    
	    
	    
	    
	    fileBrowserListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int pos,long id) {
				
				  File f = new File(paths.get(pos));
				  
				  if(f.isFile())
				  {
					  targetFile = f;
					  returnTarget();
					  //Return target File to activity
				  }
				  else
				  {
					  //f must be a dir
					  if(f.canRead())
					  {
						  currentPath = paths.get(pos);
						  showDir(paths.get(pos));
					  }
					  
				  }

				
			}			
				// TODO Auto-generated method stub				
			});
	    
	    /*
		final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("WiFi Direct File Transfer");
		*/
	   
		
	}
	
	public void returnTarget()
	{
		
		Intent returnIntent = new Intent();
		returnIntent.putExtra("file", targetFile);
		setResult(RESULT_OK, returnIntent);
		finish();
		
	}
	
	

}



