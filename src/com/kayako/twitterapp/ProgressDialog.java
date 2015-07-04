package com.kayako.twitterapp;

import com.kayako.twitterapp.R;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.TextView;

public class ProgressDialog extends Dialog {

    private TextView mDialogText;

    public ProgressDialog(Activity activityContext, String dialogMsg, boolean cancelable) {
        super(activityContext);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.components_progress_dialog);
        setCanceledOnTouchOutside(cancelable);
        mDialogText = (TextView) findViewById(R.id.progress_bar_animated_text);
        mDialogText.setText(dialogMsg);
        setCancelable(cancelable);
    }

    public void setDialogMessage(String message) {
        mDialogText.setText(message);
    }
}