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

public class ReceivedSwapAdapter extends ArrayAdapter<SwapRequestShift> {

    public ReceivedSwapAdapter(Context context, int resource, List<SwapRequestShift> sampleArrayList) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.received_list_item, parent, false);
        }

        final Context context = convertView.getContext();
        SwapRequestShift receivedSwapBody = getItem(position);

        ImageView ImageReceivedListItem = convertView.findViewById(R.id.ImageReceivedListItem);
        TextView NameReceivedListItem = convertView.findViewById(R.id.NameReceivedListItem);
        TextView ShiftTimeReceivedListItem = convertView.findViewById(R.id.ShiftTimeReceivedListItem);
        TextView shiftDayReceivedListItem = convertView.findViewById(R.id.shiftDayReceivedListItem);
        TextView shiftDateReceivedListItem = convertView.findViewById(R.id.shiftDateReceivedListItem);
        final ProgressBar progressBarReceivedListItemImg1 = convertView.findViewById(R.id.progressBarReceivedListItemImg1);

        ImageView userImageReceivedListItem = convertView.findViewById(R.id.userImageReceivedListItem);
        TextView UserNameReceivedListItem = convertView.findViewById(R.id.UserNameReceivedListItem);
        TextView userShiftTimeReceivedListItem = convertView.findViewById(R.id.userShiftTimeReceivedListItem);
        TextView userShiftDayReceivedListItem = convertView.findViewById(R.id.userShiftDayReceivedListItem);
        TextView userShiftDateReceivedListItem = convertView.findViewById(R.id.userShiftDateReceivedListItem);
        final ProgressBar progressBarReceivedListItemImg2 = convertView.findViewById(R.id.progressBarReceivedListItemImg2);

        if (receivedSwapBody != null) {

            NameReceivedListItem.setText(receivedSwapBody.getFromName());
            ShiftTimeReceivedListItem.setText(receivedSwapBody.getFromShiftTime());
            shiftDayReceivedListItem.setText(receivedSwapBody.getFromShiftDay());
            shiftDateReceivedListItem.setText(receivedSwapBody.getFromShiftDate());
            if (receivedSwapBody.getFromImageUrl() != null) {
                progressBarReceivedListItemImg1.setVisibility(View.VISIBLE);
                Glide.with(ImageReceivedListItem.getContext())
                        .load(receivedSwapBody.getFromImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBarReceivedListItemImg1.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(ImageReceivedListItem);
            } else {
                progressBarReceivedListItemImg1.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = context.getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                ImageReceivedListItem.setImageDrawable(photoUrl);
            }

            UserNameReceivedListItem.setText(receivedSwapBody.getToName());
            userShiftTimeReceivedListItem.setText(receivedSwapBody.getToShiftTime());
            userShiftDayReceivedListItem.setText(receivedSwapBody.getToShiftDay());
            userShiftDateReceivedListItem.setText(receivedSwapBody.getToShiftDate());
            if (receivedSwapBody.getToImageUrl() != null) {
                progressBarReceivedListItemImg2.setVisibility(View.VISIBLE);
                Glide.with(userImageReceivedListItem.getContext())
                        .load(receivedSwapBody.getToImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBarReceivedListItemImg2.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(userImageReceivedListItem);
            } else {
                progressBarReceivedListItemImg2.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = context.getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                userImageReceivedListItem.setImageDrawable(photoUrl);
            }
        }

        return convertView;
    }
}
