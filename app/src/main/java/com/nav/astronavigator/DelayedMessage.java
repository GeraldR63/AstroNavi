package com.nav.astronavigator;

import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
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

public class DelayedMessage {
    static View view;
    DelayedMessage (View view)
    {
        this.view=view;
    }
    public static void ShowSnackbar(String message)
    {

        Snackbar snackbar=Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View snackbarLayout = snackbar.getView();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        // Layout must match parent layout type
        lp.setMargins(50, 0, 0, 0);
        // Margins relative to the parent view.
        // This would be 50 from the top left.
        snackbarLayout.setLayoutParams(lp);
        snackbar.show();
    }
}
