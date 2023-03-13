package com.nav.astronavigator;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nav.astronavigator.databinding.FragmentSunDeclinationListDialogItemBinding;
import com.nav.astronavigator.databinding.FragmentSunDeclinationListDialogBinding;

import java.util.Date;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     SunDeclination.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class SunDeclination extends DialogFragment {


    // TODO: Customize parameter argument names
    private static final String ARG_ITEM_COUNT = "item_count";
    private FragmentSunDeclinationListDialogBinding binding;
    public static final String MyPREFERENCES = "AstroNavPrefs" ;
    SharedPreferences sharedpreferences;


    CheckBox cbTimerOnOff;
    CheckBox cbPublishSunData;
    Button pbBack;
    TextView dfDate;
    TextView dfTime;
    TextView dfDeclination;

    TextView dfGHA;

    TextView dfSunBearing;
    TextView dfSunElevation;
    TextView dfTZ;

    TextView dfLatByPureMath;   //This is the theoretical Latitude.
    TextView dfLongByPureMath;  //This is the theoretical Longitude.

    private Button pbIncrCharset;
    private Button pbDecrCharset;


    double longitude;                // Current position from  AstroNavigation dialog.
    double latitude;

    //NADataAndCalc na=new NADataAndCalc();

    // TODO: Customize parameters
    public static SunDeclination newInstance(int itemCount) {
        final SunDeclination fragment = new SunDeclination();
        final Bundle args = new Bundle();
        args.putInt(ARG_ITEM_COUNT, itemCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentSunDeclinationListDialogBinding.inflate(inflater, container, false);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return binding.getRoot();

    }

    Boolean bAuto=true;
    private void startTimerThread() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {

                while (true) {
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (bAuto==true) {
                        handler.post(new Runnable() {
                            public void run() {
                                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy");
                                String date = sdf.format(new Date());
                                dfDate.setText(date);
                                sdf = new java.text.SimpleDateFormat("HH:mm:ss");
                                String time = sdf.format(new Date());
                                dfTime.setText(time);
                                setSun(date,time);
                                //calculate();
                            }
                        });
                    }
                }
            }
        };
        new Thread(runnable).start();
    }

    void calculate()
    {
        double ZenithDistance= 90-calculus.DMS2Real(dfSunElevation.getText().toString());
        double Declination= calculus.DMS2Real(dfDeclination.getText().toString());
        double Latitude= 0;  // Vereinfacht nur zum Testen.

        System.out.println("ZenithDistance="+ZenithDistance+" Declination="+Declination);
        if (Declination<0.0) {
            Latitude = ZenithDistance + (Declination);
            //mdfDistance.setText(String.valueOf((Latitude + Declination) * 60 * SM));  //Object below Equator
        } else {
            if (Declination < ZenithDistance) {
                Latitude = Declination + ZenithDistance;
            } else {
                Latitude = Declination - ZenithDistance;
            }
        }

        Latitude=(Latitude>90.0?Latitude-90:Latitude);

        dfLatByPureMath.setText(calculus.Real2DMS(Latitude));
    }

    void setSun( String date, String time)
    {

        String  tLong="";
        try {
            double LocalTimeHighNoon = (calculus.HMS2Real(dfTime.getText().toString()) * 60 * 60)+(-1*Double.valueOf(dfTZ.getText().toString())*60*60);
            double DegreePerSecond = 360. / 24.00 / 60.00 / 60.00;
            double GMT_ZERO=12.*60.*60.;  // 12:00:00 in Greenwich

            double tLongitude = Math.abs((GMT_ZERO - LocalTimeHighNoon) * DegreePerSecond);

            if (LocalTimeHighNoon < GMT_ZERO) {
                //tDir="E";
                tLong = "E " + (calculus.Real2DMS(tLongitude));
                tLongitude *= -1;
            } else {
                //tDir="W";
                tLong = "W " + (calculus.Real2DMS(tLongitude));
            }
        }

        catch (Exception e) {
            tLong="???°??'??.??\"";
        }


        if (true) {

            try{
                    /*
                           longitude here by -1 because the algorithm expect negative values for west longitudes!
                     */
                    dfSunElevation.setText(getElevation(longitude*-1, latitude, date, time, Double.valueOf(dfTZ.getText().toString())));
                    dfSunBearing.setText(getAzimuth(longitude*-1, latitude, date, time, Double.valueOf(dfTZ.getText().toString())));
                    dfDeclination.setText(getDeclination(longitude*-1, latitude, date, time, Double.valueOf(dfTZ.getText().toString())));
                    calculate();
                    dfLongByPureMath.setText(tLong);
            } catch (Exception e)
            {

            }
        }
        else
        {
            // Sample data to check algorithm. Just for Debugging!
            longitude=-104.7417;  // !!!!!!! REMEMBER THAT IN THIS ALGORITHM -longitude is WEST!!!!!!!
            latitude=40.6028;
            String d="11.12.2019";
            String t="10:09:08";

            dfSunElevation.setText(getElevation(longitude, latitude, d, t,-7));
            dfSunBearing.setText(getAzimuth(longitude, latitude, d, t,-7));
            dfDeclination.setText(getDeclination(longitude, latitude, d, t,-7));
            calculate();
            dfLongByPureMath.setText(tLong);
        }
    }

    public static float pxFromDp(float dp, Context mContext) {
        return dp * mContext.getResources().getDisplayMetrics().density;
    }

    public void setTextSize (int dp)
    {
        dfDeclination.setTextSize(pxFromDp(dp, getActivity()));
        dfDate.setTextSize(pxFromDp(dp, getActivity()));
        dfSunBearing.setTextSize(pxFromDp(dp, getActivity()));
        dfGHA.setTextSize(pxFromDp(dp, getActivity()));
        dfSunElevation.setTextSize(pxFromDp(dp, getActivity()));
        dfDate.setTextSize(pxFromDp(dp, getActivity()));
        dfTime.setTextSize(pxFromDp(dp, getActivity()));
        dfTZ.setTextSize(pxFromDp(dp, getActivity()));
        cbTimerOnOff.setTextSize(pxFromDp(dp, getActivity()));
        cbPublishSunData.setTextSize(pxFromDp(dp, getActivity()));
        dfLongByPureMath.setTextSize(pxFromDp(dp, getActivity()));
        dfLatByPureMath.setTextSize(pxFromDp(dp, getActivity()));
    }

    void computeAndSetData()
    {
        CelestialBodys cb=new CelestialBodys(null);
        //dfDeclination.setText(cb.getDeclSun(dfDate.getText().toString(), dfTime.getText().toString(),dfTZ.getText().toString()));
        //dfDeclination.setText(getDeclination(dfDate.getText().toString(), dfTime.getText().toString(),dfTZ.getText().toString()));
        //dfGHA.setText(calculus.Real2DMS(cb.timeToAngle(cb.date2seconds("00.00.0000", dfTime.getText().toString(),dfTZ.getText().toString()))));
        double d=cb.date2seconds(dfDate.getText().toString(), dfTime.getText().toString(),dfTZ.getText().toString());
        double d2=cb.date2seconds(dfDate.getText().toString(), "00:00:00",dfTZ.getText().toString());
        dfGHA.setText(calculus.Real2DMS((cb.timeToAngle(d-d2))));
        setSun(dfDate.getText().toString(), dfTime.getText().toString());

    }
     String sCharSetSizeName="SunCharSize";
    void TextSize2SharedPref(Integer n)
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(sCharSetSizeName,n.toString());
        editor.apply();
        setTextSize(n);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ItemAdapter(getArguments().getInt(ARG_ITEM_COUNT)));
        */

        SharedPreferences.Editor editor = sharedpreferences.edit();
        //editor.putString("SimpleLatitude",""+Latitude);                  //Used in SunDeclination as position to get  Bearing and Elevation of sun
        //editor.putString("SimpleLongitude",""+tLongitude);
        // Default is Greenwich Englang 0.001475,51.477811

        try {
            longitude = Double.valueOf(sharedpreferences.getString("SimpleLongitude", "0.001475"));
            latitude = Double.valueOf(sharedpreferences.getString("SimpleLatitude", "51.477811"));
        } catch (Exception e)
        {
            longitude=0.001475;
            latitude=51.477811;
        }

        cbTimerOnOff=view.findViewById(R.id.cbTimerOnOff);
        cbPublishSunData=view.findViewById(R.id.cbPublishSunData);
        cbPublishSunData.setChecked(false);   // This is always false. User have to be forced to decide this if he want this again, again and again


        pbBack=view.findViewById(R.id.pbSunBack);
        dfDate=view.findViewById(R.id.dfSunDate);
        dfTime=view.findViewById(R.id.dfSunTime);
        dfTZ=view.findViewById(R.id.dfTZ);
        //ToDo: preset TZ by long/lat
        dfTZ.setText("1");

        dfDeclination=view.findViewById(R.id.dfSunDeclination);
        dfDeclination.setEnabled(false);
        dfDeclination.setTextColor(Color.BLACK);
        dfDeclination.setBackgroundColor(Color.rgb(128,255, 128));

        dfGHA=view.findViewById(R.id.dfSunGHA);
        dfGHA.setEnabled(false);
        dfGHA.setTextColor(Color.BLACK);
        dfGHA.setBackgroundColor(Color.rgb(255,128, 128));

        dfSunBearing=view.findViewById(R.id.dfSunBearing);
        dfSunBearing.setEnabled(false);
        dfSunBearing.setTextColor(Color.BLACK);
        dfSunBearing.setBackgroundColor(Color.rgb(128,255, 128));

        dfSunElevation=view.findViewById(R.id.dfSunElevation);
        dfSunElevation.setEnabled(false);
        dfSunElevation.setTextColor(Color.BLACK);
        dfSunElevation.setBackgroundColor(Color.rgb(128,255, 128));

        /*
           ToDO: This Latitude depends on the Elevation taken at the position in the Simple Navigation Dialog.
                 That's why this Latitude is probably "bullshit".
                 Hheck the math behind the scenes.

                 Assume you are  at long/lat and see the sun at bearing and elevation and than you calculate
                 the Latitude of the position where the sun reaches at this point in time the highest point.
                 Well...that's what I need here. Maybe the related calculation is true. I've not checked this.
         */
        dfLatByPureMath=view.findViewById(R.id.dfSunTheoreticalLat);
        dfLatByPureMath.setEnabled(false);
        dfLatByPureMath.setTextColor(Color.BLACK);
        dfLatByPureMath.setBackgroundColor(Color.rgb(255,128, 128));

        dfLongByPureMath=view.findViewById(R.id.dfSunLongitude);
        dfLongByPureMath.setEnabled(false);
        dfLongByPureMath.setTextColor(Color.BLACK);
        dfLongByPureMath.setBackgroundColor(Color.rgb(255,165, 0));



        if (sharedpreferences.getString("SunTimer", "0").equals("1"))
        {  cbTimerOnOff.setChecked(true);      bAuto=true;  }
        else
        { cbTimerOnOff.setChecked(false);bAuto=false;}

        startTimerThread();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date());
        sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());

        dfDate.setText(date);
        dfTime.setText(time);
        pbIncrCharset=view.findViewById(R.id.pbSunIncCharset);
        pbDecrCharset=view.findViewById(R.id.pbSunDecCharset);
        pbIncrCharset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer n=Integer.valueOf(sharedpreferences.getString(sCharSetSizeName, "9"))+1;

                n=(n>=40?n=40:n);
                TextSize2SharedPref( n);
            }
        });

        pbDecrCharset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer n=Integer.valueOf(sharedpreferences.getString(sCharSetSizeName, "9"))-1;

                n=(n<=2?n=2:n);
                TextSize2SharedPref(n);
            }
        });


        try {
            computeAndSetData();
        } catch (Exception e)
        {

        }

        dfTZ.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation
                try{
                computeAndSetData();
                } catch (Exception e)
                {

                }

            }
        });


        dfDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation

                try {
                    computeAndSetData();
                } catch (Exception e)
                {

                }

            }
        });

        dfTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation

                try{
                    //bAuto=false;
                    computeAndSetData();
            } catch (Exception e)
            {

            }

            }
        });

        cbTimerOnOff.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (bAuto) {
                                                    bAuto ^= bAuto;
                                                }
                                                else
                                                {
                                                    bAuto=true;
                                                }
                                            }
                                        });


            pbBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (cbPublishSunData.isChecked()) {  //If user want to push the data to "Simple Navigation (SUN)" than it happens.
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        //Editor editor = sharedpreferences.edit();

                        editor.putString("declination", dfDeclination.getText().toString());   // Data pushed to Simple Navigation Dialog
                        editor.putString("LocalHighNoon", dfTime.getText().toString());        // Data pushed to Simple Navigation Dialog

                        editor.apply();
                    }

                    NavHostFragment.findNavController(SunDeclination.this).navigate(R.id.FirstFragment);
                }

            });

        dfDate.setText( sharedpreferences.getString("SunDate", "01.01.2000"));
        dfTime.setText( sharedpreferences.getString("SunTime", "00:00:00"));
        dfTZ.setText( sharedpreferences.getString("SunTZ", "1"));
        setTextSize(Integer.valueOf(sharedpreferences.getString(sCharSetSizeName, "9")));



    }



    class cTime
    {
        public int iYear;
        public  int iMonth;
        public  int iDay;
        public  double dHours;
        public  double dMinutes;
        public  double dSeconds;
        public  double dTimeZone;
    };

    class cLocation
    {
        public double dLongitude;
        public double dLatitude;
    };

    class cSunCoordinates
    {
        public double dZenithAngle;
        public double dAzimuth;
        public double dDeclination;
        public double dHourAngle;
        public double dElevation;
        //public double dAzimuth;
        public double dRefraction;

        //public double dHourAngle;

    };

    public  cSunCoordinates sunPos(cTime udtTime,cLocation udtLocation, cSunCoordinates udtSunCoordinates) {

        double pi, tau,rpd;
        double rlat,rlon,utim;
        double dy1,dy2,dy3,dy4,dnum;
        double slon, sano,ecli,obli;
        double rasc,decl,stim,hang;
        double tmpx,tmpy,targ;

        // Set conversion factors
        pi=4*Math.atan(1);
        tau=pi+pi;
        rpd=pi/180;

        //Latitude and Longitude to radians

        rlat=udtLocation.dLatitude * rpd;
        rlon=udtLocation.dLongitude * rpd;

        //Decimal hour of the day at Greenwich

        utim=udtTime.dHours - udtTime.dTimeZone + udtTime.dMinutes/60+udtTime.dSeconds /3600;

        // Das from J2000 good for 1901 to 2099

        dy1=Math.floor((udtTime.iMonth+9)/12);
        dy2=Math.floor(7*(udtTime.iYear +dy1)/4);
        dy3=Math.floor(275*udtTime.iMonth/9);
        dy4=367 * udtTime.iYear - dy2 +dy3;
        dnum=dy4+udtTime.iDay - 730531.5 + utim/24;

        // Mean longitude of the sun

        slon=rnge(dnum * 0.01720279239 + 4.894967873, 0, tau);

        //System.out.println("slon="+slon);

        // Mean anomaly of the Sun

        sano=rnge(dnum * 0.01720197034 + 6.240040768, 0 ,tau);
        //System.out.println("sano="+sano);

        // Ecliptic longitude of the Sun
        ecli = slon + 0.03342305518 * Math.sin(sano)+0.0003490658504 * Math.sin(2 * sano);
        //System.out.println("ecli="+ecli);

        // Obliquity of the ecliptic
        obli= 0.4090877234 - 0.000000006981317008 * dnum;
        //System.out.println("obli="+obli);

        //Right ascension f the sun
        tmpx= Math.cos(obli) * Math.sin(ecli);
        tmpy= Math.cos(ecli);
        rasc= Math.atan2(tmpx, tmpy);
        //System.out.println("rasc="+rasc);

        //Declination of the sun
        decl= Math.asin(Math.sin(obli)*Math.sin(ecli));        //RAD
        udtSunCoordinates.dDeclination=Math.toDegrees(decl);

        //System.out.println("decl="+Real2DMS(decl));


        //Local sideral tim
        stim=rnge(4.894961213+(6.300388099*dnum)+rlon,0,tau);
        //System.out.println("stim="+stim);

        //Hour angle of sun
        hang=rnge(stim-rasc,0,tau);
        //System.out.println("hang="+hang);


        //Local elevation of the sun
        udtSunCoordinates.dElevation= Math.asin(Math.sin(decl) * Math.sin(rlat)+Math.cos(decl)*Math.cos(rlat) * Math.cos(hang));

        //Local azimuth of sun
        tmpx= -Math.cos(decl) * Math.cos(rlat)*Math.sin(hang);
        tmpy= Math.sin(decl) - Math.sin(rlat)*Math.sin(udtSunCoordinates.dElevation);
        udtSunCoordinates.dAzimuth= Math.atan2(tmpx, tmpy);

        //Convert azimuth and elevation to degrees
        udtSunCoordinates.dAzimuth=rnge(udtSunCoordinates.dAzimuth/rpd,0,360);
        udtSunCoordinates.dElevation=rnge(udtSunCoordinates.dElevation/rpd,-180,180);

        //Refraction correction
        targ =   (udtSunCoordinates.dElevation + (10.3/(  udtSunCoordinates.dElevation+5.11)))*rpd;
        udtSunCoordinates.dRefraction=(1.02/Math.tan(targ))/60;

        // Adjust elevation for refraction
        udtSunCoordinates.dElevation+=udtSunCoordinates.dRefraction;
        return udtSunCoordinates;
    }

    public  double rnge (double x, double rMin, double rMax) {
        double shiftedX, delta;

        shiftedX=x-rMin;
        delta=rMax-rMin;

        return (((shiftedX % delta) +delta)%delta)+rMin;
    }

    public   String getAzimuth(Double longitude, Double latitude, String iDate, String iTime, double tz)
    {

        cTime udtTime=new cTime();

        udtTime=DateTime2cTime (iDate, iTime);
        cSunCoordinates udtSunCoordinates=new cSunCoordinates();
        cLocation udtLocation=new cLocation();
        udtLocation.dLongitude=longitude;
        udtLocation.dLatitude=latitude;


        udtTime.dTimeZone=tz;

        udtSunCoordinates= sunPos( udtTime, udtLocation,  udtSunCoordinates);

        return calculus.Real2DMS(udtSunCoordinates.dAzimuth);
    }

    public   String getElevation(Double longitude, Double latitude, String iDate, String iTime, double tz)
    {

        cTime udtTime=new cTime();

        udtTime=DateTime2cTime (iDate, iTime);
        cSunCoordinates udtSunCoordinates=new cSunCoordinates();
        cLocation udtLocation=new cLocation();
        udtLocation.dLongitude=longitude;
        udtLocation.dLatitude=latitude;


        udtTime.dTimeZone=tz;

        udtSunCoordinates= sunPos( udtTime, udtLocation,  udtSunCoordinates);

        return calculus.Real2DMS(udtSunCoordinates.dElevation);
    }

    public   String getDeclination(Double longitude, Double latitude, String iDate, String iTime, double tz)
    {

        cTime udtTime=new cTime();

        udtTime=DateTime2cTime (iDate, iTime);
        cSunCoordinates udtSunCoordinates=new cSunCoordinates();
        cLocation udtLocation=new cLocation();
        udtLocation.dLongitude=longitude;
        udtLocation.dLatitude=latitude;


        udtTime.dTimeZone=tz;

        udtSunCoordinates= sunPos( udtTime, udtLocation,  udtSunCoordinates);

        return calculus.Real2DMS(udtSunCoordinates.dDeclination);
    }


    public  cTime  DateTime2cTime (String Date, String Time)
    {
        cTime cTime=new cTime();
        int position=Time.indexOf(":");
        cTime.dHours  =  Integer.valueOf(Time.substring(0,position));
        cTime.dMinutes = Integer.valueOf(Time.substring(position + 1, position=Time.indexOf(":", position+1)));
        cTime.dSeconds= Integer.valueOf(Time.substring(position+1, Time.length()));

        /* Parser dd.mm.yyyy */
        position=Date.indexOf(".");
        cTime.iDay  =  Integer.valueOf(Date.substring(0,position));
        cTime.iMonth = Integer.valueOf(Date.substring(position + 1, position=Date.indexOf(".", position+1)));
        cTime.iYear= Integer.valueOf(Date.substring(position+1, Date.length()));

        return cTime;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        //Editor editor = sharedpreferences.edit();
        editor.putString("key", "value");

        editor.putString("SunDate",dfDate.getText().toString());
        editor.putString("SunTime",dfTime.getText().toString());
        editor.putString("SunTimer", (cbTimerOnOff.isChecked() == true ? "1":"0"));

        //editor.putString("declination",dfDeclination.getText().toString());   // Data pushed to Simple Navigation Dialog
        //editor.putString("LocalHighNoon",dfTime.getText().toString());        // Data pushed to Simple Navigation Dialog

        editor.putString("SunTZ",dfTZ.getText().toString());
        editor.apply();

        binding = null;
    }

}