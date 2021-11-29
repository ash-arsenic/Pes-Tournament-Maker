package com.example.ppl;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TeamsDetails extends Fragment implements TeamDetailsDialog.TeamDetailsListener {

    public static ArrayList<TeamModel> teams;
    RecyclerView recyclerView;
    NewTeamsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teams_details, container, false);

        Button addNewTeam = view.findViewById(R.id.add_new_teams);
        addNewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamDetailsDialog dialog = new TeamDetailsDialog();
                dialog.show(getChildFragmentManager(), "Create New Team");
            }
        });

        teams = new ArrayList<>();
//        teams.add(new TeamModel("Manchester United", String.valueOf(R.drawable.man_united)));
//        teams.add(new TeamModel("Real Madrid", String.valueOf(R.drawable.real_madrid)));
//        teams.add(new TeamModel("Liecester City", String.valueOf(R.drawable.liecester_city)));
        adapter = new NewTeamsAdapter(teams);
        recyclerView = view.findViewById(R.id.added_teams);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void addDetails(String name, String abbr, String icon) {
        teams.add(new TeamModel(name, abbr, icon));
        adapter.notifyDataSetChanged();
    }

    public class NewTeamsAdapter extends RecyclerView.Adapter<NewTeamViewHolder> {
        ArrayList<TeamModel> newTeams;

        public NewTeamsAdapter(ArrayList<TeamModel> newTeams) {
            this.newTeams = newTeams;
        }

        @NonNull
        @Override
        public NewTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.new_team_rows, parent, false);
            return new NewTeamViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NewTeamViewHolder holder, int position) {
            holder.sNo.setText(String.valueOf(position+1));
            holder.teamName.setText(newTeams.get(position).getName());
            holder.teamLogo.setImageResource(Integer.parseInt(newTeams.get(position).getImage()));

            holder.editNewTeam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Okay", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return newTeams.size();
        }
    }

    public class NewTeamViewHolder extends RecyclerView.ViewHolder {
        TextView sNo;
        TextView teamName;
        ImageView teamLogo;
        ImageFilterButton editNewTeam;
        public NewTeamViewHolder(@NonNull View itemView) {
            super(itemView);
            sNo = itemView.findViewById(R.id.s_num);
            teamName = itemView.findViewById(R.id.new_team_name);
            teamLogo = itemView.findViewById(R.id.new_team_logo);

            editNewTeam = itemView.findViewById(R.id.edit_new_team);
        }
    }

}