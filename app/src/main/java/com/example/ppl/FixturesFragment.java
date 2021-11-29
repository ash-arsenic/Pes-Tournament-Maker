package com.example.ppl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FixturesFragment extends Fragment implements MatchDetailsDialog.MatchDetailsListener {
    public ArrayList<FixturesModal> fixtures;
    private String name;
    private FixturesAdapter adapter;

    public interface UpdateTableListener {
        void updateTable(ArrayList<FixturesModal> fixtures);
    }
    UpdateTableListener updateTableListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            updateTableListener = (UpdateTableListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    public FixturesFragment(String name) {
        this.name = name;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fixtures, container, false);
        loadData();

        RecyclerView fixturesRv = view.findViewById(R.id.fixtures_rv);
        adapter = new FixturesAdapter(fixtures);
        fixturesRv.setHasFixedSize(true);
        fixturesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        fixturesRv.setAdapter(adapter);

        return view;
    }

    @Override
    public void addDetails(ArrayList<FixturesModal> fixtures) {
        this.fixtures = fixtures;
        updateTableListener.updateTable(fixtures);
        adapter.notifyDataSetChanged();
        saveData();
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("FIXTURES_PREF", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json3 = gson.toJson(fixtures);
        editor.putString(name, json3);
        editor.apply();

//        sharedPreferences = getActivity().getSharedPreferences("TEAMS_PREF", getActivity().MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//        String json4= gson.toJson(TableFragment.teams);
//        editor.putString(name, json4);
//        editor.apply();
    }

    private class FixturesAdapter extends RecyclerView.Adapter<FixturesViewHolder> {
        ArrayList<FixturesModal> fixtures;

        public FixturesAdapter(ArrayList<FixturesModal> fixtures) {
            this.fixtures = fixtures;
        }

        @NonNull
        @Override
        public FixturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.fixtures_row, parent, false);

            return new FixturesViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FixturesViewHolder holder, @SuppressLint("RecyclerView") int position) {
            FixturesModal fixture = fixtures.get(position);
            holder.teamA.setText(fixture.getTeamA());
            holder.teamB.setText(fixture.getTeamB());
            holder.logoA.setImageResource(Integer.parseInt(fixture.getLogoA()));
            holder.logoB.setImageResource(Integer.parseInt(fixture.getLogoB()));
            if(fixture.getGoalA() != -1) {
                holder.scoreA.setText(String.valueOf(fixture.getGoalA()));
                holder.scoreA.setVisibility(View.VISIBLE);
                holder.scoreB.setText(String.valueOf(fixture.getGoalB()));
                holder.scoreB.setVisibility(View.VISIBLE);
            }

            holder.editFixture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MatchDetailsDialog dialog = new MatchDetailsDialog(fixtures, position);
                    dialog.show(getChildFragmentManager(), "Match Details");
                }
            });
        }

        @Override
        public int getItemCount() {
            return fixtures.size();
        }
    }

    private class FixturesViewHolder extends RecyclerView.ViewHolder {
        TextView teamA, teamB, scoreA, scoreB;
        ImageView logoA, logoB;
        ConstraintLayout editFixture;
        public FixturesViewHolder(@NonNull View itemView) {
            super(itemView);
            teamA = itemView.findViewById(R.id.fixtures_team_name);
            teamB = itemView.findViewById(R.id.fixtures_team_name2);
            scoreA = itemView.findViewById(R.id.fixture_score1);
            scoreB = itemView.findViewById(R.id.fixture_score2);
            logoA = itemView.findViewById(R.id.fixtures_team_logo);
            logoB = itemView.findViewById(R.id.fixtures_team_logo2);

            editFixture = itemView.findViewById(R.id.add_fixture);
        }
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("FIXTURES_PREF", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(name, "");
        Type type = new TypeToken<ArrayList<FixturesModal>>() {}.getType();
        fixtures = gson.fromJson(json, type);
        if(fixtures == null) {
            fixtures = new ArrayList<>();
        }
    }
}