package com.abrarlohia.fragmets;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abrarlohia.dressmaterialcatalog.R;

public class NotificationFragment extends Fragment {
    private ImageButton btn_whatsapp3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notificaton_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_whatsapp3 = view.findViewById(R.id.btn_whatsapp3);
        btn_whatsapp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSupportChat();
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
