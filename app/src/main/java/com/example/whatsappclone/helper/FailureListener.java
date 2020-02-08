package com.example.whatsappclone.helper;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;

public class Failure implements OnFailureListener {

    private Activity activity;
    private String msg;

    public Failure(Activity activity, String msg) {
        this.activity = activity;
        this.msg = msg;
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText( activity, msg, Toast.LENGTH_SHORT);
        e.printStackTrace();
    }
}
