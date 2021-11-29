package com.example.ppl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FixturesFragment.UpdateTableListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String name = getIntent().getStringExtra("NAME");

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new TableFragment(name));
        fragments.add(new FixturesFragment(name));
        ArrayList<String> tabNames = new ArrayList<>();
        tabNames.add("Table");
        tabNames.add("Fixtures");

        viewPager.setAdapter(new LeagueAdapter(getSupportFragmentManager(), fragments, tabNames));
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void updateTable(ArrayList<FixturesModal> fixtures) {
//        String tag = "android:switcher:" + R.id.view_pager + ":" + 0;
        List<Fragment> f = getSupportFragmentManager().getFragments();
        TableFragment fragment = (TableFragment) f.get(0);
        fragment.updateTable(fixtures);
    }

    private class LeagueAdapter extends FragmentStatePagerAdapter {

        ArrayList<Fragment> fragments;
        ArrayList<String> tabNames;
        public LeagueAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> tabNames) {
            super(fm);
            this.fragments = fragments;
            this.tabNames = tabNames;
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

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames.get(position);
        }
    }
}