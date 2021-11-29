package com.example.ppl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MatchDetailsDialog extends AppCompatDialogFragment {

    public interface MatchDetailsListener {
        public void addDetails(ArrayList<FixturesModal> fixtures);
    }
    MatchDetailsListener matchDetailsListener;
    int position;
    ArrayList<FixturesModal> fixtures;
    public MatchDetailsDialog(ArrayList<FixturesModal> fixtures, int position) {
        this.position = position;
        this.fixtures = fixtures;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Fragment fragment = getParentFragment();
        if (fragment instanceof MatchDetailsListener) {
            matchDetailsListener = (MatchDetailsListener) fragment;
        } else {
            throw new RuntimeException("Target Fragment is not implementing MatchDetailsListener interface");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.match_details_dialog, null);

        TextInputEditText scoreA = view.findViewById(R.id.teamA_score);
        TextInputEditText scoreB = view.findViewById(R.id.teamB_score);

        builder.setView(view)
                .setTitle("Match Result")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FixturesModal fixturesModal = new FixturesModal(fixtures.get(position).getTeamA(), fixtures.get(position).getTeamB(), Integer.parseInt(scoreA.getText().toString()), Integer.parseInt(scoreB.getText().toString()), fixtures.get(position).getLogoA(), fixtures.get(position).getLogoB());
                        fixtures.set(position, fixturesModal);
                        matchDetailsListener.addDetails(fixtures);
                    }
                });

        return builder.create();
    }
}
