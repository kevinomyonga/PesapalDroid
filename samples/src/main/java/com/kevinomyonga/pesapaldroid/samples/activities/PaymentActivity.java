package com.kevinomyonga.pesapaldroid.samples.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kevinomyonga.pesapaldroid.samples.fragments.DemoPortalJavaFragment;
import com.kevinomyonga.pesapaldroid.samples.fragments.DemoPortalXmlFragment;
import com.kevinomyonga.pesapaldroid.samples.R;

/**
 * Created by Kevin Omyonga on 12/15/2015.
 */
public class PaymentActivity extends AppCompatActivity {

    String fname, lname, email, phone, desc, amount;

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            // Show the Up button in the action bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle args = getIntent().getExtras();
        fname = args.getString("fname");
        lname = args.getString("lname");
        email = args.getString("email");
        phone = args.getString("phone");
        desc = args.getString("desc");
        amount = args.getString("amount");

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Initialize the ViewPager and set an adapter
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final String[] TITLES = { "XML Portal Sample", "Java Portal Sample" };

            @Override
            public CharSequence getPageTitle(int position) {
                return TITLES[position];
            }

            @Override
            public Fragment getItem(int position) {

                Fragment fragment;

                if (position == 1) {
                    fragment = DemoPortalXmlFragment.newInstance(fname, lname, email, phone, desc, amount);
                } else {
                    fragment = DemoPortalJavaFragment.newInstance(fname, lname, email, phone, desc, amount);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
