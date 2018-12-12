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

public class OffReceivedSwapAdapter extends ArrayAdapter<SwapRequestOff> {

    public OffReceivedSwapAdapter(Context context, int resource, List<SwapRequestOff> sampleArrayList) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.off_received_list_item, parent, false);
        }

        final Context context = convertView.getContext();
        SwapRequestOff receivedSwapBody = getItem(position);

        ImageView offImageReceivedListItem = convertView.findViewById(R.id.offImageReceivedListItem);
        TextView offNameReceivedListItem = convertView.findViewById(R.id.offNameReceivedListItem);
        TextView offDayReceivedListItem = convertView.findViewById(R.id.offDayReceivedListItem);
        TextView offDateReceivedListItem = convertView.findViewById(R.id.offDateReceivedListItem);
        final ProgressBar offProgressBarReceivedListItemImg1 = convertView.findViewById(R.id.offProgressBarReceivedListItemImg1);

        ImageView offUserImageReceivedListItem = convertView.findViewById(R.id.offUserImageReceivedListItem);
        TextView offUserNameReceivedListItem = convertView.findViewById(R.id.offUserNameReceivedListItem);
        TextView userOffDayReceivedListItem = convertView.findViewById(R.id.userOffDayReceivedListItem);
        TextView userOffDateReceivedListItem = convertView.findViewById(R.id.userOffDateReceivedListItem);
        final ProgressBar offProgressBarReceivedListItemImg2 = convertView.findViewById(R.id.offProgressBarReceivedListItemImg2);

        if (receivedSwapBody != null) {

            offNameReceivedListItem.setText(receivedSwapBody.getFromName());
            offDayReceivedListItem.setText(receivedSwapBody.getFromOffDay());
            offDateReceivedListItem.setText(receivedSwapBody.getFromOffDate());
            if (receivedSwapBody.getFromImageUrl() != null) {
                offProgressBarReceivedListItemImg1.setVisibility(View.VISIBLE);
                Glide.with(offImageReceivedListItem.getContext())
                        .load(receivedSwapBody.getFromImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                offProgressBarReceivedListItemImg1.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(offImageReceivedListItem);
            } else {
                offProgressBarReceivedListItemImg1.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = context.getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                offImageReceivedListItem.setImageDrawable(photoUrl);
            }

            offUserNameReceivedListItem.setText(receivedSwapBody.getToName());
            userOffDayReceivedListItem.setText(receivedSwapBody.getToOffDay());
            userOffDateReceivedListItem.setText(receivedSwapBody.getToOffDate());
            if (receivedSwapBody.getToImageUrl() != null) {
                offProgressBarReceivedListItemImg2.setVisibility(View.VISIBLE);
                Glide.with(offUserImageReceivedListItem.getContext())
                        .load(receivedSwapBody.getToImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                offProgressBarReceivedListItemImg2.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(offUserImageReceivedListItem);
            } else {
                offProgressBarReceivedListItemImg2.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = context.getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                offUserImageReceivedListItem.setImageDrawable(photoUrl);
            }
        }

        return convertView;
    }
}
