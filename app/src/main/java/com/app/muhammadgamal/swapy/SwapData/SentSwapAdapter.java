package com.app.muhammadgamal.swapy.SwapData;

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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class SentSwapAdapter extends ArrayAdapter<SwapRequest> {

    public SentSwapAdapter(Context context, int resource, List<SwapRequest> sampleArrayList) {
        super(context, resource, sampleArrayList);
    }

    @Nullable
    @Override
    public SwapRequest getItem(int position) {
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
        SwapRequest sentSwapBody = getItem(position);

        ImageView ImageSentListItem =convertView.findViewById(R.id.ImageSentListItem);
        TextView NameSentItem = convertView.findViewById(R.id.NameSentItem);
        TextView ShiftTimeSentListItem = convertView.findViewById(R.id.ShiftTimeSentListItem);
        TextView shiftDaySentListItem = convertView.findViewById(R.id.shiftDaySentListItem);
        TextView shiftDateSentListItem = convertView.findViewById(R.id.shiftDateSentListItem);
        TextView preferredShiftSentListItem = convertView.findViewById(R.id.preferredShiftSentListItem);
        final ProgressBar progressBarSentListItem = convertView.findViewById(R.id.progressBarSentListItem);

        if (sentSwapBody != null){

            NameSentItem.setText(sentSwapBody.getToName());
            ShiftTimeSentListItem.setText(sentSwapBody.getToShiftTime());
            shiftDaySentListItem.setText(sentSwapBody.getToShiftDay());
            preferredShiftSentListItem.setText(sentSwapBody.getToPreferredShift());
            shiftDateSentListItem.setText(sentSwapBody.getToShiftDate());
            if (sentSwapBody.getToImageUrl() != null){
                progressBarSentListItem.setVisibility(View.VISIBLE);
                Glide.with(ImageSentListItem.getContext())
                        .load(sentSwapBody.getToImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBarSentListItem.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(ImageSentListItem);
            } else {
                progressBarSentListItem.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = context.getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                ImageSentListItem.setImageDrawable(photoUrl);
            }

        }

        return convertView;
    }
}
