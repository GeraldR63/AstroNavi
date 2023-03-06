package com.nav.astronavigator;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    Button pbBack;
    TextView dfDate;
    TextView dfTime;
    TextView dfDeclination;

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
        return binding.getRoot();

    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ItemAdapter(getArguments().getInt(ARG_ITEM_COUNT)));
        */

        pbBack=view.findViewById(R.id.pbSunBack);
        dfDate=view.findViewById(R.id.dfSunDate);
        dfTime=view.findViewById(R.id.dfSunTime);
        dfDeclination=view.findViewById(R.id.dfSunDeclination);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String date = sdf.format(new Date());
        sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());

        dfDate.setText(date);
        dfTime.setText(time);
        try {
            CelestialBodys cb=new CelestialBodys(null);
            dfDeclination.setText(cb.getDeclSun(dfDate.getText().toString(), dfTime.getText().toString()));
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
                    dfDeclination.setText(cb.getDeclSun(dfDate.getText().toString(), dfTime.getText().toString()));
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
                dfDeclination.setText(cb.getDeclSun(dfDate.getText().toString(), dfTime.getText().toString()));
            } catch (Exception e)
            {

            }

            }
        });


        if (pbBack!=null) {
            pbBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    NavHostFragment.findNavController(SunDeclination.this).navigate(R.id.FirstFragment);
                }

            });
        }

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
}