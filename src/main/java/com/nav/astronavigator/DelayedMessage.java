package com.nav.astronavigator;

import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

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
