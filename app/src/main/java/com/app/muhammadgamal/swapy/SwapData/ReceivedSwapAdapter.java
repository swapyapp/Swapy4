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

public class ReceivedSwapAdapter extends ArrayAdapter<SwapDetails> {

    public ReceivedSwapAdapter(Context context, int resource, List<SwapDetails> sampleArrayList) {
        super(context, resource, sampleArrayList);
    }

    @Nullable
    @Override
    public SwapDetails getItem(int position) {
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
        SwapDetails receivedSwapBody = getItem(position);

        ImageView ImageReceivedListItem =convertView.findViewById(R.id.ImageReceivedListItem);
        TextView NameReceivedListItem = convertView.findViewById(R.id.NameReceivedListItem);
        TextView ShiftTimeReceivedListItem = convertView.findViewById(R.id.ShiftTimeReceivedListItem);
        TextView shiftDayReceivedListItem = convertView.findViewById(R.id.shiftDayReceivedListItem);
        TextView preferredShiftReceivedListItem = convertView.findViewById(R.id.preferredShiftReceivedListItem);
        TextView shiftDateReceivedListItem = convertView.findViewById(R.id.shiftDateReceivedListItem);
        final ProgressBar progressBarReceivedListItem = convertView.findViewById(R.id.progressBarReceivedListItem);

        if (receivedSwapBody != null){

            NameReceivedListItem.setText(receivedSwapBody.getSwapperName());
            ShiftTimeReceivedListItem.setText(receivedSwapBody.getSwapperShiftTime());
            shiftDayReceivedListItem.setText(receivedSwapBody.getSwapperShiftDay());
            preferredShiftReceivedListItem.setText(receivedSwapBody.getSwapperPreferredShift());
            shiftDateReceivedListItem.setText(receivedSwapBody.getSwapShiftDate());
            if (receivedSwapBody.getSwapperImageUrl() != null){
                progressBarReceivedListItem.setVisibility(View.VISIBLE);
                Glide.with(ImageReceivedListItem.getContext())
                        .load(receivedSwapBody.getSwapperImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBarReceivedListItem.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(ImageReceivedListItem);
            } else {
                progressBarReceivedListItem.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = context.getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                ImageReceivedListItem.setImageDrawable(photoUrl);
            }
        }

        return convertView;
    }
}
