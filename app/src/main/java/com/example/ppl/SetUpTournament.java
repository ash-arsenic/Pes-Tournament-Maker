package com.example.ppl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;

public class SetUpTournament extends AppCompatActivity {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    private Button navigateNext;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_tournament);

        navigateNext = findViewById(R.id.navigate_next);

        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(new TournamentDetailsFragment());
        fragments.add(new TeamsDetails());
        pager = findViewById(R.id.viewpager);
        pager.setAdapter(new IntroPagerAdapter(getSupportFragmentManager(), fragments));

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        navigateNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pager.getCurrentItem() != fragments.size()-1) {
                    pager.setCurrentItem(pager.getCurrentItem()+1);
                }else {
                    int[] selectedRadios = TournamentDetailsFragment.getSelectedRadios();
                    if(TournamentDetailsFragment.getName().equals("")) {
                        Toast.makeText(SetUpTournament.this, "Please enter tournament name", Toast.LENGTH_SHORT).show();
                    }
                    if(TeamsDetails.teams.size() < 2) {
                        Toast.makeText(SetUpTournament.this, "Not Enough Teams", Toast.LENGTH_SHORT).show();
                    } else {
//                        Log.d("TEAMS", TeamsDetails.teams.get(0).getName()+" vs "+TeamsDetails.teams.get(1).getName());
                        saveData();
                        Intent intent = new Intent(SetUpTournament.this, MainActivity.class);
                        intent.putExtra("NAME", name);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(pager.getCurrentItem() == 0) {
            super.onBackPressed();
        }else {
            pager.setCurrentItem(pager.getCurrentItem()-1);
        }
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("TEAMS_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(TeamsDetails.teams);
        name = TournamentDetailsFragment.getName();
        name = name.toUpperCase();
        name = name.replaceAll(" ", "_");
        editor.putString(name, json);
        CreateTournamentActivity.tournaments.add(name);
        String json2 = gson.toJson(CreateTournamentActivity.tournaments);
        editor.putString("TEAMS", json2);
        editor.apply();

        ////////////////////FIXTURES////////////////////////////////////
        ArrayList<TeamModel> teams = TeamsDetails.teams;
        ArrayList<FixturesModal> fixtures = new ArrayList<>();
        int legs = TournamentDetailsFragment.getSelectedRadios()[1];
        while(legs > 0) {
            for(int i=0; i<teams.size()-1; i++) {

                for(int j=i+1; j<teams.size(); j++) {
                    fixtures.add(new FixturesModal(teams.get(i).getAbbr(), teams.get(j).getAbbr(), -1, -1, teams.get(i).getImage(), teams.get(j).getImage()));
                }
            }
            legs--;
        }
        sharedPreferences = getSharedPreferences("FIXTURES_PREF", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String json3 = gson.toJson(fixtures);
        editor.putString(name, json3);
        editor.apply();
    }

    private class IntroPagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> fragments;
        public IntroPagerAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}