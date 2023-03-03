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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nav.astronavigator.databinding.FragmentSextantBinding;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_CBCorrection#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_CBCorrection extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    com.nav.astronavigator.CelestialBodys CelestialBodys; //=new CelestialBodys(this);

    Button pbBack;
    Button pbIncrCharset;
    Button pbDecrCharset;
    Button pbPrevCB;
    Button pbNextCB;
    Button pbReset;


    TextView dfCBName;
    TextView dfSHA;
    TextView dfSHACorr;
    TextView dfSHAToday;
    TextView dfDeclination;
    TextView dfDeclCorr;
    TextView dfDeclinationToday;

    TextView dfTestDate;
    TextView dfTestTime;

    public static float pxFromDp(float dp, Context mContext) {
        return dp * mContext.getResources().getDisplayMetrics().density;
    }

    public void setTextSize (int dp) {
        dfCBName.setTextSize(pxFromDp(dp, getActivity()));
        dfSHA.setTextSize(pxFromDp(dp, getActivity()));
        dfSHACorr.setTextSize(pxFromDp(dp, getActivity()));
        dfSHAToday.setTextSize(pxFromDp(dp, getActivity()));
        dfDeclination.setTextSize(pxFromDp(dp, getActivity()));
        dfDeclCorr.setTextSize(pxFromDp(dp, getActivity()));
        dfDeclinationToday.setTextSize(pxFromDp(dp, getActivity()));
        dfTestDate.setTextSize(pxFromDp(dp, getActivity()));
        dfTestTime.setTextSize(pxFromDp(dp, getActivity()));
    }
    private FragmentSextantBinding binding;
    public static final String MyPREFERENCES = "AstroNavPrefs" ;
    SharedPreferences sharedpreferences;

    public Fragment_CBCorrection() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_CBCorrection.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_CBCorrection newInstance(String param1, String param2) {
        Fragment_CBCorrection fragment = new Fragment_CBCorrection();
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



    int CBcounter=0;

    void ShowCBDataFromInternalTable()
            /*
                ToDo: If data is from SharedPreferences than background of field GREEN else WHITE
             */
    {
        dfCBName.setText(CelestialBodys.startable[CBcounter].getCBName().toUpperCase());
        dfSHA.setText(CelestialBodys.startable[CBcounter].getSHA());
        dfSHACorr.setText(CelestialBodys.startable[CBcounter].getSHAcor());
        dfDeclination.setText(CelestialBodys.startable[CBcounter].getDecl());
        dfDeclCorr.setText(CelestialBodys.startable[CBcounter].getDeclCor());
    }


    Boolean bCanSave=false;   // If data is useable than it's possible to save them
    void calculate()
    {

        /*
             ToDo: Take the data from SHA, SHACorr, Declination and Declination corr only from the display!
         */

        try {
            double cv = calculus.cv(String.valueOf(dfTestDate.getText()), String.valueOf(dfTestTime.getText()));
            double SHAPLutus = calculus.DMS2Real(String.valueOf(dfSHA.getText())) + (Double.valueOf(String.valueOf(dfSHACorr.getText())) / 11.0) * cv;
            dfSHAToday.setText(calculus.Real2DMS(SHAPLutus));
            // Declination corrected by date.
            double DeclinationPLutus = calculus.DMS2Real(String.valueOf(dfDeclination.getText())) + (Double.valueOf(String.valueOf(dfDeclCorr.getText())) / 11.0) * cv;
            dfDeclinationToday.setText(calculus.Real2DMS(DeclinationPLutus));
            bCanSave=true;
            dfSHAToday.setBackgroundColor(Color.GREEN);
            dfDeclinationToday.setBackgroundColor(Color.GREEN);
        } catch (Exception e)
        {
            bCanSave=false;
            String t="Error";
            dfSHAToday.setText(t);
            dfDeclinationToday.setText(t);
            dfSHAToday.setBackgroundColor(Color.WHITE);
            dfDeclinationToday.setBackgroundColor(Color.WHITE);

        }

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CelestialBodys = new CelestialBodys(sharedpreferences);

        pbBack = getView().findViewById(R.id.pbCRBackToSecondFrag);
        pbPrevCB = getView().findViewById(R.id.pbPrevCorrCB);
        pbNextCB = getView().findViewById(R.id.pbNextCorrCB);
        pbReset = getView().findViewById(R.id.pbResetCorrCB);

        dfCBName = getView().findViewById(R.id.dfCBCorrName);
        dfCBName.setEnabled(false);
        dfCBName.setTextColor(Color.BLACK);

        dfSHA = getView().findViewById(R.id.dfCorrectionSHA);
        dfSHACorr = getView().findViewById(R.id.dfCorrectionSHAcorr);
        dfSHAToday= getView().findViewById(R.id.dfSHAToday);
        dfSHAToday.setEnabled(false);
        dfSHAToday.setTextColor(Color.BLACK);
        dfDeclination = getView().findViewById(R.id.dfCorrectionDeclination);
        dfDeclCorr = getView().findViewById(R.id.dfCorrectionDeclCorr);
        dfDeclinationToday= getView().findViewById(R.id.dfDeclinationToday);
        dfDeclinationToday.setEnabled(false);
        dfDeclinationToday.setTextColor(Color.BLACK);
        dfTestDate=getView().findViewById(R.id.dfCorrectionTestDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date());
        dfTestDate.setText( date);
        dfTestTime=getView().findViewById(R.id.dfCorrectionTestTime);
        sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        dfTestTime.setText( time);

        ShowCBDataFromInternalTable();
        calculate();

        pbIncrCharset=getView().findViewById(R.id.pbIncCRCharset);
        pbDecrCharset=getView().findViewById(R.id.pbDecCRCharset);


        CBcounter=Integer.valueOf(sharedpreferences.getString("ActiveCB#", "0"));
        ShowCBDataFromInternalTable();
        calculate();


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

        pbNextCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    if (bCanSave == true) {
                        CelestialBodys.startable[CBcounter].modifyStar(calculus.DMS2Real(String.valueOf(dfSHA.getText())),
                                Double.valueOf(String.valueOf(dfSHACorr.getText())),
                                calculus.DMS2Real(String.valueOf(dfDeclination.getText())),
                                Double.valueOf(String.valueOf(dfDeclCorr.getText())));
                    }
                } catch (Exception e)
                {

                }
                if (CBcounter<65) {CBcounter++;} else {CBcounter=0;}
                ShowCBDataFromInternalTable();
                calculate();

            }
        });

        pbPrevCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if (bCanSave == true) {
                        CelestialBodys.startable[CBcounter].modifyStar(calculus.DMS2Real(String.valueOf(dfSHA.getText())),
                                Double.valueOf(String.valueOf(dfSHACorr.getText())),
                                calculus.DMS2Real(String.valueOf(dfDeclination.getText())),
                                Double.valueOf(String.valueOf(dfDeclCorr.getText())));
                    }
                } catch (Exception e)
                {

                }
                if (CBcounter>0) {CBcounter--;} else {CBcounter=65;}
                ShowCBDataFromInternalTable();
                calculate();
            }
        });
        pbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (bCanSave == true) {
                        CelestialBodys.startable[CBcounter].modifyStar(calculus.DMS2Real(String.valueOf(dfSHA.getText())),
                                Double.valueOf(String.valueOf(dfSHACorr.getText())),
                                calculus.DMS2Real(String.valueOf(dfDeclination.getText())),
                                Double.valueOf(String.valueOf(dfDeclCorr.getText())));
                    }
                } catch (Exception e)
                {

                }

                    NavHostFragment.findNavController(Fragment_CBCorrection.this)
                            .navigate(R.id.action_CBCorrection_to_SecondFragment);

                }
        });

        pbReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    try {
                        CelestialBodys.startable[CBcounter].delCustom();
                    } catch (Exception e)
                    {

                    }
                    ShowCBDataFromInternalTable();
                    try {
                        calculate();
                    } catch (Exception e)
                    {

                    }
                    bCanSave=false;
                    dfSHAToday.setBackgroundColor(Color.WHITE);
                    dfDeclinationToday.setBackgroundColor(Color.WHITE);

            }
        });

        dfSHA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation
                calculate();
            }
        });

        dfSHACorr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation
                calculate();
            }
        });

        dfDeclination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation
                calculate();
            }
        });
        dfDeclCorr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation
                calculate();
            }
        });
        dfTestDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation
                 calculate();
            }
        });

        dfTestTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //do here your calculation

            }
        });



    }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
           sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return inflater.inflate(R.layout.fragment__c_b_correction, container, false);
    }
}