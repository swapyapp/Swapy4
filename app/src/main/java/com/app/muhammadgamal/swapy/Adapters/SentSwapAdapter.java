package com.app.muhammadgamal.swapy.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestShift;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class SentSwapAdapter extends ArrayAdapter<SwapRequestShift> {

    public SentSwapAdapter(Context context, int resource, List<SwapRequestShift> sampleArrayList) {
        super(context, resource, sampleArrayList);
    }

    @Nullable
    @Override
    public SwapRequestShift getItem(int position) {
        //latest swaps on top of the list
        return super.getItem(getCount() - position - 1);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sent_list_item, parent, false);
        }

        final Context context = convertView.getContext();
        SwapRequestShift sentSwapBody = getItem(position);

        ImageView ImageSentListItem = convertView.findViewById(R.id.ImageSentListItem);
        TextView NameSentItem = convertView.findViewById(R.id.NameSentItem);
        TextView ShiftTimeSentListItem = convertView.findViewById(R.id.ShiftTimeSentListItem);
        TextView shiftDaySentListItem = convertView.findViewById(R.id.shiftDaySentListItem);
        TextView shiftDateSentListItem = convertView.findViewById(R.id.shiftDateSentListItem);
        final ProgressBar progressBarSentListItemImg1 = convertView.findViewById(R.id.progressBarSentListItemImg1);

        ImageView userImageSentListItem = convertView.findViewById(R.id.userImageSentListItem);
        TextView UserNameSentItem = convertView.findViewById(R.id.UserNameSentItem);
        TextView userShiftTimeSentListItem = convertView.findViewById(R.id.userShiftTimeSentListItem);
        TextView userShiftDaySentListItem = convertView.findViewById(R.id.userShiftDaySentListItem);
        TextView userShiftDateSentListItem = convertView.findViewById(R.id.userShiftDateSentListItem);
        final ProgressBar progressBarSentListItemImg2 = convertView.findViewById(R.id.progressBarSentListItemImg2);


        if (sentSwapBody != null) {

            NameSentItem.setText(sentSwapBody.getToName());
            ShiftTimeSentListItem.setText(sentSwapBody.getToShiftTime());
            shiftDaySentListItem.setText(sentSwapBody.getToShiftDay());
            shiftDateSentListItem.setText(sentSwapBody.getToShiftDate());
            if (sentSwapBody.getToImageUrl() != null) {
                progressBarSentListItemImg1.setVisibility(View.VISIBLE);
                Glide.with(ImageSentListItem.getContext())
                        .load(sentSwapBody.getToImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBarSentListItemImg1.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(ImageSentListItem);
            } else {
                progressBarSentListItemImg1.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = context.getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                ImageSentListItem.setImageDrawable(photoUrl);
            }

            UserNameSentItem.setText(sentSwapBody.getFromName());
            userShiftTimeSentListItem.setText(sentSwapBody.getFromShiftTime());
            userShiftDaySentListItem.setText(sentSwapBody.getFromShiftDay());
            userShiftDateSentListItem.setText(sentSwapBody.getFromShiftDate());
            if (sentSwapBody.getFromImageUrl() != null) {
                progressBarSentListItemImg2.setVisibility(View.VISIBLE);
                Glide.with(userImageSentListItem.getContext())
                        .load(sentSwapBody.getFromImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBarSentListItemImg2.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(userImageSentListItem);
            } else {
                progressBarSentListItemImg2.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = context.getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                userImageSentListItem.setImageDrawable(photoUrl);
            }

        }

        return convertView;
    }
}
