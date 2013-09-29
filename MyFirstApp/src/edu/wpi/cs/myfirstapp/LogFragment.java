package edu.wpi.cs.myfirstapp;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LogFragment extends Fragment {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
//		List<String> nameStringArray = new ArrayList<String>();
//		ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.log_view, nameStringArray);
//		
//		ListView listView = (ListView) findViewById(R.id.log_view);
//		
		return inflater.inflate(R.layout.log_view,  container, false);
	}

	
	public void addName(String name) {
		// TODO Auto-generated method stub
		
	}
}
