package com.udacity.stockhawk.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class AddResultDialog extends DialogFragment {

    private static final String MESSAGE_KEY = "msg_key";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String message = getArguments().getString(MESSAGE_KEY);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message).setPositiveButton("Ok", null);

        return builder.create();

    }

    public static AddResultDialog newInstance(String message) {

        AddResultDialog instance = new AddResultDialog();

        Bundle bundle = new Bundle();
        bundle.putString(MESSAGE_KEY, message);

        instance.setArguments(bundle);

        return instance;

    }

}
