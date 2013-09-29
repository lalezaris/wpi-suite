/**
 * 
 */
package edu.wpi.cs.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * @author Sam
 * 
 */
public class EnterFragment extends Fragment {
EnterButtonListener callback;

	public interface EnterButtonListener{
		public void sendInfo(String name);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.enter_view, container, false);
	}

	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		
		try{callback = (EnterButtonListener) activity;
		} catch (ClassCastException e){
			throw new ClassCastException(activity.toString() + "must implement EnterButtonListener");
		}
	}
	
}
