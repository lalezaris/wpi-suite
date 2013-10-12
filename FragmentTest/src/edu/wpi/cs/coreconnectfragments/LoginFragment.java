package edu.wpi.cs.coreconnectfragments;

import edu.wpi.cs.fragmenttest.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoginFragment extends Fragment {

	@SuppressWarnings("unused")
	private OnItemSelectedListener listener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_login,
				container, false);
		
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
}