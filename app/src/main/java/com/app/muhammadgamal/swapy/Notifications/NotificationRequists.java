package com.app.muhammadgamal.swapy.Notifications;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.muhammadgamal.swapy.Fragments.ReceivedSwapsFragment;
import com.app.muhammadgamal.swapy.R;

public class NotificationRequists extends AppCompatActivity {

    private Button openNotificationTab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_requists);
        //getActionBar().hide();

        openNotificationTab = (Button)findViewById(R.id.open_notification_btn);
        openNotificationTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceivedSwapsFragment fragment = new ReceivedSwapsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_notification,fragment);
                transaction.commit();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
