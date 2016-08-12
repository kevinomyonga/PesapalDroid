package com.kevinomyonga.pesapaldroid.samples.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kevinomyonga.pesapaldroid.samples.R;
import com.kevinomyonga.pesapaldroid.samples.activities.PortalActivity;

/**
 * Created by Kevin Omyonga on 8/11/2016.
 */
public class DemoFormFragment extends Fragment {

    EditText etxtFName, etxtLName, etxtEmail, etxtPhone, etxtDesc, etxtAmount;
    Button btnProceed;

    public DemoFormFragment() {}

    public static DemoFormFragment newInstance() {
        return new DemoFormFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_form, container, false);

        etxtFName = (EditText) view.findViewById(R.id.etxtFName);
        etxtLName = (EditText) view.findViewById(R.id.etxtLName);
        etxtEmail = (EditText) view.findViewById(R.id.etxtEmail);
        etxtPhone = (EditText) view.findViewById(R.id.etxtPhone);
        etxtDesc = (EditText) view.findViewById(R.id.etxtDesc);
        etxtAmount = (EditText) view.findViewById(R.id.etxtAmount);

        btnProceed = (Button) view.findViewById(R.id.btnProceed);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEmpty(etxtFName)) {
                    etxtFName.setError(getString(R.string.fname_required));
                } else {
                    etxtFName.setError(null);
                }
                if (isEmpty(etxtLName)) {
                    etxtLName.setError(getString(R.string.lname_required));
                } else {
                    etxtLName.setError(null);
                }
                if (isEmpty(etxtEmail)) {
                    etxtEmail.setError(getString(R.string.email_required));
                } else {
                    etxtEmail.setError(null);
                }
                if (isEmpty(etxtPhone)) {
                    etxtPhone.setError(getString(R.string.phone_required));
                } else {
                    etxtPhone.setError(null);
                }
                if (isEmpty(etxtDesc)) {
                    etxtDesc.setError(getString(R.string.desc_required));
                } else {
                    etxtDesc.setError(null);
                }
                if (isEmpty(etxtAmount)) {
                    etxtAmount.setError(getString(R.string.amount_required));
                } else {
                    etxtAmount.setError(null);
                }
                if (isEmpty(etxtFName) || isEmpty(etxtLName) || isEmpty(etxtEmail) ||
                        isEmpty(etxtPhone) || isEmpty(etxtDesc) || isEmpty(etxtAmount)) {
                    return;
                }

                Intent i = new Intent(getActivity(), PortalActivity.class);
                i.putExtra("fname", etxtFName.getText().toString());
                i.putExtra("lname", etxtLName.getText().toString());
                i.putExtra("email", etxtEmail.getText().toString());
                i.putExtra("phone", etxtPhone.getText().toString());
                i.putExtra("desc", etxtDesc.getText().toString());
                i.putExtra("amount", etxtAmount.getText().toString());
                startActivity(i);
            }
        });

        return view;
    }

    public boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }
}
