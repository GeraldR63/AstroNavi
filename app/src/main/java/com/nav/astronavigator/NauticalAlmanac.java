package com.nav.astronavigator;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.nav.astronavigator.databinding.FragmentSecondBinding;
import com.nav.astronavigator.CelestialBodys;
import com.nav.astronavigator.DelayedMessage;
import com.nav.astronavigator.NADataAndCalc;

public class NauticalAlmanac extends Fragment {
    com.nav.astronavigator.NADataAndCalc NADataAndCalc=new NADataAndCalc();
    com.nav.astronavigator.CelestialBodys CelestialBodys=new CelestialBodys();


    private FragmentSecondBinding binding;

    TextView mdfDate;
    TextView mdfTime;

     TextView mdfDRLong;
     TextView mdfDRLat;

     TextView mdfHeading;
     TextView mdfSpeed;


     TextView mdfCBName;


     RadioButton CB1;
     RadioButton CB2;
     RadioButton CB3;

     Button      pbNextCB;
     Button      pbPrevCB;
     Button      pbCalcNA;
     CheckBox    checkBoxCalcMode;
     Button      pbDefaultsNA;
     Button      pbSextant;

     TextView mdfFixTime;
     TextView mdfObservedTime;
     TextView mdfObservedDate;

     TextView mdfHo;
     //TextView mdfCBBearing;
     TextView TextView21;
     TextView TextView22;
     TextView TextView23;
     TextView TextView24;

     TextView mdfGHAAries;
     TextView mdfGHAAriesPlus1;
     TextView mdfSHA;
     TextView mdfDeclinationNA;
     TextView mdfPosition;
     TextView mdfStatus;

    public static final String MyPREFERENCES = "AstroNavPrefs" ;
    SharedPreferences sharedpreferences;
    private CoordinatorLayout coordinatorLayout;

    String postFixLast="_1";
    int activeStar=1;          //Numerisches Synonym fuer postFixLast

    public int CBcounter=0;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return binding.getRoot();

    }

    public void refreshCBrelatedData(String postFix)
    {
        mdfCBName.setText(sharedpreferences.getString("CBName"+postFix, "Regulus"));
        mdfFixTime.setText( sharedpreferences.getString("FixTime", "11:55:00"));
        mdfObservedTime.setText( sharedpreferences.getString("ObservedTime"+postFix, "11:55:00"));
        mdfObservedDate.setText( sharedpreferences.getString("ObservedDate"+postFix, "01.01.2023"));
        mdfHo.setText( sharedpreferences.getString("Ho"+postFix, "22.5°"));
        //mdfCBBearing.setText( sharedpreferences.getString("Bearing"+postFix, "-21.5°"));
        mdfGHAAries.setText( sharedpreferences.getString("GHAAries"+postFix, "359°59'59.99\""));
        mdfGHAAriesPlus1.setText( sharedpreferences.getString("GHAAriesPlus1"+postFix, "359°59'59.99\""));
        mdfSHA.setText( sharedpreferences.getString("SHA"+postFix, "359°59'59.99\""));
        mdfDeclinationNA.setText( sharedpreferences.getString("DeclinationNA"+postFix, "359°59'59.99\""));
        mdfPosition.setText( sharedpreferences.getString("LastPosition", "359°59'59.99E 359°59'59.99N\""));
    }

    void manageVisibilityNA(int mode)
    {

        mdfGHAAries.setVisibility(mode);
        mdfGHAAriesPlus1.setVisibility(mode);
        mdfDeclinationNA.setVisibility(mode);
        mdfSHA.setVisibility(mode);
        TextView21.setVisibility(mode);
        TextView22.setVisibility(mode);
        TextView23.setVisibility(mode);
        TextView24.setVisibility(mode);

        if (mode==View.VISIBLE)
        {
           mdfStatus.setText("Use Nautical Almanac");
        }
        else
        {
            mdfStatus.setText("Calculate GHA, SHA and Declination");
        }

    }
    public static float pxFromDp(float dp, Context mContext) {
        return dp * mContext.getResources().getDisplayMetrics().density;
    }

    int dp=8;

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mdfDate=getView().findViewById(R.id.dfDate);
        mdfDate.setTextSize(pxFromDp(dp, getActivity()));
        mdfTime=getView().findViewById(R.id.dfTime);
        mdfTime.setTextSize(pxFromDp(dp, getActivity()));

        mdfDRLat=getView().findViewById(R.id.dfDRLat);
        mdfDRLat.setTextSize(pxFromDp(dp, getActivity()));
        mdfDRLong=getView().findViewById(R.id.dfDRLong);
        mdfDRLong.setTextSize(pxFromDp(dp, getActivity()));

        mdfHeading=getView().findViewById(R.id.dfHeading);
        mdfHeading.setTextSize(pxFromDp(dp, getActivity()));
        mdfSpeed=getView().findViewById(R.id.dfSpeed);
        mdfSpeed.setTextSize(pxFromDp(dp, getActivity()));

        pbCalcNA=getView().findViewById(R.id.pbCalcNA);
        pbCalcNA.setTextSize(pxFromDp(dp, getActivity()));
        checkBoxCalcMode=getView().findViewById(R.id.checkBoxCalcMode); //Calculation NA oder P.Lutus
        checkBoxCalcMode.setTextSize(pxFromDp(dp, getActivity()));
        checkBoxCalcMode.setChecked(true);                              //Default NA usage
        pbDefaultsNA=getView().findViewById(R.id.pbDefaultsNA);
        pbDefaultsNA.setTextSize(pxFromDp(dp, getActivity()));
        pbSextant=getView().findViewById(R.id.pbSextant);
        pbSextant.setTextSize(pxFromDp(dp, getActivity()));

        pbNextCB=getView().findViewById(R.id.pbNext);
        pbNextCB.setTextSize(pxFromDp(dp, getActivity()));
        pbPrevCB=getView().findViewById(R.id.pbPrevCB);
        pbPrevCB.setTextSize(pxFromDp(dp, getActivity()));

        CB1=getView().findViewById(R.id.cb1);
        CB1.setTextSize(pxFromDp(dp, getActivity()));
        CB2=getView().findViewById(R.id.cb2);
        CB2.setTextSize(pxFromDp(dp, getActivity()));
        CB3=getView().findViewById(R.id.cb3);
        CB3.setTextSize(pxFromDp(dp, getActivity()));

        mdfCBName=getView().findViewById(R.id.dfCBName);
        mdfCBName.setTextSize(pxFromDp(dp, getActivity()));
        mdfCBName.setEnabled(false);
        mdfCBName.setTextColor(Color.BLACK);

        CB1.setChecked(true);

        mdfFixTime=getView().findViewById(R.id.dfFixTime);
        mdfFixTime.setTextSize(pxFromDp(dp, getActivity()));
        mdfObservedTime=getView().findViewById(R.id.dfObservedTime);
        mdfObservedTime.setTextSize(pxFromDp(dp, getActivity()));
        mdfObservedDate=getView().findViewById(R.id.dfObservedDate);
        mdfObservedDate.setTextSize(pxFromDp(dp, getActivity()));

        mdfHo=getView().findViewById(R.id.dfHo);
        mdfHo.setTextSize(pxFromDp(dp, getActivity()));
        // mdfCBBearing=getView().findViewById(R.id.dfCBbearing);

        TextView21=getView().findViewById(R.id.textView21);
        TextView21.setTextSize(pxFromDp(dp, getActivity()));
        TextView22=getView().findViewById(R.id.textView22);
        TextView22.setTextSize(pxFromDp(dp, getActivity()));
        TextView23=getView().findViewById(R.id.textView23);
        TextView23.setTextSize(pxFromDp(dp, getActivity()));
        TextView24=getView().findViewById(R.id.textView24);
        TextView24.setTextSize(pxFromDp(dp, getActivity()));

        mdfGHAAries=getView().findViewById(R.id.dfGHAAries);
        mdfGHAAries.setTextSize(pxFromDp(dp, getActivity()));
        mdfGHAAriesPlus1=getView().findViewById(R.id.dfGHAAriesPlus1);
        mdfGHAAriesPlus1.setTextSize(pxFromDp(dp, getActivity()));
        mdfSHA=getView().findViewById(R.id.dfSHA);
        mdfSHA.setTextSize(pxFromDp(dp, getActivity()));
        mdfDeclinationNA=getView().findViewById(R.id.dfDeclinationNA);
        mdfDeclinationNA.setTextSize(pxFromDp(dp, getActivity()));



        mdfPosition=getView().findViewById(R.id.dfPosition);
        mdfPosition.setTextSize(pxFromDp(dp, getActivity()));
        mdfPosition.setEnabled(false);
        mdfPosition.setTextColor(Color.BLACK);
        mdfStatus=getView().findViewById(R.id.dfStatus);
        mdfStatus.setTextSize(pxFromDp(dp, getActivity()));
        mdfStatus.setEnabled(false);
        mdfStatus.setTextColor(Color.BLACK);


        pbDefaultsNA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NADataAndCalc!= null) {
                    NADataAndCalc.setDefaultsNA(view, NauticalAlmanac.this);
                }
            }
        });


        //String postFix;
        CB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveCBrelatedData(postFixLast,false);
                postFixLast="_1";
                activeStar=1;
                refreshCBrelatedData(postFixLast);
                CB1.setChecked(true);
                CB2.setChecked(false);
                CB3.setChecked(false);
                mdfStatus.setBackgroundColor(Color.WHITE);
                mdfStatus.setText("Selected CB#1");
            }
        });
        CB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveCBrelatedData(postFixLast,false);
                postFixLast="_2";
                activeStar=2;
                refreshCBrelatedData(postFixLast);
                CB1.setChecked(false);
                CB2.setChecked(true);
                CB3.setChecked(false);
                mdfStatus.setBackgroundColor(Color.WHITE);
                mdfStatus.setText("Selected CB#2");

            }
        });
        CB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveCBrelatedData(postFixLast,false);
                postFixLast="_3";
                activeStar=3;
                refreshCBrelatedData(postFixLast);
                CB1.setChecked(false);
                CB2.setChecked(false);
                CB3.setChecked(true);
                mdfStatus.setBackgroundColor(Color.WHITE);
                mdfStatus.setText("Selected CB#3");
            }
        });



        pbNextCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CBcounter<65) {CBcounter++;} else {CBcounter=0;}
                mdfCBName.setText(CelestialBodys.startable[CBcounter].name.toUpperCase());
                CelestialBodys.selectedStar[activeStar-1]=CBcounter;
                mdfStatus.setBackgroundColor(Color.WHITE);
                mdfStatus.setText("Searched for Celestial Body");
            }
        });

        pbPrevCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CBcounter>0) {CBcounter--;} else {CBcounter=65;}
                mdfCBName.setText(CelestialBodys.startable[CBcounter].name.toUpperCase());
                CelestialBodys.selectedStar[activeStar-1]=CBcounter;
                mdfStatus.setBackgroundColor(Color.WHITE);
                mdfStatus.setText("Searched for Celestial Body");
            }
        });

        pbCalcNA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DelayedMessage msg=new DelayedMessage(view);

                // Maske abspeichern, damit Aenderungen bei der Berechnung beruecksichtigt werden.
                SaveCBrelatedData(postFixLast,false);

                if (checkBoxCalcMode.isChecked())
                {
                    try {
                        msg.ShowSnackbar("Calculation using NA");
                        mdfPosition.setText(NADataAndCalc.Calculate(view, NauticalAlmanac.this, sharedpreferences));
                    }
                    catch (Exception e)
                    {
                        msg.ShowSnackbar("Input error. Check DMS format!");
                    }

                }
                else
                {
                    msg.ShowSnackbar("Calculation by ideas of P.Lutus");
                }


            }
        });

        checkBoxCalcMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DelayedMessage msg=new DelayedMessage(view);

                if (checkBoxCalcMode.isChecked())
                {
                    manageVisibilityNA(View.VISIBLE);
                    msg.ShowSnackbar("Mode: NA");
                }
                else
                {
                    manageVisibilityNA(View.INVISIBLE);
                    msg.ShowSnackbar("Mode: P.Lutus");
                }


            }
        });





        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(NauticalAlmanac.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        binding.pbSextant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Die richtigen Parameter an das Unterfenster zuverlässig übergeben.
                if (CB1.isChecked())
                {
                    postFixLast="_1";
                    activeStar=1;
                }
                if (CB2.isChecked())
                {
                    postFixLast="_2";
                    activeStar=2;
                }
                if (CB3.isChecked())
                {
                    postFixLast="_3";
                    activeStar=3;
                }


                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("ActiveCB","_"+activeStar);
                editor.putString("WhoAmI","COMPLEX");
                editor.apply();
                editor.commit();

                NavHostFragment.findNavController(NauticalAlmanac.this)
                        .navigate(R.id.action_SecondFragment_to_Sextant);

                refreshCBrelatedData(postFixLast);

            }
        });


        mdfDate.setText( sharedpreferences.getString("date", "01.01.2023"));
        mdfTime.setText( sharedpreferences.getString("time", "11:55:00"));
        mdfDRLong.setText( sharedpreferences.getString("DRLong", "000°00'00.00\""));
        mdfDRLat.setText( sharedpreferences.getString("DRLat", "000°00'00.00\""));
        mdfHeading.setText( sharedpreferences.getString("DRHeading", "172°"));
        mdfSpeed.setText( sharedpreferences.getString("DRSpeed", "18.5kn"));
        mdfPosition.setText( sharedpreferences.getString("LastPosition", "000°00'00.0\" 000°00'00.0\""));


        if (sharedpreferences.getString("CB1active", "false").equals("true"))
        {
            CB1.setChecked(true);
            CB2.setChecked(false);
            CB3.setChecked(false);
            postFixLast="_1";
            mdfStatus.setBackgroundColor(Color.WHITE);
            mdfStatus.setText("Selected CB#1");

        }
        else if (sharedpreferences.getString("CB2active", "false").equals("true"))
        {
            CB1.setChecked(false);
            CB2.setChecked(true);
            CB3.setChecked(false);
            postFixLast="_2";
            mdfStatus.setBackgroundColor(Color.WHITE);
            mdfStatus.setText("Selected CB#2");

        }
        else
        {
            CB1.setChecked(false);
            CB2.setChecked(false);
            CB3.setChecked(true);
            postFixLast="_3";
            mdfStatus.setBackgroundColor(Color.WHITE);
            mdfStatus.setText("Selected CB#3");
        }

        refreshCBrelatedData(postFixLast);

    }

    public void SaveCBrelatedData(String postFix, Boolean init)
    {
        //String postFix;
        SharedPreferences.Editor editor = sharedpreferences.edit();
        //Editor editor = sharedpreferences.edit();
        //editor.putString("key", "value");

        editor.putString("date",mdfDate.getText().toString());
        editor.putString("time",mdfTime.getText().toString());
        editor.putString("DRLong",mdfDRLong.getText().toString());
        editor.putString("DRLat",mdfDRLat.getText().toString());
        editor.putString("DRHeading",mdfHeading.getText().toString());
        editor.putString("DRSpeed",mdfSpeed.getText().toString());
        editor.putString("LastPosition",mdfPosition.getText().toString());


        if (init==false) {
            editor.putString("CB1active", (postFixLast == "_1" ? "true" : "false"));
            editor.putString("CB2active", (postFixLast == "_2" ? "true" : "false"));
            editor.putString("CB3active", (postFixLast == "_3" ? "true" : "false"));
        }
        /*
          Ab hier muss der Code noch angepasst werden, weil die Daten anders gehandhabt werden muessen.
          Drei Sterne aber nur ein Feld sichtbar. Alle Infos in Array.
         */


        editor.putString("FixTime", mdfFixTime.getText().toString());
        editor.putString("CBName"+postFix, mdfCBName.getText().toString());
        editor.putString("ObservedTime"+postFix, mdfObservedTime.getText().toString());
        editor.putString("ObservedDate"+postFix, mdfObservedDate.getText().toString());
        editor.putString("Ho"+postFix, mdfHo.getText().toString());
        //editor.putString("Bearing"+postFix, mdfCBBearing.getText().toString());
        editor.putString("GHAAries"+postFix, mdfGHAAries.getText().toString());
        editor.putString("GHAAriesPlus1"+postFix, mdfGHAAriesPlus1.getText().toString());
        editor.putString("SHA"+postFix, mdfSHA.getText().toString());
        editor.putString("DeclinationNA"+postFix, mdfDeclinationNA.getText().toString());

        editor.putString("PositionLatLong", mdfPosition.getText().toString());

        editor.putString("WhoAmI","COMPLEX");
        editor.apply();
        editor.commit();

    }

    @Override
    public void onDestroyView() {
        //System.out.println("ON DESTROY WINDOW");

        if (CB1.isChecked())
        {
            postFixLast="_1";
        }
        else
        if (CB2.isChecked())
        {
            postFixLast="_2";
        }
        else
        {
            postFixLast="_3";
        }

        SaveCBrelatedData(postFixLast,false);  // false, data from visible mask

        super.onDestroyView();
        binding = null;
    }

}