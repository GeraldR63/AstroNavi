package com.nav.astronavigator;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;

import com.nav.astronavigator.NauticalAlmanac;
import com.nav.astronavigator.DelayedMessage;
import com.nav.astronavigator.calculus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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

class NAData
{


    com.nav.astronavigator.calculus calculus;
  public String Time="20:39:23";
  public String Date="04.07.2022";

  public String DRLong;
  public String DRLat;
  public double FractionalDRLong;
  public double FractionalDRLat;
  public double Heading=325.0;
  public double Speed=20;
  public String CBName="Regulus";

  public String Fix="21:00:00";
  public String ObservationTime="20:39:23";
  public String ObservationDate="04.07.2023";


  public double Ho=27.0772;
  public double CBBearing=0.00;
  public double GHAArieshh24=232.6798;
  public double GHAAriesPlus1h=207.6150;

  public double SHA=137.3167;
  public double Declination=374.0600;
  public double Z=0.0;                       // Z(Azimuth)
  public double p;

  double Hc;
  double GHAAries;
  double GHA;
  double LHAAries;
  double LHAHO2102D;

    /*B29*/    double pIntercept=0.0;
    /*B34*/    double sinZ=0.0;
    /*B35*/    double sinZ2=0.0;
    /*B36*/    double cosZ=0.0;
    /*B37*/    double cosZ2=0.0;

   String postFixLast="_1";
   int activeStar=1;          // Numerisches Synonym fuer postFixLast
                              // Von NAData gibt es drei Instanzen fuer CB#1, CB#2, CB#3
                              // Die Funktion NADataInit legt fest
                              // fuer welche der drei Instanzen die Daten aus den
                              // SharedPreferences zu laden sind.
                              //
    public static final String MyPREFERENCES = "AstroNavPrefs" ;


     public void readPrefs(int activeStar,SharedPreferences sharedpreferences)
             /*
                 Liest aus den SharedPreferences die Daten des jeweiligen Sterns in lokale Variablen vom Typ
                 Double, weil damit einfacher gerechnet werden kann als mit HMS Daten.
                 Außerdem wird der jeweiligen Instance der Klasse mitgegeben an welche Position CB#1, CB#2,CB#3
                 sie steht.

                 Innerhalb der Instance werden alle Berechnungen, die ausschliesslich zu dem Stern gehoeren,
                 durchgefuehrt.
              */
     {
           try {

               this.activeStar = activeStar;
               postFixLast = "_" + String.valueOf(this.activeStar);

               CBName = sharedpreferences.getString("CBName" + postFixLast, "Regulus");
               // Checken ob Double.parseDouble auch mit dem Gradzeichen funktioniert!
               CBBearing = Double.parseDouble(sharedpreferences.getString("Bearing" + postFixLast, "-21.5").replace(",", "."));
               Fix = sharedpreferences.getString("FixTime", "11:55:00");
               ObservationTime = sharedpreferences.getString("ObservedTime" + postFixLast, "11:55:00");
               ObservationDate = sharedpreferences.getString("ObservedDate" + postFixLast, "01.01.2023");
               // Checken ob Double.parseDouble auch mit dem Gradzeichen funktioniert!
               Ho = Double.parseDouble(sharedpreferences.getString("Ho" + postFixLast, "22.5").replace(",", "."));

               GHAArieshh24 = calculus.DMS2Real(sharedpreferences.getString("GHAAries" + postFixLast, "359°59'59.99\""));
               GHAAriesPlus1h = calculus.DMS2Real(sharedpreferences.getString("GHAAriesPlus1" + postFixLast, "359°59'59.99\""));
               SHA = calculus.DMS2Real(sharedpreferences.getString("SHA" + postFixLast, "359°59'59.99\""));
               Declination = calculus.DMS2Real(sharedpreferences.getString("DeclinationNA" + postFixLast, "359°59'59.99\""));

             /*
             System.out.println("Declination "+CBName+ " "+ sharedpreferences.getString("DeclinationNA"+postFixLast, "359°59'59.99\""));
             System.out.println("Declination "+CBName+ " "+Declination);
             */



             /*
                Hier muss noch FractionalDRLong und DRLat initialisiert werden.
              */

               FractionalDRLat = calculus.DMS2Real(sharedpreferences.getString("DRLat", "032°00'00.0\""));
               FractionalDRLong = calculus.DMS2Real(sharedpreferences.getString("DRLong", "-015°00'00.0\""));
               Heading = Double.parseDouble(sharedpreferences.getString("DRHeading", "325.0").replace(",", "."));
               Speed = Double.parseDouble(sharedpreferences.getString("DRSpeed", "20.0").replace(",", "."));
           } catch (Exception e)
           {
               System.out.println("NADATA[x].readPrefs "+e.getMessage());
           }
     }

    /*
       Der Interpolationsfaktor wird benoetigt, um an Hand von GHAArieshh24 und GHAAriesPlus1h die reale GHA
       aus den Daten des Nautical Almanac zu berechnen. Das spart das Nachschlagen im NA in den Minuten/Sekunden
       Tabellen.

     */
    void calculate() {


        System.out.println("===========================================================");
        System.out.println("Testvergleichsreport Applet, OpenOffice, Nautical Almanac");
        System.out.println("Himmelskoerper "+CBName);
        System.out.println("===========================================================");




    /*B25*/    double t=calculus.HMS2Real(ObservationTime)-calculus.HMS2Real(Fix);
               System.out.println("B25 t                    = "+Double.toString(t));
    /*B26*/    double LongHelper=((Speed/60)*Math.sin(Math.toRadians(Heading)))/Math.cos(Math.toRadians(calculus.DMS2Real(DRLat)));
               System.out.println("B26 LongHelper           = "+Double.toString(LongHelper));

    /*B27*/    double DRLong=this.FractionalDRLong+t*LongHelper;  // Redundant aber einfacher zu uebertragen
               System.out.println("B27 DRLong               = "+Double.toString(DRLong));
    /*B28*/    double DRLat=this.FractionalDRLat+(t*(Speed/60)*Math.cos(Math.toRadians(Heading)));    // Redundant aber einfacher zu uebertragen
               System.out.println("B28 DRLat                = "+Double.toString(DRLat));
    /*B13*/    double interPolationFactor = calculus.MS2Real(ObservationTime);
               System.out.println("B13 interpolation factor = "+Double.toString(interPolationFactor));
    /*B16*/    GHAAries=GHAArieshh24+interPolationFactor*(GHAAriesPlus1h-GHAArieshh24);  // B14+B13*(B15-B14)
               System.out.println("B16 GHAAries             = "+Double.toString(GHAAries));
    /*B17*/    //SHA aus NA
               System.out.println("B17 SHA                  = "+Double.toString(SHA));
    /*B18*/    GHA=(GHAAries+SHA)>360?(GHAAries+SHA)-360:(GHAAries+SHA);
               System.out.println("B18 GHA                  = "+Double.toString(GHA));
    /*B19*/    double LHA_NA=GHA+DRLong;
               System.out.println("B19 LHA_NA               = "+Double.toString(LHA_NA));
    /*B20*/    LHAAries=DRLong>0?GHAAries+DRLong:GHAAries-DRLong;                          // West oder Ost Laengengrad?
               System.out.println("B20 LHAAries             = "+Double.toString(LHAAries));
    /*B21*/    LHAHO2102D=Math.round(LHAAries>360?LHAAries-360:LHAAries);                  // Gerundet fuer den StarFinder
               System.out.println("B21 LHAHO2102D           = "+Double.toString(LHAHO2102D));
    /*B22*/    //double Declination=this.Declination;                                               // Aus dem NA
               System.out.println("B22 Declination          = "+Double.toString(Declination));
    /*B23*/    double S=Math.sin(Math.toRadians(Declination));
               System.out.println("B23 S                    = "+Double.toString(S));
    /*B24*/    double CC=Math.cos(Math.toRadians(Declination))*Math.cos(Math.toRadians(LHA_NA));
               System.out.println("B24 CC                   = "+Double.toString(CC));

    /*B38*/    double asinHC=S*Math.sin(Math.toRadians(DRLat))+CC*Math.cos(Math.toRadians(DRLat));
               System.out.println("B38 asinHC               = "+Double.toString(asinHC));
    /*B39*/     Hc=Math.toDegrees(Math.asin((asinHC)));
               System.out.println("B39 Hc                   = "+Double.toString(Hc));
    /*B31*/    double X=((S*Math.cos(Math.toRadians(DRLat)))-CC*Math.sin(Math.toRadians(DRLat)))/Math.cos(Math.toRadians(Hc));
               System.out.println("B31 X                    = "+Double.toString(X));
    /*B32*/    double AA=Math.toDegrees(Math.acos(X));
               System.out.println("B32 AA                   = "+Double.toString(AA));
    /*B33*/    double Z_Azimuth=(LHA_NA>180?AA:360-AA);
               System.out.println("B33 Z_Azimuth            = "+Double.toString(Z_Azimuth));
    /*B34*/     sinZ=Math.sin(Math.toRadians(Z_Azimuth));
               System.out.println("B34 sinZ                 = "+Double.toString(sinZ));
    /*B35*/     sinZ2=sinZ*sinZ;
               System.out.println("B35 sinZ^2               = "+Double.toString(sinZ2));
    /*B36*/     cosZ=Math.cos(Math.toRadians(AA));
               System.out.println("B36 cosZ                 = "+Double.toString(cosZ));
    /*B37*/     cosZ2=cosZ*cosZ;
               System.out.println("B37 cosZ^2               = "+Double.toString(cosZ2));

    /*B29*/     pIntercept=Ho-Hc;
               System.out.println("B11 Ho bzw. Hc           = "+Double.toString(Ho));
               System.out.println("B29 pIntercept           = "+Double.toString(pIntercept));
    /*B30*/    double pInNM=pIntercept*60;
               System.out.println("B30 pInNM                = "+Double.toString(pInNM));
       Z=Z_Azimuth;
       p=pIntercept;
    }




}

public class NADataAndCalc {
    // Three Stars to get a fix
    public NAData NAData[]=new NAData[3];
    static View view;
    static NauticalAlmanac Fragment;
    static DelayedMessage msg;


    public double L1;
    public double B1;


    void initNADATA()
    {
        for (int i=0; i<3;i++) {
            NAData[i] = new NAData();
        }

    }

    NADataAndCalc()  // Default constructor
    {
         initNADATA();
    }




    void setDefaultsNA(View view, NauticalAlmanac na)
    {
        this.view=view;
        msg=new DelayedMessage(view);
        msg.ShowSnackbar("Set NA defaults");


        // Valid data for 3 Celestial Bodys
        na.CB1.setChecked(false);
        na.CB2.setChecked(false);
        na.CB3.setChecked(true);

        NAData[0].Date=NAData[1].Date=NAData[2].Date="04.07.2022";  // Date of calculation DR Data



        NAData[0].Time=NAData[1].Time=NAData[2].Time="21:00:00";
        NAData[0].DRLong=NAData[1].DRLong=NAData[2].DRLong="-015°00'00.00\"";
        NAData[0].DRLat=NAData[1].DRLat=NAData[2].DRLat="032°00'00.00\"";
        NAData[0].FractionalDRLat=NAData[1].FractionalDRLat=NAData[2].FractionalDRLat=-15.00;
        NAData[0].FractionalDRLat=NAData[1].FractionalDRLat=NAData[2].FractionalDRLat=32.00;
        NAData[0].Heading=NAData[1].Heading=NAData[2].Heading=325.00;
        NAData[0].Speed=NAData[1].Speed=NAData[2].Speed=20.00;
        NAData[0].Fix=NAData[1].Fix=NAData[2].Fix="21:00:00";


        // Specific CB data
        NAData[0].CBName="REGULUS";
        NAData[1].CBName="ANTARES";
        NAData[2].CBName="KOCHAB";
        NAData[0].ObservationTime="20:39:23";
        NAData[1].ObservationTime="20:45:47";
        NAData[2].ObservationTime="21:10:34";
        NAData[0].ObservationDate=NAData[1].ObservationDate=NAData[2].ObservationDate="04.07.2022";
        NAData[0].Ho=27.0772;
        NAData[1].Ho=25.9361;
        NAData[2].Ho=47.5563;
        NAData[0].CBBearing=3.14;  //   PI bearing do not matter just for informational purpose
        NAData[1].CBBearing=6.28;  // 2 PI bearing do not matter just for informational purpose
        NAData[2].CBBearing=9.42;  // 3 PI bearing do not matter just for informational purpose
        NAData[0].GHAArieshh24=222.8067; // Compare data with NA!
        NAData[1].GHAArieshh24=222.8067;
        NAData[2].GHAArieshh24=237.8483;
        NAData[0].GHAAriesPlus1h=237.8483;
        NAData[1].GHAAriesPlus1h=237.8483;
        NAData[2].GHAAriesPlus1h=252.8900;
        NAData[0].SHA=207.6150;
        NAData[1].SHA=112.3017;
        NAData[2].SHA=137.3200;
        NAData[0].Declination=11.8600;
        NAData[1].Declination=-26.4817;
        NAData[2].Declination=74.0683;

        na.mdfDate.setText(NAData[0].Date);
        na.mdfTime.setText(NAData[0].Time);
        na.mdfDRLong.setText(NAData[0].DRLong);
        na.mdfDRLat.setText(NAData[0].DRLat);
        na.mdfHeading.setText(String.format(Locale.ROOT,"%.5f",NAData[0].Heading));
        na.mdfSpeed.setText(String.format(Locale.ROOT,"%.5f",NAData[0].Speed));


        for (int i=0; i<3; i++) {
            String postFix="_1";
            switch (i)
            {
                case 0:                 postFix="_1";
                         break;
                case 1:                 postFix="_2";
                         break;
                case 2:                 postFix="_3";
                    break;
            }

            na.mdfObservedDate.setText(NAData[i].ObservationDate);
            na.mdfObservedTime.setText(NAData[i].ObservationTime);
            na.mdfCBName.setText(NAData[i].CBName);
            na.mdfHo.setText(String.format(Locale.ROOT,"%.5f",NAData[i].Ho));
            //na.mdfCBBearing.setText(String.format("%.5f",NAData[i].CBBearing));
            na.mdfGHAAries.setText(calculus.Real2DMS(NAData[i].GHAArieshh24));
            na.mdfGHAAriesPlus1.setText(calculus.Real2DMS(NAData[i].GHAAriesPlus1h));
            na.mdfSHA.setText(calculus.Real2DMS(NAData[i].SHA));
            na.mdfDeclinationNA.setText(calculus.Real2DMS(NAData[i].Declination));
            na.mdfFixTime.setText(NAData[i].Fix);




            /* TodDo:
               Daten in die Maske übertragen.

             */


            // Abspeichern als wenn der RadioButton gedrückt wurden.
            na.SaveCBrelatedData(postFix,true); // false, data from array
            na.postFixLast=postFix;
            na.refreshCBrelatedData(postFix);
            na.mdfPosition.setText(calculus.Real2DMS(-15.0174).replace('-','E')+" "+calculus.Real2DMS(31.6195).replace('-','S'));   // Die berechnete Position, die es lt. NA fuer den Default ist.
            na.mdfStatus.setBackgroundColor(Color.WHITE);
            na.mdfStatus.setText("NA defaults. First iteration.");


        }

        na.CB1.setChecked(true);
        na.CB2.setChecked(false);
        na.CB3.setChecked(false);
        na.postFixLast="_1";
        na.refreshCBrelatedData("_1");
    }


    String Calculate(View view, NauticalAlmanac na, SharedPreferences sharedpreferences)
    {
      String Position="<unknown>";
      try {
          this.view = view;
          msg = new DelayedMessage(view);
          msg.ShowSnackbar("Calculating by 3 CB");
      }
      catch (Exception e)
      {
          System.out.println("NADataAndCalc Exception msg.ShowSnackbar "+e.getMessage());
      }
     /*
     double C31=45.00;
     System.out.println("===========================================================");
     System.out.println("Testvergleich RAD und SIN zwischen OpenOffice Calc und JAVA");
     System.out.println("C31 "+C31);
     System.out.println("rad(C31)     = "+Double.toString(Math.toRadians(C31)));
     System.out.println("sin(C31)     = "+Double.toString(Math.sin(C31)));
     System.out.println("sin(rad(C31))= "+Double.toString(Math.sin(Math.toRadians(C31))));
     System.out.println("rad(sin(C31))= "+Double.toString(Math.toRadians(Math.sin(C31))));
     System.out.println("===========================================================");
     */

      try {
          for (int i = 0; i < 3; i++) {
              msg.ShowSnackbar("Calculating CB#"+(i+1));
              NAData[i].readPrefs(i + 1, sharedpreferences);   // Daten aus den SharedPreference holen  / +1 weil die postfixes der Sterne von _1 bis _3 gehen.
              NAData[i].calculate();                                 // Anschliessend gleich Z Azimuth  berechnen
          }
      } catch(Exception e)
      {
          System.out.println("NADataAndCalc Exception for int i<3 calc each CB "+e.getMessage());
      }

        /*

          B29    NAData[x].pIntercept;
          B34    NAData[x].sinZ;
          B35    NAData[x].sinZ2;
          B36    NAData[x].cosZ;
          B37    NAData[x].cosZ2;
        */


      try {


          /*B40*/
          double A = NAData[0].cosZ2 + NAData[1].cosZ2 + NAData[2].cosZ2;           // B37+D37+F37 (Z der drei Sterne addieren)
          System.out.println("B40 A                    = " + Double.toString(A));
          /*B41*/
          double B = NAData[0].cosZ * NAData[0].sinZ + NAData[1].cosZ * NAData[1].sinZ + NAData[2].cosZ * NAData[2].sinZ;
          System.out.println("B41 B                    = " + Double.toString(B));
          /*B42*/
          double C = NAData[0].sinZ2 + NAData[1].sinZ2 + NAData[2].sinZ2;
          System.out.println("B42 C                    = " + Double.toString(C));
          /*B43*/
          double D = NAData[0].pIntercept * NAData[0].cosZ + NAData[1].pIntercept * NAData[1].cosZ + NAData[2].pIntercept * NAData[2].cosZ;
          System.out.println("B43 D                    = " + Double.toString(D));
          /*B44*/
          double E = NAData[0].pIntercept * NAData[0].sinZ + NAData[1].pIntercept * NAData[1].sinZ + NAData[2].pIntercept * NAData[2].sinZ;
          System.out.println("B44 E                    = " + Double.toString(E));
          /*B45*/
          double G = (A * C) - (B * B);
          System.out.println("B45 G                    = " + Double.toString(G));
          /*B46*/
          L1 = NAData[0].FractionalDRLong + (A * E - B * D) / (G * Math.cos(NAData[0].FractionalDRLat));   // L1= Laengengrad
          System.out.println("B46 L1                    = " + Double.toString(L1));
          /*B47*/
          B1 = NAData[0].FractionalDRLat + (C * D - B * E) / G;                                          // B1= Breitengrad
          System.out.println("B47 B1                    = " + Double.toString(B1));
          // ToDo: Berechne die Präzision und ob ein zweiter Schritt nötig ist bei dem die bis hier berechneten Daten die Eingangsdaten werden
          // precision=60 * sqrt ((L1 - Lf)^2 * cos^2(Bf) + (B1 -Bf)^2)
          /*B48*/
          double precision = 60 * Math.sqrt(Math.pow(L1 - NAData[0].FractionalDRLong, 2) * Math.pow(Math.cos(NAData[0].FractionalDRLat), 2) + Math.pow(B1 - NAData[0].FractionalDRLat, 2));
          System.out.println("B48 precisions           = " + Double.toString(precision));

      System.out.println("====================END OF REPORT==========================");
      System.out.println("===========================================================");

      if (precision<=20) // precision in SM
      {
          na.mdfStatus.setEnabled(true);
          na.mdfStatus.setBackgroundColor(Color.GREEN);  // Praezision gut
          na.mdfStatus.setText("Precision good");
          na.mdfStatus.setEnabled(false);
      }
      else
      {
          na.mdfStatus.setEnabled(true);
          na.mdfStatus.setBackgroundColor(Color.RED);    // Praezision nicht ausreichend
          na.mdfStatus.setText("Precision poor");
          na.mdfStatus.setEnabled(false);
          na.mdfDRLong.setText(calculus.Real2DMS(L1));
          na.mdfDRLat.setText(calculus.Real2DMS(B1));
          SharedPreferences.Editor editor = sharedpreferences.edit();
          //Editor editor = sharedpreferences.edit();
          //editor.putString("key", "value");

          editor.putString("DRLong",na.mdfDRLong.getText().toString());
          editor.putString("DRLat",na.mdfDRLat.getText().toString());
          editor.apply();
          //editor.commit();

      }
      } catch (Exception e)
      {
          System.out.println("NADataAndCalc Exception A,B,C,D,E,G, L1, B1 "+e.getMessage());
      }

      String retval="Error in position conversion";
      try {
           retval = calculus.Real2DMS(L1).replace('-', 'E') + " " + calculus.Real2DMS(B1).replace('-', 'S');  // ToDo: Hier noch E/W und N/S ergaenzen
      } catch (Exception e)
      {
          msg.ShowSnackbar("Error in position conversion");
          System.out.println("NADataAndCalc Exception retval "+e.getMessage());
      }

      return retval;
    }


    public double range_degrees(double d)
     /*
         Code, taken idea from Stellarium, File siderial_time.c
     */
    {
        d = d % 360.;
        if(d<0.) d += 360.;
        return d;
    }

    public double GHAAries(String Date, String Time)
    /*
           This function give the same results as Stellarium give. This code is NOT taken from Stelarium.
           It's my own stuff.

           There is a small difference related to Nautical Almanac but for example:

            Code to test this stuff:

            String date="21.03.2023";
            String time="12:00:00";
            System.out.println("GHA Aries for "+date+" "+time+" is "+Double.toString(GHAAries(date,time))+" "+Real2DMS((GHAAries(date,time) )));
            System.out.println("GHA Aries lt NA  358°44.0'");

            Result is:  GHA Aries for 21.03.2023 12:00:00 is 358.75028450926766 358°45'1.02"

            date="21.03.2023";
            time="12:32:12";
            System.out.println("GHA Aries for "+date+" "+time+" is "+Double.toString(GHAAries(date,time))+" "+Real2DMS((GHAAries(date,time) )));
            System.out.println("GHA Aries lt NA  006°48.3'");

            Result is: GHA Aries for 21.03.2023 12:32:12 is 6.822324679233134 006°49'20.37"

            However, since this difference is in all of the well know Algorithms from

            Meeus, Astr. Algorithms, Formula 11.1, 11.4 pg 83. (or 2nd ed. 1998, 12.1, 12.4 pg.87)
            N. Capitaine, P.T.Wallace, J. Chapront: Expressions for IAU 2000 precession quantities.
            A&A412, 567-586 (2003)
            DOI: 10.1051/0004-6361:20031539

            I'm not in the position to offer a better solution.

    */
    {
        int year,month,day;
        int hour, minute, seconds;
        double c1=280.46061837;
        double c2=360.98564736629;

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

        double dWhole=367*year-(int)(7*(year+(int)((month+9)/12))/4)+(int)(275*month/9)+day-730531.5;
        double dFrac=((double)hour+((double)minute/60.+(double)seconds/3600.))/24.;
        return range_degrees(c1+c2*(dWhole+dFrac));
    }




}
