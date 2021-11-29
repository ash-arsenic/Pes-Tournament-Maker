package com.example.ppl;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;

public class TournamentDetailsFragment extends Fragment {
    private static TextInputEditText tournamentName;
    private RadioGroup typeRadioGroup;
    private RadioGroup legsRadioGroup;
    public static int[] selectedRadios = {0, 1};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tournament_details, container, false);

        tournamentName = view.findViewById(R.id.tournament_name);
        typeRadioGroup = view.findViewById(R.id.radio_group);
        legsRadioGroup = view.findViewById(R.id.radio_group2);

        typeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.round_robin:
                        selectedRadios[0] = 0;
                        break;
                    case R.id.elimination:
                        selectedRadios[0] = 1;
                        break;

                }
            }
        });

        legsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.one:
                        selectedRadios[1] = 1;
                        break;
                    case R.id.two:
                        selectedRadios[1] = 2;
                        break;
                    case R.id.three:
                        selectedRadios[1] = 3;
                        break;
                }
            }
        });
        return view;
    }
    public static String getName() {
        return tournamentName.getText().toString();
    }

    public static int[] getSelectedRadios() {
        return selectedRadios;
    }

}