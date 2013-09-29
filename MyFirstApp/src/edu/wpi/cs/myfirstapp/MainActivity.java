package edu.wpi.cs.myfirstapp;

import edu.wpi.cs.myfirstapp.EnterFragment.EnterButtonListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class MainActivity extends FragmentActivity implements EnterButtonListener {

	public static final String NAME = "edu.wpi.cs.myfirstapp.NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void sendInfo(String name) {
		
		LogFragment logFrag = (LogFragment) getSupportFragmentManager().findFragmentById(R.id.log_fragment);
		
		logFrag.addName(name);
		
	}
    

}
