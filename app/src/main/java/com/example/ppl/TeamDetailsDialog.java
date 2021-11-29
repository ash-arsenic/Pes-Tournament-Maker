package com.example.ppl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

public class TeamDetailsDialog extends AppCompatDialogFragment {

    public interface TeamDetailsListener {
        public void addDetails(String name, String abbr, String icon);
    }
    TeamDetailsListener teamDetailsListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Fragment fragment = getParentFragment();
        if (fragment instanceof TeamDetailsListener) {
            teamDetailsListener = (TeamDetailsListener) fragment;
        } else {
            throw new RuntimeException("Target Fragment is not implementing SendData interface");
        }
    }

    private int selectedItem = R.drawable.man_united;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.team_details_dialog, null);
        TextInputEditText teamName = view.findViewById(R.id.team_name);
        TextInputEditText teamAbbr = view.findViewById(R.id.team_abbr);
        RadioGroup teamImage = view.findViewById(R.id.team_image_radio_group);
        teamImage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.mnu:
                        selectedItem = R.drawable.man_united;
                        break;

                    case R.id.mc:
                        selectedItem = R.drawable.man_city;
                        break;

                    case R.id.lfc:
                        selectedItem = R.drawable.liverpool;
                        break;

                    case R.id.cfc:
                        selectedItem = R.drawable.chelsea;
                        break;

                    case R.id.rm:
                        selectedItem = R.drawable.real_madrid;
                        break;

                    case R.id.lcfc:
                        selectedItem = R.drawable.liecester_city;
                        break;

                    case R.id.fcb:
                        selectedItem = R.drawable.barcelona;
                        break;

                    case R.id.afc:
                        selectedItem = R.drawable.aresnal;
                        break;
                }
            }
        });

        builder.setView(view)
                .setTitle("Add new teams")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        teamDetailsListener.addDetails(teamName.getText().toString(), teamAbbr.getText().toString(), String.valueOf(selectedItem));
                    }
                });

        return builder.create();
    }
}
