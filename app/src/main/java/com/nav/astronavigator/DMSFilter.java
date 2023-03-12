package com.nav.astronavigator;

/*
  This class written by Gerald Roehrbein in March 2023 is a ->real<- DMS format manager.

  If input contains a sign +-EWNS than the total computed DMS can not be above 180°(For example +179°59.99'00.59").
  Without sign it can not be above 360° (For example 359°59.99'00.59").

  The crude DMS format is allowed because of a mix of data from Nautical Almanac and data gathered by the navigator.
  A sextant offers 000°00'00.00". The algorithm offers a mix.

  The algorithm  distinguish between NS because if it is NS than max is 90°.

  I searched a lot of hours for ready to run solution but found none. That's why I created this one.
  Especially the wired Regular Expressions offered at the Web drove me crazy.
  Don't publish bullshit in BLOGS and discussion groups!

  This class seem to work good. So pls. if you use it don't remove my Copyright here. Thanks!
 */

import android.text.InputType;
import android.text.Spanned;
import android.text.method.NumberKeyListener;
import android.widget.TextView;

import java.nio.charset.StandardCharsets;

class DMSFilter extends NumberKeyListener {

    /*
       ToDo: in onViewCreated add:
            TextView mdfDeclination=view.findViewById(R.id.dfDeclination);
            mdfDeclination.setFilters(new InputFilter[] { new DMSFilter(), new InputFilter.LengthFilter(17)});
            mdfDeclination.addTextChangedListener(new TextWatcher() {
            String old;
            DMSFilter DMSFilter=new DMSFilter();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                old=s.toString();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Params: New String given by onTextChanged, Old String taken from beforeTextChanged, editfield where figures come from
                DMSFilter.checkDMS(s.toString(),old,mdfDeclination);
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation
            }
        });
     */
    public   boolean bDMSParser(String inputStr, double maxDegree)    {
        boolean retval=false;

        if (!inputStr.matches ("^([+-]?\\d{0,3}\\°?\\d{0,2}\\.?\\d{0,2}\\'?\\d{0,2}\\.?\\d{0,2}\\\"?)$" ))
        {
            return false;
        }

        String dummy=inputStr;


        if (dummy.indexOf("°")!=-1) {
            String sub = dummy.substring(0, dummy.indexOf("°"));
            //System.out.println("Substring degrees "+sub);
            if (sub.length()>0) {

                try {
                    int comp=Integer.valueOf(sub);
                    double degrees = Double.valueOf(sub);
                    if ((double)comp!=degrees) return false;
                    //System.out.println("degrees "+degrees);
                    if (degrees > maxDegree-1) {
                        //System.out.println("Degrees above 360? ");
                        //DialogBox.show("Alert","Degrees above 359°");
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        }
        double minutes=0;
        if ((dummy.indexOf("°")!=-1) && (dummy.indexOf("'")!=-1)) {
            String sub=dummy.substring(dummy.indexOf("°") + 1, dummy.indexOf("'"));
            //System.out.println("Substring degrees "+sub);
            if (sub.length()>5) return false;
            if (sub.length()>0  ) {
                try {
                    minutes = Double.valueOf(sub);
                    //System.out.println("degrees "+degrees);
                    if (minutes > 59.99) {
                        //System.out.println("Degrees above 360? ");
                        //DialogBox.show("Alert","Degrees above 359°");
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        }

        if ((dummy.indexOf("'")!=-1) && (dummy.indexOf("\"")!=-1)) {
            String sub=dummy.substring(dummy.indexOf("'") + 1, dummy.indexOf("\""));
            //System.out.println("Substring degrees "+sub);
            if (sub.length()>5) return false;
            if (sub.length()>0  ) {
                try {
                    double seconds = Double.valueOf(sub);
                    //System.out.println("degrees "+degrees);

                    if (seconds > ((60. -minutes)*60.)) {
                        // Es duerfen nur die bis zu 360° oder 180°  fehlenden Sekunden eingegeben werden
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        }


            /*
              ToDo: Add code to check range of minutes and seconds also!
             */

        return true;
    }

    public void checkDMS(String newString, String oldString, TextView tv)
    {
        if (newString.length()>0) {

            if (newString.toString().contains("°") && newString.toString().contains("\'") && newString.toString().contains("\"")) //Don't allow to delete this special characters!
            {

                char signs[] = {'N', 'S', 'W', 'E', '+', '-'};
                String parse = newString.toString();
                double maxDegree = 360;
                for (int ctr = 0; ctr < signs.length; ctr++) {
                    if (newString.toString().getBytes(StandardCharsets.UTF_8)[0] == signs[ctr]) {
                        parse = newString.toString().substring(1, newString.length());

                         switch (signs[ctr])
                         {
                             case 'N':
                             case 'S': maxDegree=90;
                                         break;
                             default:
                                 maxDegree=180;

                         }
                        break;
                    }
                }

                //if (old.length()>0)
                if (!bDMSParser(parse, maxDegree)) {
                    tv.setText(oldString);
                    return;
                }
                if (parse.matches("^([+-]?\\d{0,4})$")) {  // Check for Number! If someone try to enter decimal number out of range!
                    if (parse.length() > 0) {
                        double d = Double.valueOf(parse);
                        if (d >= maxDegree) {
                            tv.setText(oldString);
                            return;
                        }

                        if (d <= -maxDegree) {
                            tv.setText(oldString);
                            return;
                        }
                    }
                }
            }
            else {
                tv.setText(oldString);
                return;
            }
        }


    }

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
