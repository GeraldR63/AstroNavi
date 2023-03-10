package com.nav.astronavigator;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.nav.astronavigator.DelayedMessage;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IllegalFormatPrecisionException;
import java.util.Locale;
import com.nav.astronavigator.DialogBox;

public class calculus {

    static View view;
    static DialogBox DialogBox=new DialogBox(view);
    static DelayedMessage msg;

    calculus(View view)
    {
        this.view=view;
        msg=new DelayedMessage(view);
    }


    public static double DMS2Real(String Degree_DMS)
    {
        int degrees=0;
        double minutes=0;
        double seconds=0;
        int sign;
        //Snackbar snackbar;
        //

        // Parser fuer xxx°yy.yy'ss.ss"
        //             359°59.99'59.99"
        Degree_DMS=Degree_DMS.replace("E","-");  // East negative
        Degree_DMS=Degree_DMS.replace("O","-");  // Ost negative
        Degree_DMS=Degree_DMS.replace("S","-");  // South negative
        Degree_DMS=Degree_DMS.replace("e","-");  // East negative
        Degree_DMS=Degree_DMS.replace("o","-");  // Ost negative
        Degree_DMS=Degree_DMS.replace("s","-");  // South negative


        sign=Degree_DMS.contains("-")==true? -1: 1;

        try {
            //System.out.println("degrees "+Degree_DMS);
            String sub=Degree_DMS.substring(0, Degree_DMS.indexOf("°"));
            //System.out.println("Substring degrees "+sub);
            degrees =  Integer.valueOf(sub);
            //System.out.println("degrees "+degrees);
            if (degrees >359)
            {
                //System.out.println("Degrees above 360? ");
                //DialogBox.show("Alert","Degrees above 359°");
                msg.ShowSnackbar("Degrees above 359°");
                return -2;
            }
            //System.out.println("degrees "+degrees);

            sub=Degree_DMS.substring(Degree_DMS.indexOf("°") + 1, Degree_DMS.indexOf("'"));
            //System.out.println("Substring minutes "+sub);
            if (  Double.valueOf(sub)>59.99)
            {
                System.out.println("Minutes above 59? ");
                msg.ShowSnackbar("Minutes above 59.99'");
                return -3;
            }

            minutes =  (double)Double.valueOf(sub) / 60;
            //System.out.println("minutes "+minutes);

            sub=Degree_DMS.substring(Degree_DMS.indexOf("\'")+1, Degree_DMS.indexOf("\""));
            //System.out.println("Substring seconds "+sub);
            if (Double.valueOf(sub)>59.99)
            {
                //System.out.println("Seconds above 59.59? ");
                msg.ShowSnackbar("Seconds above 59.99\"");
                return -4;
            }

            seconds = (double) Double.valueOf(sub) / 3600;


            //System.out.println("seconds "+seconds);
        }
        catch (Exception e)
        {
            //System.out.println("DMS2Real format error ");
            msg.ShowSnackbar("Unknown Format error in your input");
            return -1;
        }

        return (Math.abs(degrees)+minutes+seconds)*sign;
    }

    public static String Real2DMS(double dms)
    {
        int degrees;
        double  minutes;
        int iminutes;
        double seconds;
        int iseconds;
        double sign;


        //
        try {
            degrees = (int) dms;
            minutes = Math.abs((dms - degrees) * 60);
            iminutes = (int) minutes;
            seconds = Math.abs((minutes - Double.valueOf(iminutes)) * 60);
            minutes -= Math.abs((minutes - Double.valueOf(iminutes)) * 60)/60;


            System.out.println("Degree "+degrees+" Minutes "+String.format(Locale.ROOT,"%02.2f",minutes)+" Seconds "+String.format(Locale.ROOT,"%02.2f",seconds));


            //minutes -= seconds;
            if (minutes<0) minutes=0;



            System.out.println("Seconds "+seconds+" String Format "+String.format(Locale.ROOT,"%02.2f",seconds));
            if (Double.valueOf(String.format(Locale.ROOT,"%02.2f",seconds))>=60)
            {
                minutes++;
                seconds-=60;
            }

            System.out.println("Minutes "+minutes+" String Format "+String.format(Locale.ROOT,"%02.2f",minutes));
            if (Double.valueOf(String.format(Locale.ROOT,"%02.2f",minutes))>=60)
            {
                degrees++;
                minutes-=60;
            }

            if (degrees>=360)
            {
                degrees-=360;
            }


            return String.format("%03d",degrees)+"°"+String.format(Locale.ROOT,"%02.0f",Math.abs(minutes))+"'"+String.format(Locale.ROOT,"%02.2f",Math.abs(seconds))+"\"";
        }
        catch (Exception e)
        {
            System.out.println("Format error Real2DMS. ");
            //msg.ShowSnackbar("Only D°M'S\" to DDD°MM'SS.SS\" allowed!");
            return "???°??.?'??.??\"";
        }

        //return "000°00'00.00\"";
    }

    public static int DMSFormatCheck(String DMS)
    // Parser fuer xxx°yy'ss.ss"
    //             359°59'59.59"
    // Liefert 1 zurueck wenn der String im DMS Format ist ansonsten 0
    {
        if (DMS2Real(DMS)>-1) return 1;  // 000°00'00.00" sind Unsinn. Braucht man zur Berechnung nicht.
        return 0;
    }

    public static double HMS2Real(String Degree_DMS)
    {
        int hour=0;
        double minutes=0;
        double seconds=0;
        int sign;
        int position=Degree_DMS.indexOf(":");  // Erster Doppelpunkt und weitere

        //

        // Parser fuer xxx°yy'ss.ss"  oder xxx°yy.yy'ss.ss"
        //             359°59'59.59"  oder 359°59.59'59.59"
        //             (Eigentlich ist Minuten mit Dezimalstelle Schwachsinn aber im NA findet man das für GHA und andere Werte wenn keine Sekunden angegeben sind.)

        try {
            String sub=Degree_DMS.substring(0,position);
            //System.out.println("Substring hour "+sub);
            hour =  Integer.valueOf(sub);
            sub=Degree_DMS.substring(position + 1, position=Degree_DMS.indexOf(":", position+1));
            //System.out.println("Substring minutes "+sub);
            minutes =  (double) Integer.valueOf(sub) / 60;
            sub=Degree_DMS.substring(position+1, Degree_DMS.length());
            //System.out.println("Substring seconds "+sub);
            seconds = (double) Double.valueOf(sub) / 3600;
            //System.out.println("seconds "+seconds);
        }
        catch (Exception e)
        {
            System.out.println("HMS2Real format error ");
            return -1;
        }

        return hour+minutes+seconds;
    }

    public static double MS2Real(String HMS)
    {
        int hour=0;
        double minutes=0;
        double seconds=0;
        int sign;
        int position=HMS.indexOf(":");  // Erster Doppelpunkt und weitere

        //
        //System.out.println("Zeit Eingabe "+HMS);
        // Parser fuer xxx°yy'ss.ss"
        //             359°59'59.59"

        try {
            String sub=HMS.substring(0,position);
            //System.out.println("Substring hour "+sub);
            hour =  Integer.valueOf(sub);
            sub=HMS.substring(position + 1, position=HMS.indexOf(":", position+1));
            //System.out.println("Substring minutes "+sub);
            minutes =  (double) Integer.valueOf(sub) / 60;
            sub=HMS.substring(position+1, HMS.length());
            //System.out.println("Substring seconds "+sub);
            seconds = (double) Double.valueOf(sub) / 3600;
            //System.out.println("seconds "+seconds);
        }
        catch (Exception e)
        {
            System.out.println("MS2Real format error ");
            return -1;
        }

        return minutes+seconds;
    }



}
