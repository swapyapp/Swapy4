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
import com.app.muhammadgamal.swapy.SwapData.SwapRequestOff;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestShift;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class OffSentSwapAdapter extends ArrayAdapter<SwapRequestOff> {

    public OffSentSwapAdapter(Context context, int resource, List<SwapRequestOff> sampleArrayList) {
        super(context, resource, sampleArrayList);
    }

    @Nullable
    @Override
    public SwapRequestOff getItem(int position) {
        //latest swaps on top of the list
        return super.getItem(getCount() - position - 1);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.off_sent_list_item, parent, false);
        }

        final Context context = convertView.getContext();
        SwapRequestOff sentSwapBody = getItem(position);

        ImageView ImageOffSentListItem = convertView.findViewById(R.id.ImageOffSentListItem);
        TextView NameOffSentItem = convertView.findViewById(R.id.NameOffSentItem);
        TextView OffDaySentListItem = convertView.findViewById(R.id.OffDaySentListItem);
        TextView OffDateSentListItem = convertView.findViewById(R.id.OffDateSentListItem);
        final ProgressBar offProgressBarSentListItemImg1 = convertView.findViewById(R.id.offProgressBarSentListItemImg1);

        ImageView userImageOffSentListItem = convertView.findViewById(R.id.userImageOffSentListItem);
        TextView UserNameOffSentItem = convertView.findViewById(R.id.UserNameOffSentItem);
        TextView userOffDaySentListItem = convertView.findViewById(R.id.userOffDaySentListItem);
        TextView userOffDateSentListItem = convertView.findViewById(R.id.userOffDateSentListItem);
        final ProgressBar progressBarOffSentListItemImg2 = convertView.findViewById(R.id.progressBarOffSentListItemImg2);

        if (sentSwapBody != null) {

            NameOffSentItem.setText(sentSwapBody.getToName());
            OffDaySentListItem.setText(sentSwapBody.getToOffDay());
            OffDateSentListItem.setText(sentSwapBody.getToOffDate());
            if (sentSwapBody.getToImageUrl() != null) {
                offProgressBarSentListItemImg1.setVisibility(View.VISIBLE);
                Glide.with(ImageOffSentListItem.getContext())
                        .load(sentSwapBody.getToImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                offProgressBarSentListItemImg1.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(ImageOffSentListItem);
            } else {
                offProgressBarSentListItemImg1.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = context.getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                ImageOffSentListItem.setImageDrawable(photoUrl);
            }

            UserNameOffSentItem.setText(sentSwapBody.getFromName());
            userOffDaySentListItem.setText(sentSwapBody.getFromOffDay());
            userOffDateSentListItem.setText(sentSwapBody.getFromOffDate());
            if (sentSwapBody.getFromImageUrl() != null) {
                progressBarOffSentListItemImg2.setVisibility(View.VISIBLE);
                Glide.with(userImageOffSentListItem.getContext())
                        .load(sentSwapBody.getFromImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBarOffSentListItemImg2.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(userImageOffSentListItem);
            } else {
                progressBarOffSentListItemImg2.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = context.getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                userImageOffSentListItem.setImageDrawable(photoUrl);
            }

        }

        return convertView;
    }
}
