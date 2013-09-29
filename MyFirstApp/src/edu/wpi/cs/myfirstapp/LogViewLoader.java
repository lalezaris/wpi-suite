/**
 * 
 */
package edu.wpi.cs.myfirstapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

/**
 * @author Sam
 *
 */
public class LogViewLoader extends ListActivity implements LoaderCallbacks<D> {

	SimpleCursorAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		
	}
	
	@Override
	public Loader<D> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoadFinished(Loader<D> arg0, D arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoaderReset(Loader<D> arg0) {
		// TODO Auto-generated method stub
		
	}

}
