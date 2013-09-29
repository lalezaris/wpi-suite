package edu.wpi.cs.myfirstapp;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends FragmentActivity /*implements EnterButtonListener */{

	public static final String NAME = "edu.wpi.cs.myfirstapp.NAME";
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	public void sendName(View view) {
		names.add(nameField.getText().toString());
		adapter.notifyDataSetChanged();
		nameField.setText("");
		
	}
    

}
