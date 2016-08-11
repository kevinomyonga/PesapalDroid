package com.kevinomyonga.pesapaldroid.samples.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kevinomyonga.pesapaldroid.samples.R;

/**
 * Created by Kevin Omyonga on 8/11/2016.
 */
public class DemoInfoFragment extends Fragment {

    Button btnGithub, btnFeedback, btnDonate;

    public DemoInfoFragment() {}

    public static DemoInfoFragment newInstance() {
        return new DemoInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        btnGithub = (Button) view.findViewById(R.id.btnGithub);
        btnFeedback = (Button) view.findViewById(R.id.btnFeedback);
        btnDonate = (Button) view.findViewById(R.id.btnDonate);

        btnGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(
                        "https://github.com/ImperiusRex/PesapalDroid")));
            }
        });

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent("android.intent.action.SENDTO",
                        Uri.fromParts("mailto", getString(R.string.app_author_email), null));
                i.putExtra("android.intent.extra.SUBJECT", "PesapalDroid Feedback");
                i.putExtra("android.intent.extra.TEXT", "Your feedback here...");
                startActivity(Intent.createChooser(i, "Send Feedback"));
            }
        });

        btnDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.scheme("https").authority("www.paypal.com").path("cgi-bin/webscr");
                uriBuilder.appendQueryParameter("cmd", "_donations");

                uriBuilder.appendQueryParameter("business", "komyonga@gmail.com");
                uriBuilder.appendQueryParameter("lc", "US");
                uriBuilder.appendQueryParameter("item_name", "Donation");
                uriBuilder.appendQueryParameter("no_note", "1");
                // uriBuilder.appendQueryParameter("no_note", "0");
                // uriBuilder.appendQueryParameter("cn", "Note to the developer");
                uriBuilder.appendQueryParameter("no_shipping", "1");
                uriBuilder.appendQueryParameter("currency_code", "USD");
                Uri payPalUri = uriBuilder.build();

                // Start your favorite browser
                Intent viewIntent = new Intent(Intent.ACTION_VIEW, payPalUri);
                startActivity(viewIntent);
            }
        });

        return view;
    }
}
