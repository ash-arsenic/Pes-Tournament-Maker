package com.example.ppl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CreateTournamentActivity extends AppCompatActivity {
    public static ArrayList<String> tournaments;
    TournamentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tournament);
        loadData();

        TextView label = findViewById(R.id.create_tournament_label);
        if(tournaments.size()!=0) {
            label.setVisibility(View.INVISIBLE);
        }
        RecyclerView recyclerView = findViewById(R.id.recycler_tournament);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TournamentAdapter(tournaments);
        recyclerView.setAdapter(adapter);

        FloatingActionButton createTournament = findViewById(R.id.create_tournament);
        createTournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateTournamentActivity.this, SetUpTournament.class);
                startActivity(intent);
            }
        });
    }


    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("TEAMS_PREF", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("TEAMS", "");
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        tournaments = gson.fromJson(json, type);
        if(tournaments == null) {
            tournaments = new ArrayList<>();
        }
    }

    public class TournamentAdapter extends RecyclerView.Adapter<TournamentViewHolder> {
        ArrayList<String> tournas;

        public TournamentAdapter(ArrayList<String> tournas) {
            this.tournas = tournas;
        }

        @NonNull
        @Override
        public TournamentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.tournament_row, parent, false);
            return new TournamentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TournamentViewHolder holder, int position) {
            String name = tournas.get(position);
            String label = name;
            label = label.replaceAll("_", " ");
            holder.tournament.setText(label);
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTournament(name);
                }
            });
            holder.continueTournament.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTournament(name);
                }
            });
        }

        @Override
        public int getItemCount() {
            return tournas.size();
        }
    }

    public class TournamentViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layout;
        TextView tournament;
        ImageButton continueTournament;
        public TournamentViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.tournament_row);
            tournament = itemView.findViewById(R.id.tournament_name_row);
            continueTournament = itemView.findViewById(R.id.continue_tournament);
        }
    }

    private void showTournament(String name) {
        Intent intent = new Intent(CreateTournamentActivity.this, MainActivity.class);
        intent.putExtra("NAME", name);
        startActivity(intent);
    }
}