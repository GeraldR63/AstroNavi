package com.nav.astronavigator;

import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

public class DialogBox
{
    private AlertDialog dialog;
    private View view;

    DialogBox(View view)
    {
        this.view=view;
    }
    void show(String title, String message)
    {
        try {
            dialog = new AlertDialog.Builder(this.view.getContext()) // Pass a reference to your main activity here
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.cancel();
                        }
                    })
                    .show();
        } catch (Exception e)
        {

        }
    }


}