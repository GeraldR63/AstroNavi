package com.nav.astronavigator;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.nav.astronavigator.databinding.FragmentFirstBinding;
import com.nav.astronavigator.calculus;
import com.nav.astronavigator.DialogBox;

public class AstroNavigator extends Fragment {
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

    private FragmentFirstBinding binding;
    private EditText mdfSextant;
    private EditText mdfDeclination;
    private CheckBox mcbSouth;
    private EditText mdfGHA;
    private EditText mdfLatitude;

    private Button pbHo2Hc;
    private EditText mdfLocalHighNoon;
    private EditText mdfLongitude;
    private TextView textDecimalSextant;
    private TextView textDecimalLatitude;
    private TextView textDecimalLongitude;

    private TextView textDecimalDiffGMT;
    private TextView mdfDistance;
    private TextView textDecimalGHA;
    private Button pbIncrCharset;
    private Button pbDecrCharset;
    private Button pbButtonFirst;


    public static final String MyPREFERENCES = "AstroNavPrefs" ;
    SharedPreferences sharedpreferences;
    private CoordinatorLayout coordinatorLayout;



    protected void calculate(View view)
    {
        calculus calculus=new calculus(view);
        double GMT_ZERO=12*60*60;  // 12:00:00 in Greenwich
        double SM=1.852;
        double ZenithDistance= 90-calculus.DMS2Real(mdfSextant.getText().toString());
        double Declination= calculus.DMS2Real(mdfDeclination.getText().toString());
        double Latitude= 0;  // Verienfacht nur zum Testen.
        double LocalTimeHighNoon=calculus.HMS2Real(mdfLocalHighNoon.getText().toString())*60*60;
        double DegreePerSecond=360.00/24.00/60.00/60.00;  //Winkelgrad um den sich die Erde pro Sekunde dreht.
        double tLongitude;
        double tGHA;

        //String tDir;
        String tLong;
        //String tGHA="";

        /*
        coordinatorLayout=this.coordinatorLayout.findViewById( R.id.nav_host_fragment_content_main);
        //coordinatorLayout.findViewById( R.id.nav_host_fragment_content_main);

        Snackbar snackbar = Snackbar.make(view, "Calculating", Snackbar.LENGTH_LONG);
        snackbar.show();

         */

        try {
            tLongitude = Math.abs((GMT_ZERO - LocalTimeHighNoon) * DegreePerSecond);

            if (LocalTimeHighNoon < GMT_ZERO) {
                //tDir="E";
                tLong = "E " + String.valueOf(calculus.Real2DMS(tLongitude));
            } else {
                //tDir="W";
                tLong = "W " + String.valueOf(calculus.Real2DMS(tLongitude));
            }


            tGHA = Math.abs((GMT_ZERO + LocalTimeHighNoon) * DegreePerSecond);
            if (tGHA > 360) {
                tGHA -= 360;
            }

            mdfGHA.setText(calculus.Real2DMS(tGHA));
            textDecimalGHA.setText(String.valueOf(tGHA)+"°");

            textDecimalDiffGMT.setText(String.valueOf(GMT_ZERO - LocalTimeHighNoon)+"s");
            mdfLongitude.setText(tLong);
            textDecimalLongitude.setText(String.valueOf(tLongitude)+"°");


            textDecimalSextant.setText(String.format("%.5f", calculus.DMS2Real(mdfSextant.getText().toString()))+"°");

            if (mcbSouth.isChecked()) {
                Latitude = ZenithDistance - Declination;
                mdfDistance.setText(String.valueOf((Latitude + Declination) * 60 * SM));  //Object below Equator
            } else {
                if (Declination < ZenithDistance) {
                    Latitude = Declination + ZenithDistance;
                    mdfDistance.setText(String.valueOf((Latitude - Declination) * 60 * SM)); // Object above Equator but below observers location
                    //mdfDistance.setText("Dec<Zenith"); // Object above Equator but below observers location
                } else {
                    Latitude = Declination - ZenithDistance;
                    mdfDistance.setText(String.valueOf((Declination - Latitude) * 60 * SM)); // Object above Equator and above observers location
                    //mdfDistance.setText("Dec>Zenith"); // Object above Equator but below observers location
                }
            }

            textDecimalLatitude.setText(String.format("%.5f", Latitude)+"°");
            mdfLatitude.setText(calculus.Real2DMS(Latitude));
        }
        catch (Exception e)
        {

        }

    }




    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View fragmentFirstLayout = inflater.inflate(R.layout.fragment_first, container, false);
        // Get the count text view
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);



        return binding.getRoot();

    }

    public static float pxFromDp(float dp, Context mContext) {
        return dp * mContext.getResources().getDisplayMetrics().density;
    }



    public void setTextSize (int dp)
    {
        mdfSextant.setTextSize(pxFromDp(dp, getActivity()));
        mdfDeclination.setTextSize(pxFromDp(dp, getActivity()));
        mdfGHA.setTextSize(pxFromDp(dp, getActivity()));
        mdfLatitude.setTextSize(pxFromDp(dp, getActivity()));
        mdfLongitude.setTextSize(pxFromDp(dp, getActivity()));
        mdfLocalHighNoon.setTextSize(pxFromDp(dp, getActivity()));
        pbHo2Hc.setTextSize(pxFromDp(dp, getActivity()));
        mdfDistance.setTextSize(pxFromDp(dp, getActivity()));

        textDecimalSextant.setTextSize(pxFromDp(dp, getActivity()));
        textDecimalLatitude.setTextSize(pxFromDp(dp, getActivity()));
        textDecimalLongitude.setTextSize(pxFromDp(dp, getActivity()));
        textDecimalDiffGMT.setTextSize(pxFromDp(dp, getActivity()));
        textDecimalGHA.setTextSize(pxFromDp(dp, getActivity()));
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)

    {
        super.onViewCreated(view, savedInstanceState);
        mdfSextant=view.findViewById(R.id.dfSextant);

        mdfDeclination=view.findViewById(R.id.dfDeclination);

        mdfGHA=view.findViewById(R.id.dfGHA);

        mdfGHA.setEnabled(false);
        mcbSouth=view.findViewById(R.id.cbNS);
        //mcbSouth=view.findViewById(R.id.cbNS);
        mdfLatitude=view.findViewById(R.id.dfLatitude);

        mdfLatitude.setEnabled(false);
        mdfLongitude=view.findViewById(R.id.dfLongitude);

        mdfLongitude.setEnabled(false);
        mdfLocalHighNoon=view.findViewById(R.id.dfLocalHighNoon);

        pbButtonFirst=view.findViewById(R.id.button_first);
        pbHo2Hc=view.findViewById(R.id.pbHo2Hc);
        pbIncrCharset=view.findViewById(R.id.pbIncCharset);
        pbDecrCharset=view.findViewById(R.id.pbDecCharset);


        textDecimalSextant=view.findViewById(R.id.textDecimalSextant);
        textDecimalLatitude=view.findViewById(R.id.textDecimalLatitude);
        textDecimalLongitude=view.findViewById(R.id.textDecimalLongitude);
        textDecimalDiffGMT=view.findViewById(R.id.textDecimalDiffGMT);
        textDecimalGHA=view.findViewById(R.id.textDecimalGHA);

        mdfDistance=view.findViewById(R.id.dfDistance);


        //getView().setBackgroundColor(Color.RED);


        /*
        Snackbar snackbar = Snackbar.make(view.findViewById(androidx.constraintlayout.widget.R.id.content), "Welcome To Main Activity", Snackbar.LENGTH_LONG);
        snackbar.show();
        */
        mdfSextant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation
                calculate(view);

            }
        });


        mdfDeclination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation
                calculate(view);

            }
        });


        mdfLocalHighNoon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation
                calculate(view);

            }
        });

        pbHo2Hc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("WhoAmI","SIMPLE");

                editor.apply();
                editor.commit();

                NavHostFragment.findNavController(AstroNavigator.this)
                        .navigate(R.id.action_FirstFragment_to_Sextant);
                // Nach der Berechnung von Hc diese anzeigen.
                mdfSextant.setText( sharedpreferences.getString("sextant", "000°00'00.00\""));
                calculate(view);
                setTextSize(Integer.valueOf(sharedpreferences.getString("CharSize", "9")));
            }
        });


        calculate(view);

        mcbSouth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                calculate(v);
            }
        });

        pbButtonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AstroNavigator.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        pbIncrCharset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer n=Integer.valueOf(sharedpreferences.getString("CharSize", "9"))+1;

                n=(n>=40?n=40:n);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("CharSize",n.toString());
                editor.apply();
                editor.commit();
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
                editor.commit();
                setTextSize(n);

            }
        });




        //SharedPreferences prefs = this.getSharedPreferences("general_settings", Context.MODE_PRIVATE);
        mdfSextant.setText( sharedpreferences.getString("sextant", "021°00'00.00\""));
        mdfDeclination.setText( sharedpreferences.getString("declination", "022°00'00.00\""));
        mdfLocalHighNoon.setText( sharedpreferences.getString("LocalHighNoon", "12:00:00"));
        setTextSize(Integer.valueOf(sharedpreferences.getString("CharSize", "9")));

        if (sharedpreferences.getString("NS", "S").equals("S"))
        {  mcbSouth.setChecked(true);        }
        else
        { mcbSouth.setChecked(false);}
         //calculate(View);
    }

    @Override
    public void onDestroyView() {

        //System.out.println("ON DESTROY WINDOW");
        SharedPreferences.Editor editor = sharedpreferences.edit();
        //Editor editor = sharedpreferences.edit();
        editor.putString("key", "value");

        editor.putString("sextant",mdfSextant.getText().toString());
        editor.putString("declination",mdfDeclination.getText().toString());
        editor.putString("NS", (mcbSouth.isChecked() == true ? "S":"N"));

        editor.putString("LocalHighNoon",mdfLocalHighNoon.getText().toString());
        editor.putString("WhoAmI","SIMPLE");
        editor.apply();
        editor.commit();

        super.onDestroyView();
        binding = null;
    }

}