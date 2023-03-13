package com.nav.astronavigator;
//import android.content.Context;
//import android.graphics.Color;
import android.view.View;
//import android.widget.LinearLayout;

//import com.nav.astronavigator.DelayedMessage;
//import com.google.android.material.snackbar.Snackbar;

//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.IllegalFormatPrecisionException;
import java.util.Locale;
//import com.nav.astronavigator.DialogBox;

public class calculus {
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
    static double pi=3.14159265358979323846;
    static double twopi=(2*pi);

    static View view;
    //static DialogBox DialogBox=new DialogBox(view);
    static DelayedMessage msg;
    //static double pi= 3.14159265358979323846;

    calculus(View view)
    {
        this.view=view;
       // msg=new DelayedMessage(view);
    }


    public  static double DMS2Real(String Degree_DMS)
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
        Degree_DMS=Degree_DMS.replace("S","-");  // South negative
        Degree_DMS=Degree_DMS.replace("N","+");  // North positive
        Degree_DMS=Degree_DMS.replace("W","+");  // West positive


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
            msg.ShowSnackbar("Format DMS2Real >"+Degree_DMS+"<");
            return -1;
        }

        return (Math.abs(degrees)+minutes+seconds)*sign;
    }

    public  static String Real2DMS(double dms)
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


            //System.out.println("Degree "+degrees+" Minutes "+String.format(Locale.ROOT,"%02.2f",minutes)+" Seconds "+String.format(Locale.ROOT,"%02.2f",seconds));


            //minutes -= seconds;
            if (minutes<0) minutes=0;



            //System.out.println("Seconds "+seconds+" String Format "+String.format(Locale.ROOT,"%02.2f",seconds));
            if (Double.valueOf(String.format(Locale.ROOT,"%02.2f",seconds))>=60)
            {
                minutes++;
                seconds-=60;
            }

            //System.out.println("Minutes "+minutes+" String Format "+String.format(Locale.ROOT,"%02.2f",minutes));
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
            System.out.println("Format Real2DMS >"+dms+"<");
            //msg.ShowSnackbar("Only D°M'S\" to DDD°MM'SS.SS\" allowed!");
            return "???°??.?'??.??\"";
        }

        //return "000°00'00.00\"";
    }

    public int DMSFormatCheck(String DMS)
    // Parser fuer xxx°yy'ss.ss"
    //             359°59'59.59"
    // Liefert 1 zurueck wenn der String im DMS Format ist ansonsten 0
    {
        if (DMS2Real(DMS)>-1) return 1;  // 000°00'00.00" sind Unsinn. Braucht man zur Berechnung nicht.
        return 0;
    }

    public  static double HMS2Real(String Degree_DMS)
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

    public  static double MS2Real(String HMS)
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
            System.out.println("Format MS2Real >"+HMS+"<");
            return -1;
        }

        return minutes+seconds;
    }

    /*
        Code below comes from code I use in my Arduino High Altitude Balloon projects.
        This code is some optimized code I found at the web. It's optimized for speed and memory consumption.
        So far I remember the original source was Silicon Graphics Labs.

        -----------------------------gps.h C language by RN start
        #ifndef GPS
        #define GPS
        #include <stdlib.h>
        #include <stdio.h>
        #include <math.h>


        //   (C) 2019 Gerald Roehrbein
        //   Ueberarbeitete und stark vereinfacht aus unterschiedlichsten Quellen des Inder nett.



        double distance_gps(double lat1, double lon1, double lat2, double lon2); //Distance between 1 und 2
        double degree(double lat1, double lon1, double lat2, double lon2);       //Angle from 1 to 2
        double deg2rad(double deg);                                              //DEG to RAD (we already have it in Java in MATH container class lib)
        double rad2deg(double rad);                                              //RAD to DEG  (we already have it in Java in MATH container class lib)
        #endif
        -----------------------------gps.h C language by RN end

        It's easy to translate code below to C/C++

        gps.c starts below this comment.
     */
    double deg_rad(double lat1,double lon1,double lat2, double lon2)
    {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        return Math.acos(Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(lon2-lon1)));
    }


    double degree(double lat1,double lon1,double lat2, double lon2)
    {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        double head= rad2deg(Math.atan2((Math.sin(deg2rad(lon2-lon1))*Math.cos(deg2rad(lat2))),((Math.cos(deg2rad(lat1))*Math.sin(deg2rad(lat2)))-(Math.sin(deg2rad(lat1))*Math.cos(deg2rad(lat2))*Math.cos(deg2rad(lon2-lon1))))));
        return (head<=0?head+360:head);
    }



    double distance_gps(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        //printf("Distance=%lf\n\r", acos(sin(deg2rad(lat1)) * sin(deg2rad(lat2)) + cos(deg2rad(lat1)) * cos(deg2rad(lat2)) * cos(deg2rad(lon2-lon1))) * 6378.388);
        return deg_rad(lat1,lon1,lat2,lon2) * 6378.388;
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    double deg2rad(double deg) {
        return (deg * pi / 180);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    double rad2deg(double rad) {
        return (rad / (pi / 180) );
    }
    /*
    gps.c ends above this comment.
    */

    public static double mdy_sect(String Date, String Time)
    /*
       From P.Lutus Source eph.c

       Calculates the seconds since 1900 up to "now"?!.
    */

    {
        double r;
        int year,month,day;
        int hour, minute, seconds;

        /* Parser hh:mm:ss */
        int position=Time.indexOf(":");
        hour  =  Integer.valueOf(Time.substring(0,position));
        minute = Integer.valueOf(Time.substring(position + 1, position=Time.indexOf(":", position+1)));
        seconds= Integer.valueOf(Time.substring(position+1, Time.length()));

        /* Parser dd.mm.yyyy */
        position=Date.indexOf(".");
        day  =  Integer.valueOf(Date.substring(0,position));
        month = Integer.valueOf(Date.substring(position + 1, position=Date.indexOf(".", position+1)));
        year= Integer.valueOf(Date.substring(position+1, Date.length()));

        if(year > 1900)
            year -= 1900;
        month++;

        if(month < 4)
        {
            month += 12;
            year -= 1;
        }

        r = day + Math.floor(month * 30.6001) + (year * 365.25) - 63;
        r = Math.floor(r);
        r = (r * 24) + hour;
        r = (r * 60) + minute;
        r = (r * 60) + seconds;
        return(r);
    }

     public static double cv (String Date, String Time) {
         double cv = 0.0;  // CV is a correction multiplicator which depends on the days since 1900
         try {
             // mdy_sect returns the seconds since 1900
             // Devided by 86400 give the days since 1900.
             // The meaning of -29220.0 is not clear.
             // This figure divided by 365.25 days give more or less the years since 1900.
             // In my opinion this should produce a multiplier related to the date the eph data came from.
             cv = (((mdy_sect(Date, Time)) / 86400.0) - 29220.0) / 365.25;
         } catch (Exception e) {
             cv = 0.0;
         }
         return cv;
     }

    public static double timeToAngle(double ltime){  // Time in Seconds since 00:00:00
        //Real length of a day is 23 hours, 56 minutes, and 4 seconds
        double r = (((ltime) / (24*60*60)  ) * twopi);
        //double r = (((ltime) / (23.*60.*60.+56.*60.+4.09)  ) * twopi);
        return Math.toDegrees(r)+180;
    }


}
