package com.abrarlohia.fragmets;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abrarlohia.dressmaterialcatalog.R;

public class ContactUsFragment extends Fragment {

    private Button btn_send_mail;
    private EditText EdtTxt_name, EdtTxt_phno, EdtTxt_email, EdtTxt_msg;
    private String name, ph, mail, msg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_us_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_send_mail = view.findViewById(R.id.btn_send_mail);
        EdtTxt_name = view.findViewById(R.id.EdtTxt_name);
        EdtTxt_phno = view.findViewById(R.id.EdtTxt_phno);
        EdtTxt_msg = view.findViewById(R.id.EdtTxt_msg);


        btn_send_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW
                        , Uri.parse("mailto:" + "yashrajsinhjadeja2253@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contact");
                intent.putExtra(Intent.EXTRA_TEXT, "My name is: " + EdtTxt_name.getText().toString() + " \n "
                        + "Phone No : " + EdtTxt_phno.getText().toString() + " \n "
                        + "My Message  :\n " + EdtTxt_msg.getText().toString());
                startActivity(intent);

            }
        });
    }
}
