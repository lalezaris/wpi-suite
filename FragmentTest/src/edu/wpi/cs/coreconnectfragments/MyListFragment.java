package edu.wpi.cs.coreconnectfragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import edu.wpi.cs.fragmenttest.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyListFragment extends Fragment {

	private OnItemSelectedListener listener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_login,
				container, false);

		Button button = (Button) view.findViewById(R.id.login_button);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				updateDetail();
			}
		});
		return view;
	}

	public interface OnItemSelectedListener {
		public void onRssItemSelected(String link);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnItemSelectedListener) {
			listener = (OnItemSelectedListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implemenet MyListFragment.OnItemSelectedListener");
		}
	}

	// May also be triggered from the Activity
	public void updateDetail() {
		// Create fake data
		// String newTime = String.valueOf(System.currentTimeMillis());

		// set url http://localhost:8080/WPISuite/API
		// set http method
		// add headers

		listener.onRssItemSelected(new LoginTask().execute("aa").toString());

		// Send data to Activity
	}
}