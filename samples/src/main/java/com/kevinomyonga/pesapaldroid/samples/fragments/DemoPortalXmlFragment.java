package com.kevinomyonga.pesapaldroid.samples.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevinomyonga.pesapaldroid.PesapalDroidFragment;
import com.kevinomyonga.pesapaldroid.samples.R;

/**
 * Created by Kevin Omyonga on 12/15/2015.
 */
public class DemoPortalXmlFragment extends Fragment {

    public DemoPortalXmlFragment() {}

    public static DemoPortalXmlFragment newInstance(String fname, String lname, String email,
                                                    String phone, String desc, String amount) {
        DemoPortalXmlFragment f = new DemoPortalXmlFragment();
        Bundle args = new Bundle();
        args.putString("fname", fname);
        args.putString("lname", lname);
        args.putString("email", email);
        args.putString("phone", phone);
        args.putString("desc", desc);
        args.putString("amount", amount);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_xml, container, false);

        Bundle args = getArguments();

        PesapalDroidFragment pesapalDroidFragment = (PesapalDroidFragment)
                getChildFragmentManager().findFragmentById(R.id.payment_fragment);

        //Pass the buyer details
        pesapalDroidFragment.setfName(args.getString("fname"));
        pesapalDroidFragment.setlName(args.getString("lname"));
        pesapalDroidFragment.setEmail(args.getString("email"));
        pesapalDroidFragment.setPhone(args.getString("phone"));
        pesapalDroidFragment.setDesc(args.getString("desc"));
        pesapalDroidFragment.setAmount(args.getString("amount"));

        return view;
    }
}
