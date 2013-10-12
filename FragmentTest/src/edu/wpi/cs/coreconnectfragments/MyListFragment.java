package edu.wpi.cs.coreconnectfragments;

import edu.wpi.cs.fragmenttest.R;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyListFragment extends Fragment {

	@SuppressWarnings("unused")
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
		
		System.out.println("UpdateDetail called!");
		
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://130.215.169.129:8080/WPISuite/API"));
		
		System.out.println("Set Default Network Configuration");
		
		// Form the basic auth string
		String basicAuth = "Basic ";
		//String password = new String("password");
		//String credentials = "admin" + ":" + password;
		//basicAuth += Base64.encodeBase64String(credentials.getBytes());
		basicAuth += "YWRtaW46cGFzc3dvcmQ=";

		System.out.println(basicAuth);
		// Create and send the login request
		Request request = Network.getInstance().makeRequest("login", HttpMethod.POST);
		request.addHeader("Authorization", basicAuth);
		request.addObserver(new LoginRequestObserver());
		
		System.out.println("Sending request");
		
		request.send();
		
		System.out.println("Request sent");

		// Send data to Activity
	}
}