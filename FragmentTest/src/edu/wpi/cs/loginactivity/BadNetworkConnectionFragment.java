package edu.wpi.cs.loginactivity;

import edu.wpi.cs.fragmenttest.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class BadNetworkConnectionFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_bad_network).setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   //closes dialog, allows waiting for re-attempt
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}