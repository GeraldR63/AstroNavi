package com.nav.astronavigator;

import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
    /*
       (w) 2022 to 2023 by Gerald Roehrbein
       This code is under the GPL 2.0
       You can reuse it by yourself but I expect that you will leave a hint on me.
       I publish since 40 years code an I know the way others take it an make their own property from it.
       Girls, that's stupid. It's very stupid to hinder the creative ones, use their code and intellectual work
       and let them die anonymous. That's why, especially here in Germany nothing works anymore because
       all of the thefts and liar have to work today by themself.

       Sorry. I'm very upset to this. I love the people in the US. The only ones ever paid for my ShareWare
       in the past 40 years came from the USA. Germans pay for nothing. But they pay now a price. A high price.
     */

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