package com.nav.astronavigator;

import android.text.InputType;
import android.text.Spanned;
import android.text.method.NumberKeyListener;

class DMSFilter extends NumberKeyListener {

    /*
       ToDo: If ready to run move filter and function below into this class and create file "DMSFilter.java".
     */

    @Override
    public int getInputType() {
        return InputType.TYPE_CLASS_DATETIME;
    }

    @Override
    protected char[] getAcceptedChars() {
        return new char[]{'+','-','0', '1', '2','°', '3', '4','\'', '5', '6','.', '7', '8','"','9','N','S','E','W',' '};
    }


    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {


                /*
                     ToDo: Move this in the  DMSFilter class if it works!

                     Since development of a DMS parser takes a lot of time I avoided this up to now
                     but it makes sense to have one.

                */

        //System.out.println("Source      "+source+" "+start+" "+end);
        //System.out.println("Destination "+dest+" "+dstart+" "+dend);

        for (int i = start; i < end; i++) {

            char allowed[]={'N','S','W','E','+','-','0', '1', '2','°', '3', '4','\'', '5', '6','.', '7', '8','"','9'};
            boolean bMatches=false;

            for (int n=0;n<allowed.length;n++) {
                if (source.charAt(i)==allowed[n]) {bMatches=true;}   // Zulassiges zeichen
            }
            //if (!Character.isLetterOrDigit(source.charAt(i))) {
            if (!bMatches) {
                return "";           //Illegal character!
            }
        }
        return null;
    };
}
