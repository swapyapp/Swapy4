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

import com.app.muhammadgamal.swapy.Common;
import com.app.muhammadgamal.swapy.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AcceptedSwapAdapter extends ArrayAdapter<SwapRequest> {

    public AcceptedSwapAdapter(Context context, int resource, List<SwapRequest> sampleArrayList) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accepted_list_item, parent, false);
        }

        final Context context = convertView.getContext();
        SwapRequest receivedSwapBody = getItem(position);

        ImageView ImageAcceptedListItem =convertView.findViewById(R.id.ImageAcceptedListItem);
        TextView NameAcceptedItem = convertView.findViewById(R.id.NameAcceptedItem);
        TextView ShiftTimeAcceptedListItem = convertView.findViewById(R.id.ShiftTimeAcceptedListItem);
        TextView shiftDayAcceptedListItem = convertView.findViewById(R.id.shiftDayAcceptedListItem);
        TextView preferredShiftAcceptedListItem = convertView.findViewById(R.id.preferredShiftAcceptedListItem);
        TextView shiftDateAcceptedListItem = convertView.findViewById(R.id.shiftDateAcceptedListItem);
        final ProgressBar progressBarAcceptedListItem = convertView.findViewById(R.id.progressBarAcceptedListItem);

        if (receivedSwapBody != null){
            //if current user is the sender then show receiver data
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String currentUserId = currentUser.getUid();
            if (receivedSwapBody.getFromID().equals(currentUserId)){

                NameAcceptedItem.setText(receivedSwapBody.getToName());
                ShiftTimeAcceptedListItem.setText(receivedSwapBody.getToShiftTime());
                shiftDayAcceptedListItem.setText(receivedSwapBody.getToShiftDay());
                preferredShiftAcceptedListItem.setText(receivedSwapBody.getToPreferredShift());
                shiftDateAcceptedListItem.setText(receivedSwapBody.getToShiftDate());
                if (receivedSwapBody.getToImageUrl() != null){
                    progressBarAcceptedListItem.setVisibility(View.VISIBLE);
                    Glide.with(ImageAcceptedListItem.getContext())
                            .load(receivedSwapBody.getToImageUrl())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBarAcceptedListItem.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(ImageAcceptedListItem);
                } else {
                    progressBarAcceptedListItem.setVisibility(View.GONE);
                    // set the swapper Image to default if no image provided
                    Resources resources = context.getResources();
                    Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                    ImageAcceptedListItem.setImageDrawable(photoUrl);
                }

            }

            //if current user is the receiver then show sender data
            if (receivedSwapBody.getToID().equals(currentUserId)){

                NameAcceptedItem.setText(receivedSwapBody.getFromName());
                ShiftTimeAcceptedListItem.setText(receivedSwapBody.getFromShiftTime());
                shiftDayAcceptedListItem.setText(receivedSwapBody.getFromShiftDay());
                preferredShiftAcceptedListItem.setText(receivedSwapBody.getFromPreferredShift());
                shiftDateAcceptedListItem.setText(receivedSwapBody.getFromShiftDate());
                if (receivedSwapBody.getFromImageUrl() != null){
                    progressBarAcceptedListItem.setVisibility(View.VISIBLE);
                    Glide.with(ImageAcceptedListItem.getContext())
                            .load(receivedSwapBody.getFromImageUrl())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBarAcceptedListItem.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(ImageAcceptedListItem);
                } else {
                    progressBarAcceptedListItem.setVisibility(View.GONE);
                    // set the swapper Image to default if no image provided
                    Resources resources = context.getResources();
                    Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                    ImageAcceptedListItem.setImageDrawable(photoUrl);
                }

            }

        }


        return convertView;
    }

}
