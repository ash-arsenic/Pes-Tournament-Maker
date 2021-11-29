package com.example.ppl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.net.Inet4Address;
import java.security.PrivateKey;
import java.util.ArrayList;

public class TableFragment extends Fragment {
    public static ArrayList<TeamModel> teams;
    public static ArrayList<FixturesModal> fixtures;
    String name;
    private static TeamListAdapter adapter;
    TableFragment(String name) {
        this.name = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_table, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.teams_list);
        loadData();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TeamListAdapter(teams);
        recyclerView.setAdapter(adapter);

        updateTable(fixtures);
        return view;
    }

    public  void updateTable(ArrayList<FixturesModal> fixtures) {
        for(int i=0; i<teams.size(); i++) {
            int wins = 0;
            int loses = 0;
            int draws = 0;
            int gf = 0;
            int ga = 0;
            ArrayList<FixturesModal> fic = new ArrayList<>();
            String name = teams.get(i).getAbbr();
            for(int j=0; j< fixtures.size(); j++) {
                if(fixtures.get(j).getGoalA() == -1) {
                    continue;
                }
                if(fixtures.get(j).getTeamA().equals(name) || fixtures.get(j).getTeamB().equals(name)) {
                    fic.add(fixtures.get(j));
                }
            }
            for(int k=0; k< fic.size(); k++) {
                FixturesModal f = fic.get(k);
                String teamA = f.getTeamA();
                String teamB = f.getTeamB();

                if(teamA.equals(name)) {
                    if(f.getGoalA() > f.getGoalB()) {
                        wins++;
                    }else if(f.getGoalA() < f.getGoalB()){
                        loses++;
                    } else {
                        draws++;
                    }
                    gf = gf + f.getGoalA();
                    ga = ga + f.getGoalB();
                } else if(teamB.equals(name)) {
                    if(f.getGoalA() < f.getGoalB()) {
                        wins++;
                    }else if(f.getGoalA() > f.getGoalB()){
                        loses++;
                    } else {
                        draws++;
                    }

                    gf = gf + f.getGoalB();
                    ga = ga + f.getGoalA();
                }
            }

            teams.get(i).setWins(wins);
            teams.get(i).setLoses(loses);
            teams.get(i).setDraws(draws);
            teams.get(i).setGoalFor(gf);
            teams.get(i).setGoalAgainst(ga);
            teams.get(i).setTotalMatches(wins+loses+draws);
        }
        sortTable();
        adapter.notifyDataSetChanged();
    }

    public void sortTable() {
/////////////////////////SORTING///////////////////////////////
        for(int i=0; i< teams.size()-1; i++) {
            for(int j=0; j<teams.size()-1-i; j++) {
                if (teams.get(j).getPoints() < teams.get(j+1).getPoints()) {
                    TeamModel team = teams.get(j);
                    teams.set(j, teams.get(j+1));
                    teams.set(j+1, team);
                } else if (teams.get(j).getPoints() == teams.get(j+1).getPoints()) {
                    if (teams.get(j).getGD() < teams.get(j+1).getGD()) {
                        TeamModel team = teams.get(j);
                        teams.set(j, teams.get(j+1));
                        teams.set(j+1, team);
                    } else if (teams.get(j).getGD() == teams.get(j+1).getGD()) {
                        if (teams.get(j).getGoalFor() < teams.get(j+1).getGoalFor()) {
                            TeamModel team = teams.get(j);
                            teams.set(j, teams.get(j+1));
                            teams.set(j+1, team);
                        }
                    }
                }
            }
        }
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TEAMS_PREF", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(name, "");
        Type type = new TypeToken<ArrayList<TeamModel>>() {}.getType();
        teams = gson.fromJson(json, type);
        if(teams == null) {
            teams = new ArrayList<>();
        }
        sharedPreferences = getActivity().getSharedPreferences("FIXTURES_PREF", Context.MODE_PRIVATE);

        String json2 = sharedPreferences.getString(name, "");
        type = new TypeToken<ArrayList<FixturesModal>>() {}.getType();
        fixtures = gson.fromJson(json2, type);
        if(fixtures == null) {
            fixtures = new ArrayList<>();
        }
    }

    private class TeamListAdapter extends RecyclerView.Adapter<TeamsListViewHolder> {
        ArrayList<TeamModel> teams;

        public TeamListAdapter(ArrayList<TeamModel> teams) {
            this.teams = teams;
        }

        @NonNull
        @Override
        public TeamsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.table_rows, parent, false);
            return new TeamsListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TeamsListViewHolder holder, int position) {
            holder.no.setText(String.valueOf(position+1));
            holder.name.setText(teams.get(position).getAbbr());
            holder.played.setText(String.valueOf(teams.get(position).getTotalMatches()));
            holder.win.setText(String.valueOf(teams.get(position).getWins()));
            holder.draw.setText(String.valueOf(teams.get(position).getDraws()));
            holder.lost.setText(String.valueOf(teams.get(position).getLoses()));
            holder.gd.setText(String.valueOf(teams.get(position).getGD()));
            holder.points.setText(String.valueOf(teams.get(position).getPoints()));
            holder.teamLogo.setImageResource(Integer.parseInt(teams.get(position).getImage()));
            holder.plus.setText(String.valueOf(teams.get(position).getGoalAgainst())+"/"+String.valueOf(teams.get(position).getGoalFor()));
        }

        @Override
        public int getItemCount() {
            return teams.size();
        }
    }

    private class TeamsListViewHolder extends RecyclerView.ViewHolder {

        private TextView no, name, played, win, draw, lost, plus, gd, points;
        private ImageView teamLogo;
        public TeamsListViewHolder(@NonNull View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.no);
            name = itemView.findViewById(R.id.team);
            played = itemView.findViewById(R.id.pl);
            win = itemView.findViewById(R.id.win);
            draw = itemView.findViewById(R.id.draw);
            lost = itemView.findViewById(R.id.lost);
            plus = itemView.findViewById(R.id.plus);
            gd = itemView.findViewById(R.id.gd);
            points = itemView.findViewById(R.id.points);
            teamLogo = itemView.findViewById(R.id.team_logo);
        }
    }
}