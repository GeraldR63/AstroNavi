package com.nav.astronavigator;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.print.PrintManager;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class NauticalAlmanac extends Fragment {
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

    com.nav.astronavigator.NADataAndCalc NADataAndCalc=new NADataAndCalc();
    com.nav.astronavigator.CelestialBodys CelestialBodys; //=new CelestialBodys(this);
    printSightRedcution printSightRedcution;

    Boolean bCalculated=false;                       // Wird zum Drucken gebraucht. Drucken nur nach erfolgreicher Berechnung.
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
     //CheckBox    checkBoxCalcMode;
     Button      pbCBS;                    // Change Celestial Body Defaults
     Button      pbDefaultsNA;
     Button      pbSextant;
     Button      pbIncrCharset;
     Button      pbDecrCharset;
     Button      pbPrintSightReductionForm;

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

    public int[] CBcounter= new int[3];

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
        //mdfCBName.setText(sharedpreferences.getString("CBName"+postFix, "Regulus"));
        mdfCBName.setText(CelestialBodys.startable[CBcounter[activeStar-1]].name.toUpperCase());
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


    public static float pxFromDp(float dp, Context mContext) {
        return dp * mContext.getResources().getDisplayMetrics().density;
    }

    //int dp=8;

    public void setTextSize (int dp)
    {
        mdfDate.setTextSize(pxFromDp(dp, getActivity()));
        mdfTime.setTextSize(pxFromDp(dp, getActivity()));
        mdfDRLat.setTextSize(pxFromDp(dp, getActivity()));
        mdfDRLong.setTextSize(pxFromDp(dp, getActivity()));
        mdfHeading.setTextSize(pxFromDp(dp, getActivity()));
        mdfSpeed.setTextSize(pxFromDp(dp, getActivity()));
        pbCalcNA.setTextSize(pxFromDp(dp, getActivity()));
        pbCBS.setTextSize(pxFromDp(dp, getActivity()));
        pbDefaultsNA.setTextSize(pxFromDp(dp, getActivity()));
        pbSextant.setTextSize(pxFromDp(dp, getActivity()));
        pbNextCB.setTextSize(pxFromDp(dp, getActivity()));
        pbPrevCB.setTextSize(pxFromDp(dp, getActivity()));
        pbPrintSightReductionForm.setTextSize(pxFromDp(dp, getActivity()));

        /*
        CB1.setTextSize(pxFromDp(dp, getActivity()));
        CB2.setTextSize(pxFromDp(dp, getActivity()));
        CB3.setTextSize(pxFromDp(dp, getActivity()));
        */

        mdfCBName.setTextSize(pxFromDp(dp, getActivity()));
        mdfFixTime.setTextSize(pxFromDp(dp, getActivity()));
        mdfObservedTime.setTextSize(pxFromDp(dp, getActivity()));
        mdfObservedDate.setTextSize(pxFromDp(dp, getActivity()));
        mdfHo.setTextSize(pxFromDp(dp, getActivity()));
        /*
        TextView21.setTextSize(pxFromDp(dp, getActivity()));
        TextView22.setTextSize(pxFromDp(dp, getActivity()));
        TextView23.setTextSize(pxFromDp(dp, getActivity()));
        TextView24.setTextSize(pxFromDp(dp, getActivity()));
         */
        mdfGHAAries.setTextSize(pxFromDp(dp, getActivity()));
        mdfGHAAriesPlus1.setTextSize(pxFromDp(dp, getActivity()));
        mdfSHA.setTextSize(pxFromDp(dp, getActivity()));
        mdfDeclinationNA.setTextSize(pxFromDp(dp, getActivity()));
        mdfPosition.setTextSize(pxFromDp(dp, getActivity()));
        mdfStatus.setTextSize(pxFromDp(dp, getActivity()));
    }

    void calculate(View view)
    {
        /*
          From P.Lutus Source eph.c
            now = mdy_sect(datime);
          	cv = (now / 86400.0) - 29220.0;
	        cv /= 365.25;
	        gha = ghaa + (startable[i].sha + (startable[i].shacor * cv));
			dec = startable[i].decl + (startable[i].declcor * cv);
         */

        double cv=calculus.cv(String.valueOf(mdfObservedDate.getText()), String.valueOf(mdfObservedTime.getText()));

        mdfCBName.setText(CelestialBodys.startable[CBcounter[activeStar-1]].name.toUpperCase());
        try {
            // How to correct SHA?
            // The divider 11 is a correction to all of the corr values from P. Lutus gathered in 1980.
            // This value is more or less good. The difference to the calculations of the NA is nearby 2 minutes.
            // At the open water we want to know where we are. Nearby the coast Astronavigation makes no sense! That's why this calculation is
            // not perfect but good and it's in memory of P.Lutus one of the first fellows which wrote such astronavigational software.
            // ToDo: If you want to have better results than calculate all of the correction values in the Star table in file CelestialBody.java.

            double SHAPLutus=CelestialBodys.startable[CBcounter[activeStar-1]].sha +(CelestialBodys.startable[CBcounter[activeStar-1]].shacor/11)*cv;
            mdfSHA.setText(calculus.Real2DMS(SHAPLutus));
            // Declination corrected by date.
            double DeclinationPLutus=CelestialBodys.startable[CBcounter[activeStar-1]].decl + (CelestialBodys.startable[CBcounter[activeStar-1]].declcor/11) *cv;
            mdfDeclinationNA.setText(calculus.Real2DMS(DeclinationPLutus));
            CelestialBodys.selectedStar[activeStar-1]=CBcounter[activeStar-1];
            double GHAAries=NADataAndCalc.GHAAries(String.valueOf(mdfObservedDate.getText()),String.valueOf(mdfObservedTime.getText()));
            mdfGHAAries.setText(calculus.Real2DMS(GHAAries));
            double GHAAriesP1=NADataAndCalc.GHAAries(String.valueOf(mdfObservedDate.getText()),String.valueOf(mdfObservedTime.getText()));
            mdfGHAAriesPlus1.setText(calculus.Real2DMS(GHAAriesP1));

            mdfGHAAries.setBackgroundColor(Color.GREEN);
            mdfGHAAriesPlus1.setBackgroundColor(Color.GREEN);

            mdfStatus.setBackgroundColor(Color.WHITE);

        }
        catch (Exception e)
        {
            mdfStatus.setText("Input error. Check date/time for CB");
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CelestialBodys=new CelestialBodys(sharedpreferences);

        mdfDate=getView().findViewById(R.id.dfDate);
        mdfTime=getView().findViewById(R.id.dfTime);

        /*
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date());
        mdfDate.setText( date);
        sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        mdfTime.setText( time);
        */


        mdfDRLat=getView().findViewById(R.id.dfDRLat);
        mdfDRLong=getView().findViewById(R.id.dfDRLong);
        mdfHeading=getView().findViewById(R.id.dfHeading);
        mdfSpeed=getView().findViewById(R.id.dfSpeed);
        pbCalcNA=getView().findViewById(R.id.pbCalcNA);


        /*
             This check box do not make sense!
             A user can press the PREV and NEXT buttons to get GHA, SHA and DECLINATION from calculator
             but he can correct these values by using Nautical Almanac.
         */

        pbCBS=getView().findViewById(R.id.pbStarCorrection); //Calculation NA oder P.Lutus


        pbDefaultsNA=getView().findViewById(R.id.pbDefaultsNA);
        pbSextant=getView().findViewById(R.id.pbSextant);
        pbNextCB=getView().findViewById(R.id.pbNext);
        pbPrevCB=getView().findViewById(R.id.pbPrevCB);
        pbPrintSightReductionForm=getView().findViewById(R.id.pbPrintSightReduction);
        pbIncrCharset=getView().findViewById(R.id.pbIncrNACharset);
        pbDecrCharset=getView().findViewById(R.id.pbDecrNACharset);
        CB1=getView().findViewById(R.id.cb1);
        CB2=getView().findViewById(R.id.cb2);
        CB3=getView().findViewById(R.id.cb3);
        mdfCBName=getView().findViewById(R.id.dfCBName);
        mdfCBName.setEnabled(false);
        mdfCBName.setTextColor(Color.BLACK);

        CB1.setChecked(true);

        mdfFixTime=getView().findViewById(R.id.dfFixTime);
        mdfObservedTime=getView().findViewById(R.id.dfObservedTime);
        mdfObservedDate=getView().findViewById(R.id.dfObservedDate);

        mdfHo=getView().findViewById(R.id.dfHo);
        // mdfCBBearing=getView().findViewById(R.id.dfCBbearing);

        TextView21=getView().findViewById(R.id.textView21);
        TextView22=getView().findViewById(R.id.textView22);
        TextView23=getView().findViewById(R.id.textView23);
        TextView24=getView().findViewById(R.id.textView24);

        mdfGHAAries=getView().findViewById(R.id.dfGHAAries);
        mdfGHAAriesPlus1=getView().findViewById(R.id.dfGHAAriesPlus1);
        mdfSHA=getView().findViewById(R.id.dfSHA);
        mdfDeclinationNA=getView().findViewById(R.id.dfDeclinationNA);

        mdfPosition=getView().findViewById(R.id.dfPosition);
        mdfPosition.setEnabled(false);
        mdfPosition.setTextColor(Color.BLACK);
        mdfStatus=getView().findViewById(R.id.dfStatus);

        mdfStatus.setEnabled(false);
        mdfStatus.setTextColor(Color.BLACK);





        mdfGHAAries.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation
                mdfGHAAries.setBackgroundColor(Color.WHITE);
                mdfStatus.setText("GHA Aries manual input");
            }
        });

        mdfGHAAriesPlus1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation
                mdfGHAAriesPlus1.setBackgroundColor(Color.WHITE);
                mdfStatus.setText("GHA AriesPlus1 manual input");
            }
        });



        mdfObservedDate.addTextChangedListener(new TextWatcher() {
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


        mdfObservedTime.addTextChangedListener(new TextWatcher() {
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

        printSightRedcution = new printSightRedcution(NADataAndCalc);
        pbPrintSightReductionForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* ToDo: Print the Sight Reduction */

                    try {
                        if (NADataAndCalc!=null) {
                            printSightRedcution.doWebViewPrint(getActivity());
                            //printSightRedcution.doWebViewPrint(MainActivity.this);
                            mdfStatus.setBackgroundColor(Color.WHITE);
                            mdfStatus.setText("Printing Sight Reduction Form");
                        }
                        else {
                            mdfStatus.setBackgroundColor(Color.WHITE);
                            mdfStatus.setText("No printable calculation");
                        }
                    }
                    catch (Exception e)
                    {
                        mdfStatus.setBackgroundColor(Color.RED);
                        mdfStatus.setText(e.getMessage());
                    }

                //printSightRedcution=null;
            }
        });


        pbDefaultsNA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NADataAndCalc!= null) {
                    NADataAndCalc.setDefaultsNA(view, NauticalAlmanac.this);
                    mdfGHAAries.setBackgroundColor(Color.WHITE);
                    mdfGHAAriesPlus1.setBackgroundColor(Color.WHITE);

                    CBcounter[2]=35;   //Regulus
                    postFixLast="_3";
                    activeStar=3;
                    refreshCBrelatedData(postFixLast);

                    CBcounter[1]=14;  //Antares
                    postFixLast="_2";
                    activeStar=2;
                    refreshCBrelatedData(postFixLast);


                    CBcounter[0]=46;  //Kochab
                    postFixLast="_1";
                    activeStar=1;
                    refreshCBrelatedData(postFixLast);



                    mdfStatus.setText("Set Defaults NA 04.07.2022");
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
                if (CBcounter[activeStar-1]<65) {CBcounter[activeStar-1]++;} else {CBcounter[activeStar-1]=0;}

                calculate(view);
                mdfStatus.setText("Searched for Celestial Body");

            }
        });

        pbPrevCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CBcounter[activeStar-1]>0) {CBcounter[activeStar-1]--;} else {CBcounter[activeStar-1]=65;}
                calculate(view);
                mdfStatus.setText("Searched for Celestial Body");
            }
        });

        pbCalcNA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DelayedMessage msg=new DelayedMessage(view);
                bCalculated=false;
                // Maske abspeichern, damit Aenderungen bei der Berechnung beruecksichtigt werden.
                SaveCBrelatedData(postFixLast,false);

                    try {
                        msg.ShowSnackbar("Calculation using NA");
                        mdfPosition.setText(NADataAndCalc.Calculate(view, NauticalAlmanac.this, sharedpreferences));
                        bCalculated=true;
                    }
                    catch (Exception e)
                    {
                        msg.ShowSnackbar("Input error. Check DMS format!");
                    }



            }
        });

        pbCBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DelayedMessage msg=new DelayedMessage(view);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("ActiveCB#",""+CBcounter[activeStar-1]);
                editor.apply();
                    msg.ShowSnackbar("Set CB Defaults by yourself!");
                NavHostFragment.findNavController(NauticalAlmanac.this)
                        .navigate(R.id.action_SecondFragment_to_CBCorrection);



            }
        });

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveCBrelatedData(postFixLast,false);
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

                SaveCBrelatedData(postFixLast,false);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("ActiveCB","_"+activeStar);
                editor.putString("WhoAmI","COMPLEX");
                editor.putString("CurrentHC",""+mdfHo.getText());
                editor.apply();
                //editor.commit();

                NavHostFragment.findNavController(NauticalAlmanac.this)
                        .navigate(R.id.action_SecondFragment_to_Sextant);

                refreshCBrelatedData(postFixLast);

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



        mdfDate.setText( sharedpreferences.getString("date", "01.01.2023"));
        mdfTime.setText( sharedpreferences.getString("time", "11:55:00"));
        mdfDRLong.setText( sharedpreferences.getString("DRLong", "000°00'00.00\""));
        mdfDRLat.setText( sharedpreferences.getString("DRLat", "000°00'00.00\""));
        mdfHeading.setText( sharedpreferences.getString("DRHeading", "172°"));
        mdfSpeed.setText( sharedpreferences.getString("DRSpeed", "18.5kn"));
        mdfPosition.setText( sharedpreferences.getString("LastPosition", "000°00'00.0\" 000°00'00.0\""));
        setTextSize(Integer.valueOf(sharedpreferences.getString("CharSize", "9")));
        CBcounter[0]=Integer.valueOf(sharedpreferences.getString("ActiveStar_1", "1"));
        CBcounter[1]=Integer.valueOf(sharedpreferences.getString("ActiveStar_2", "2"));
        CBcounter[2]=Integer.valueOf(sharedpreferences.getString("ActiveStar_3", "3"));

        if (sharedpreferences.getString("CB1active", "false").equals("true"))
        {
            CB1.setChecked(true);
            CB2.setChecked(false);
            CB3.setChecked(false);
            postFixLast="_1";
            activeStar=1;
            mdfCBName.setText(CelestialBodys.startable[CBcounter[activeStar-1]].name.toUpperCase());
            mdfStatus.setBackgroundColor(Color.WHITE);
            mdfStatus.setText("Selected CB#1");
            refreshCBrelatedData(postFixLast);

        }
        else if (sharedpreferences.getString("CB2active", "false").equals("true"))
        {
            CB1.setChecked(false);
            CB2.setChecked(true);
            CB3.setChecked(false);
            postFixLast="_2";
            activeStar=2;
            mdfCBName.setText(CelestialBodys.startable[CBcounter[activeStar-1]].name.toUpperCase());
            mdfStatus.setBackgroundColor(Color.WHITE);
            mdfStatus.setText("Selected CB#2");
            refreshCBrelatedData(postFixLast);
        }
        else
        {
            CB1.setChecked(false);
            CB2.setChecked(false);
            CB3.setChecked(true);
            postFixLast="_3";
            activeStar=3;
            mdfCBName.setText(CelestialBodys.startable[CBcounter[activeStar-1]].name.toUpperCase());
            mdfStatus.setBackgroundColor(Color.WHITE);
            mdfStatus.setText("Selected CB#3");
            refreshCBrelatedData(postFixLast);
        }

        /*
          This code is just to check if it is required to reload CB Data in case
          user called "Celestial Body Correction" and changed something for the CB.
         */
        int checkStar=Integer.valueOf(sharedpreferences.getString("ActiveCB#", "-1"));
        if (checkStar!=-1)
        {
            /*
                The field is removed from SharedPreferences to avoid problems
             */
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.remove("ActiveCB#");
            editor.apply();

            /*
                Quick and dirty to refresh the dialog with changes made in the CBS dialog.
             */
            pbNextCB.performClick();
            pbPrevCB.performClick();
        }


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

        //CBcounter[0]=Integer.valueOf(sharedpreferences.getString("ActiveStar"+postFixLast, "1"));
        editor.putString("ActiveStar_1",String.valueOf(CBcounter[0]));
        editor.putString("ActiveStar_2",String.valueOf(CBcounter[1]));
        editor.putString("ActiveStar_3",String.valueOf(CBcounter[2]));

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
        //editor.commit();

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