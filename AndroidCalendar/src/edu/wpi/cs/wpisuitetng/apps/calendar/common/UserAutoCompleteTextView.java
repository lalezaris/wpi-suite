/**
 * 
 */
package edu.wpi.cs.wpisuitetng.apps.calendar.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.CompletionInfo;
import android.widget.AutoCompleteTextView;

/**
 * @author Nathan Longnecker
 *
 */
public class UserAutoCompleteTextView extends AutoCompleteTextView {

	public UserAutoCompleteTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public UserAutoCompleteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public UserAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCommitCompletion(CompletionInfo completion) {
		System.out.println("CommitCompletion!");
	}

}
