package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.app.muhammadgamal.swapy.Adapters.IntroViewPagerAdapter;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.ResgistrationActivities.SignInActivity;
import com.app.muhammadgamal.swapy.ResgistrationActivities.SignUpActivity;
import com.app.muhammadgamal.swapy.SwapData.IntroScreenItem;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screen_viewpager;
    private IntroViewPagerAdapter introViewPagerAdapter;
    private TabLayout tab_indicator;
    private Button btn_next, btn_get_started;
    private int position;
    private Animation btnAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        if (restorePrefData()) {

            Intent intent = new Intent(IntroActivity.this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }

        tab_indicator = findViewById(R.id.tab_indicator);

        screen_viewpager = findViewById(R.id.screen_viewpager);

        final List<IntroScreenItem> mList = new ArrayList<>();
        mList.add(new IntroScreenItem("Swapy", "Swapy is a free app that let you swap your shift or your off day with hundreds of other users", R.drawable.intro1));
        mList.add(new IntroScreenItem("Search", "You can search among hundreds of swaps with specific date and time", R.drawable.intro2));
        mList.add(new IntroScreenItem("Send requests", "After you find the right people to swap with, you can send them swap requests", R.drawable.intro3));

        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screen_viewpager.setAdapter(introViewPagerAdapter);

        tab_indicator.setupWithViewPager(screen_viewpager);

        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);

        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screen_viewpager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screen_viewpager.setCurrentItem(position);
                }

                if (position == mList.size() - 1) {

                    loadLastScreen();

                }

            }
        });

        btn_get_started = findViewById(R.id.btn_get_started);
        btn_get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(IntroActivity.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                savePrefsData();

            }
        });

        tab_indicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size() - 1) {
                    loadLastScreen();
                } else {
                    btn_next.setVisibility(View.VISIBLE);
                    btn_get_started.setVisibility(View.INVISIBLE);
                    tab_indicator.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private Boolean restorePrefData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = pref.getBoolean("isIntroOpened", false);
        return isIntroActivityOpenedBefore;

    }

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.commit();

    }

    private void loadLastScreen() {

        btn_next.setVisibility(View.INVISIBLE);
        btn_get_started.setVisibility(View.VISIBLE);
        tab_indicator.setVisibility(View.INVISIBLE);

        btn_get_started.setAnimation(btnAnim);

    }
}
