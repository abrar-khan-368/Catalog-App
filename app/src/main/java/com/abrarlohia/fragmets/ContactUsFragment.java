package com.abrarlohia.fragmets;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abrarlohia.dressmaterialcatalog.MainActivity;
import com.abrarlohia.dressmaterialcatalog.R;
import com.google.android.material.snackbar.Snackbar;


public class ContactUsFragment extends Fragment {

    private Button btn_send_mail;
    private EditText EdtTxt_name, EdtTxt_phno, EdtTxt_email, EdtTxt_msg;
    private String name, ph, mail, msg;
    private ImageButton btn_whatsapp2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_us_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_whatsapp2 = view.findViewById(R.id.btn_whatsapp2);
        btn_send_mail = view.findViewById(R.id.btn_send_mail);
        EdtTxt_name = view.findViewById(R.id.EdtTxt_name);
        EdtTxt_phno = view.findViewById(R.id.EdtTxt_phno);
        EdtTxt_msg = view.findViewById(R.id.EdtTxt_msg);
        //Todo: Whats App
        btn_whatsapp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSupportChat();
            }
        });
        btn_send_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW
                        , Uri.parse("mailto:" + "womantrend4@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contact");
                intent.putExtra(Intent.EXTRA_TEXT, "My name is: " + EdtTxt_name.getText().toString() + " \n "
                        + "Phone No : " + EdtTxt_phno.getText().toString() + " \n "
                        + "My Message  :\n " + EdtTxt_msg.getText().toString());
                startActivity(intent);

            }
        });
    }

    //TODO:Whats App Chat
    private void startSupportChat() {
        try {
            String trimToNumner = "+919558021665"; //10 digit number
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://wa.me/" + trimToNumner + "/?text=" + "Hello WomanTrend"));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
