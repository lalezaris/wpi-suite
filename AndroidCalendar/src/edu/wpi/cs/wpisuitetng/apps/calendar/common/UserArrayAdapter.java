/**
 * 
 */
package edu.wpi.cs.wpisuitetng.apps.calendar.common;

import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author Nathan Longnecker
 *
 */
public class UserArrayAdapter extends ArrayAdapter<User> {

	private Context context;
	
	public UserArrayAdapter(Context context, int resource, List<User> objects) {
		super(context, resource, objects);
		this.context = context;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }

        User user = getItem(position);
        if (user!= null) {
            // My layout has only one TextView
            TextView itemView = (TextView) view.findViewById(android.R.id.text1);
            if (itemView != null) {
                // do whatever you want with your string
                itemView.setText(String.format("%s", user.getName()));
            }
        }

        return view;
    }
}
