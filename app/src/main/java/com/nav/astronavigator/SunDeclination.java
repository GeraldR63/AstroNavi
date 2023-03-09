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
    Button pbBack;
    TextView dfDate;
    TextView dfTime;
    TextView dfDeclination;

    TextView dfGHA;

    TextView dfSunBearing;
    TextView dfSunElevation;
    TextView dfTZ;

    private Button pbIncrCharset;
    private Button pbDecrCharset;


    double longitude;                // Current position from  AstroNavigation dialog.
    double latitude;

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


                            }
                        });
                    }
                }

            }
        };
        new Thread(runnable).start();
    }

    void setSun( String date, String time)
    {

        if (false==false) {
            try {

                dfSunElevation.setText(getElevation(longitude, latitude, date, time, Double.valueOf(dfTZ.getText().toString())));
                dfSunBearing.setText(getAzimuth(longitude, latitude, date, time, Double.valueOf(dfTZ.getText().toString())));
            } catch (Exception e)
            {

            }
        }
        else
        {
            // Sample data to check algorithm
            longitude=-104.7417;
            latitude=40.6028;
            String d="11.12.2019";
            String t="10:09:08";

            dfSunElevation.setText(getElevation(longitude, latitude, d, t,-7));
            dfSunBearing.setText(getAzimuth(longitude, latitude, d, t,-7));
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


        pbBack=view.findViewById(R.id.pbSunBack);
        dfDate=view.findViewById(R.id.dfSunDate);
        dfTime=view.findViewById(R.id.dfSunTime);
        dfTZ=view.findViewById(R.id.dfTZ);
        //ToDo: preset TZ by long/lat
        dfTZ.setText("-1");

        dfDeclination=view.findViewById(R.id.dfSunDeclination);
        dfDeclination.setEnabled(false);
        dfDeclination.setTextColor(Color.BLACK);
        dfGHA=view.findViewById(R.id.dfSunGHA);
        dfGHA.setEnabled(false);
        dfGHA.setTextColor(Color.BLACK);

        dfSunBearing=view.findViewById(R.id.dfSunBearing);
        dfSunBearing.setEnabled(false);
        dfSunBearing.setTextColor(Color.BLACK);

        dfSunElevation=view.findViewById(R.id.dfSunElevation);
        dfSunElevation.setEnabled(false);
        dfSunElevation.setTextColor(Color.BLACK);

        cbTimerOnOff.setChecked(true);
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
                Integer n=Integer.valueOf(sharedpreferences.getString("CharSize", "9"))+1;

                n=(n>=40?n=40:n);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("CharSize",n.toString());
                editor.apply();
                //editor.commit();
                setTextSize(n);
            }
        });

        pbDecrCharset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer n=Integer.valueOf(sharedpreferences.getString("CharSize", "9"))-1;

                n=(n<=2?n=2:n);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("CharSize",n.toString());
                editor.apply();
                //editor.commit();
                setTextSize(n);

            }
        });


        try {
            CelestialBodys cb=new CelestialBodys(null);
            dfDeclination.setText(cb.getDeclSun(dfDate.getText().toString(), dfTime.getText().toString()));
            dfGHA.setText(calculus.Real2DMS(cb.timeToAngle(cb.date2seconds("00.00.0000", dfTime.getText().toString()))));
            setSun(date,time);
        } catch (Exception e)
        {

        }



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
                CelestialBodys cb=new CelestialBodys(null);
                try {
                    //bAuto=false;
                    dfDeclination.setText(cb.getDeclSun(dfDate.getText().toString(), dfTime.getText().toString()));
                    dfGHA.setText(calculus.Real2DMS(cb.timeToAngle(cb.date2seconds("00.00.0000", dfTime.getText().toString()))));
                    setSun(dfDate.getText().toString(), dfTime.getText().toString());
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
                CelestialBodys cb=new CelestialBodys(null);
                try{
                    //bAuto=false;
                    dfDeclination.setText(cb.getDeclSun(dfDate.getText().toString(), dfTime.getText().toString()));
                    dfGHA.setText(calculus.Real2DMS(cb.timeToAngle(cb.date2seconds("00.00.0000", dfTime.getText().toString()))));
                    setSun(dfDate.getText().toString(), dfTime.getText().toString());
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
                    NavHostFragment.findNavController(SunDeclination.this).navigate(R.id.FirstFragment);
                }

            });

        setTextSize(Integer.valueOf(sharedpreferences.getString("CharSize", "9")));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        final TextView text;

        ViewHolder(FragmentSunDeclinationListDialogItemBinding binding) {
            super(binding.getRoot());
            text = binding.text;
        }

    }

    private class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final int mItemCount;

        ItemAdapter(int itemCount) {
            mItemCount = itemCount;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(FragmentSunDeclinationListDialogItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return mItemCount;
        }
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
        decl= Math.asin(Math.sin(obli)*Math.sin(ecli));

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


}