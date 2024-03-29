package com.nav.astronavigator;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.nav.astronavigator.databinding.FragmentSextantBinding;
import com.nav.astronavigator.calculus;
import com.nav.astronavigator.databinding.FragmentSextantBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_sextant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_sextant extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    com.nav.astronavigator.calculus calculus;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String postFix="_1";
    private FragmentSextantBinding binding;
    public static final String MyPREFERENCES = "AstroNavPrefs" ;
    SharedPreferences sharedpreferences;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextView dfCB;
    TextView dfHo;
    TextView dfIndexCorrectionIC;
    TextView dfDIP;
    TextView dfSextantAltitudeSA;
    TextView dfLIMB;
    TextView dfAtmosphericCorrections;
    TextView dfAdditionalCorrections;
    TextView dfHc;
    TextView dfHcDMS;

    Button pbBack;
    Button pbReset;
    Boolean back2Simple=false;



    public fragment_sextant() {
        // Required empty public constructor
        super(R.layout.fragment_sextant);

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_sextant.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_sextant newInstance(String param1, String param2) {
        fragment_sextant fragment = new fragment_sextant();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSextantBinding.inflate(inflater, container, false);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sextant, container, false);
    }


    double calculateSA()
    {


        return calculus.DMS2Real(String.valueOf(dfHo.getText()))+calculus.DMS2Real(String.valueOf(dfIndexCorrectionIC.getText()))+calculus.DMS2Real(String.valueOf(dfDIP.getText()));
    }

    double calculateHC()
    {

        return calculateSA()+calculus.DMS2Real(String.valueOf(dfLIMB.getText()))+calculus.DMS2Real(String.valueOf(dfAtmosphericCorrections.getText()))+calculus.DMS2Real(String.valueOf(dfAdditionalCorrections.getText()));
    }

    void displayCalculation (TextView hc, TextView sa, TextView caller)
    {
        try {
            hc.setText(Double.toString(calculateHC()));
            dfHcDMS.setText(calculus.Real2DMS(calculateHC()));
            dfHcDMS.setBackgroundColor(Color.rgb(102,255,102));
            sa.setText(Double.toString(calculateSA()));
            if (caller!=null) {
                caller.setBackgroundColor(Color.WHITE);
            }
            hc.setBackgroundColor(Color.rgb(102,255,102));
            sa.setBackgroundColor(Color.rgb(102,255,102));
        } catch (Exception e)
        {
            //System.out.println("DMS2Real format error ");
            if (caller!=null) {
                caller.setBackgroundColor(Color.rgb(51, 204, 255));
            }
            hc.setBackgroundColor(Color.rgb(255,102,102));
            dfHcDMS.setBackgroundColor(Color.rgb(255,102,102));
            sa.setBackgroundColor(Color.rgb(255,102,102));

        }

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //editor.putString("ActiveCB","_"+activeStar);
        postFix=sharedpreferences.getString("ActiveCB", "_1");

        //editor.putString("WhoAmI","SIMPLE");
        // Dieser Dialog kann entweder von Simple oder Nautical aufgerufen werden, um Hc zu berechnen
        // An Hand WhoAmI wird entschieden wer der Aufrufer war.
        if (sharedpreferences.getString("WhoAmI", "SIMPLE")=="SIMPLE")
        {
            postFix=" Simple";
            back2Simple=true;
        }


        pbBack = getView().findViewById(R.id.pbBackToSecondFrag);
        pbReset = getView().findViewById(R.id.pbReset);

        dfCB=getView().findViewById(R.id.dfCBCorrections);
        dfCB.setText("CB#"+postFix.substring(1,2));
        dfCB.setEnabled(false);
        //dfCB.setBackgroundColor(Color.GRAY);
        dfHo=getView().findViewById(R.id.dfHoCorr);
        dfIndexCorrectionIC=getView().findViewById(R.id.dfIndexCorr);
        dfDIP=getView().findViewById(R.id.dfDIPCorr);
        dfSextantAltitudeSA=getView().findViewById(R.id.dfSextantAltitude);
        dfSextantAltitudeSA.setEnabled(false);
        dfSextantAltitudeSA.setTextColor(Color.BLACK);
        //dfSextantAltitudeSA.setBackgroundColor(Color.GRAY);
        dfLIMB=getView().findViewById(R.id.dfLIMB);
        dfAtmosphericCorrections=getView().findViewById(R.id.dfAtmosphericCorr);
        dfAdditionalCorrections=getView().findViewById(R.id.dfAdditionalCorr);
        dfHc=getView().findViewById(R.id.dfHCCorrected);
        dfHc.setEnabled(false);
        dfHc.setTextColor(Color.BLACK);
        dfHcDMS=getView().findViewById(R.id.dfHCDMS);
        dfHcDMS.setTextColor(Color.BLACK);
        dfHcDMS.setEnabled(false);
        //dfHc.setBackgroundColor(Color.GRAY);

        ReadCBrelatedData(postFix);

        dfHo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                    //do here your calculation
                    displayCalculation(dfHc, dfSextantAltitudeSA, dfHo);
                }
        });

        dfIndexCorrectionIC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation
                displayCalculation(dfHc, dfSextantAltitudeSA, dfIndexCorrectionIC);
            }
        });

        dfDIP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation
                displayCalculation(dfHc, dfSextantAltitudeSA, dfDIP);
            }
        });

        dfLIMB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation
                displayCalculation(dfHc, dfSextantAltitudeSA, dfLIMB);
            }
        });

        dfAtmosphericCorrections.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation
                displayCalculation(dfHc, dfSextantAltitudeSA, dfAtmosphericCorrections);
            }
        });

        dfAdditionalCorrections.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation
                displayCalculation(dfHc, dfSextantAltitudeSA, dfAdditionalCorrections);

            }
        });


        pbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sharedpreferences.getString("WhoAmI", "COMPLEX")=="SIMPLE")
                {
                    postFix=" Simple";
                    back2Simple=true;
                }

                if (!back2Simple) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("Ho" + postFix, dfHc.getText().toString());
                    editor.apply();
                    editor.commit();

                    NavHostFragment.findNavController(fragment_sextant.this)
                            .navigate(R.id.action_SecondFragment_to_Sextant);
                }
                else
                {

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("sextant", calculus.Real2DMS(Double.valueOf(dfHc.getText().toString())));
                    editor.apply();
                    editor.commit();


                    NavHostFragment.findNavController(fragment_sextant.this)
                            .navigate(R.id.action_Sextant_to_FirstFragment);

                }
            }
        });

        pbReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dfIndexCorrectionIC.setText("000°00'00.00\"");
               dfDIP.setText("000°00'00.00\"");
               dfLIMB.setText("000°00'00.00\"");
               dfAtmosphericCorrections.setText("000°00'00.00\"");
               dfAdditionalCorrections.setText("000°00'00.00\"");

                try {
                    dfHc.setText(Double.toString(calculateHC()));
                    dfHcDMS.setText(calculus.Real2DMS(calculateHC()));
                    dfSextantAltitudeSA.setText(Double.toString(calculateSA()));
                } catch (Exception e)
                {
                    //System.out.println("DMS2Real format error ");
                }
            }
        });

        displayCalculation(dfHc, dfSextantAltitudeSA, null);
    }

    public void SaveCBrelatedData(String postFix)
    {
        //String postFix;
        SharedPreferences.Editor editor = sharedpreferences.edit();
        //Editor editor = sharedpreferences.edit();
        //editor.putString("key", "value");


            editor.putString("sextant", calculus.Real2DMS(Double.valueOf(dfHc.getText().toString())));
            editor.putString("HCCalc" + postFix, dfHc.getText().toString());
            editor.putString("HOCalc" + postFix, dfHo.getText().toString());
            editor.putString("IndexErrorIC" + postFix, dfIndexCorrectionIC.getText().toString());
            editor.putString("DIP" + postFix, dfDIP.getText().toString());
            editor.putString("SextantAlltitude" + postFix, dfSextantAltitudeSA.getText().toString());
            editor.putString("LIMB" + postFix, dfLIMB.getText().toString());
            editor.putString("AtmosphericCorrection" + postFix, dfAtmosphericCorrections.getText().toString());
            editor.putString("AdditionalCorrection" + postFix, dfAdditionalCorrections.getText().toString());

            //editor.putString("WhoAmI","COMPLEX");  // Automatisch auf den Default NA setzen. Wenn WhoAmI != SIMPLE dann geht es nach NA zurueck.


        editor.apply();
        editor.commit();

    }

    public void ReadCBrelatedData(String postFix)
    {

        dfHc.setText( sharedpreferences.getString("HCCalc"+postFix, "000°00'00.00\""));
        dfHo.setText( sharedpreferences.getString("HOCalc"+postFix, "000°00'00.00\""));
        dfIndexCorrectionIC.setText( sharedpreferences.getString("IndexErrorIC"+postFix, "000°00'00.00\""));
        dfDIP.setText( sharedpreferences.getString("DIP"+postFix, "000°00'00.00\""));
        dfSextantAltitudeSA.setText( sharedpreferences.getString("SextantAltitude"+postFix, "000°00'00.00\""));
        dfLIMB.setText( sharedpreferences.getString("LIMB"+postFix, "000°00'00.00\""));
        dfAtmosphericCorrections.setText( sharedpreferences.getString("AtmosphericCorrection"+postFix, "000°00'00.00\""));
        dfAdditionalCorrections.setText( sharedpreferences.getString("AdditionalCorrection"+postFix, "000°00'00.00\""));

        try {
            dfHc.setText(Double.toString(calculateHC()));
            dfHcDMS.setText(calculus.Real2DMS(calculateHC()));
            dfSextantAltitudeSA.setText(Double.toString(calculateSA()));
        } catch (Exception e)
        {
            //System.out.println("DMS2Real format error ");
        }

    }


    @Override
    public void onDestroyView() {
        //System.out.println("ON DESTROY WINDOW");

        SaveCBrelatedData(postFix);


        super.onDestroyView();
        binding = null;
    }
}