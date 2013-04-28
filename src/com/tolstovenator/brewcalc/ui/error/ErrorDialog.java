package com.tolstovenator.brewcalc.ui.error;

import com.tolstovenator.brewcalc.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ErrorDialog extends DialogFragment{
	
	private String message;
	
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    	builder.setMessage(message)
        .setTitle(R.string.error_dialog_title);
    	builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
    	return builder.create();
	}

	
}
