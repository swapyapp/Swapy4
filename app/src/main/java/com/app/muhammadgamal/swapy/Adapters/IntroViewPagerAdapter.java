package com.app.muhammadgamal.swapy.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.IntroScreenItem;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context mContext;
    List<IntroScreenItem> mListScreen;

    public IntroViewPagerAdapter(Context mContext, List<IntroScreenItem> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.layout_screen_intro, null);

        ImageView introImage = layoutScreen.findViewById(R.id.introImage);
        TextView introTitle = layoutScreen.findViewById(R.id.introTitle);
        TextView introDescription = layoutScreen.findViewById(R.id.introDescription);

        introImage.setImageResource(mListScreen.get(position).getImage());
        introTitle.setText(mListScreen.get(position).getTitle());
        introDescription.setText(mListScreen.get(position).getDescription());

        container.addView(layoutScreen);

        return layoutScreen;

    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);
    }
}
