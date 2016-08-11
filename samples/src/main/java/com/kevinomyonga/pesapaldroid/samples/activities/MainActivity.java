package com.kevinomyonga.pesapaldroid.samples.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.kevinomyonga.pesapaldroid.samples.R;
import com.kevinomyonga.pesapaldroid.samples.fragments.DemoFormFragment;
import com.kevinomyonga.pesapaldroid.samples.fragments.DemoInfoFragment;

/**
 * Created by Kevin Omyonga on 12/1/2015.
 */
public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Initialize the ViewPager and set an adapter
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final String[] TITLES = { "Sample Form", "About the Project" };

            @Override
            public CharSequence getPageTitle(int position) {
                return TITLES[position];
            }

            @Override
            public Fragment getItem(int position) {

                Fragment fragment = null;

                switch (position) {
                    case 0:
                        fragment = DemoFormFragment.newInstance();
                        break;
                    case 1:
                        fragment = DemoInfoFragment.newInstance();
                        break;
                }

                return fragment;
            }

            @Override
            public int getCount() {
                return TITLES.length;
            }
        };
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        // Bind the tabs to the ViewPager
        tabLayout.setupWithViewPager(viewPager);
    }
}
