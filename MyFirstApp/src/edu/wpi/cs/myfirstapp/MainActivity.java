package edu.wpi.cs.myfirstapp;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends FragmentActivity /*implements EnterButtonListener */{

	ListView listView;
	Button enterButton;
	EditText  nameField;
	
	ArrayAdapter<String> adapter;
	List<String> names;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        listView = (ListView)findViewById(R.id.log_view);
        enterButton = (Button)findViewById(R.id.enter_button);
        nameField = (EditText)findViewById(R.id.name_input);
        
        names = new ArrayList<String>();
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);
        
        registerForContextMenu(listView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
    	super.onCreateContextMenu(menu, v, menuInfo);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.context_menu, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item){
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	switch(item.getItemId()){
    	case R.id.delete_entry:
    		delete_entry(info.id);
    		return true;
    		
    	default:
            return super.onContextItemSelected(item);
    	}
    }
    
    
	private void delete_entry(long id) {
		
		for(int x = 0; x < listView.getCount(); x++){
			if(listView.getItemIdAtPosition(x) == id){
				names.remove(x);
				adapter.notifyDataSetChanged();
			}
		}
	}

	

	public void sendName(View view) {
		names.add(nameField.getText().toString());
		adapter.notifyDataSetChanged();
		nameField.setText("");
		
	}
    

}
