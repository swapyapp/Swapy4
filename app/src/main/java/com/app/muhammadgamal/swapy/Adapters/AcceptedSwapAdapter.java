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

import com.app.muhammadgamal.swapy.Common;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapRequest;
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
        TextView shiftDateAcceptedListItem = convertView.findViewById(R.id.shiftDateAcceptedListItem);
        final ProgressBar progressBarAcceptedListItemImg1 = convertView.findViewById(R.id.progressBarAcceptedListItemImg1);

        ImageView userImageAcceptedListItem =convertView.findViewById(R.id.userImageAcceptedListItem);
        TextView UserNameAcceptedItem = convertView.findViewById(R.id.UserNameAcceptedItem);
        TextView userShiftTimeAcceptedListItem = convertView.findViewById(R.id.userShiftTimeAcceptedListItem);
        TextView userShiftDayAcceptedListItem = convertView.findViewById(R.id.userShiftDayAcceptedListItem);
        TextView userShiftDateAcceptedListItem = convertView.findViewById(R.id.userShiftDateAcceptedListItem);
        final ProgressBar progressBarAcceptedListItemImg2 = convertView.findViewById(R.id.progressBarAcceptedListItemImg2);

        if (receivedSwapBody != null){
            //if current user is the sender then show receiver data
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String currentUserId = currentUser.getUid();
            if (receivedSwapBody.getFromID().equals(currentUserId)){

                NameAcceptedItem.setText(receivedSwapBody.getToName());
                ShiftTimeAcceptedListItem.setText(receivedSwapBody.getToShiftTime());
                shiftDayAcceptedListItem.setText(receivedSwapBody.getToShiftDay());
                shiftDateAcceptedListItem.setText(receivedSwapBody.getToShiftDate());
                if (receivedSwapBody.getToImageUrl() != null){
                    progressBarAcceptedListItemImg1.setVisibility(View.VISIBLE);
                    Glide.with(ImageAcceptedListItem.getContext())
                            .load(receivedSwapBody.getToImageUrl())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBarAcceptedListItemImg1.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(ImageAcceptedListItem);
                } else {
                    progressBarAcceptedListItemImg1.setVisibility(View.GONE);
                    // set the swapper Image to default if no image provided
                    Resources resources = context.getResources();
                    Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                    ImageAcceptedListItem.setImageDrawable(photoUrl);
                }

                UserNameAcceptedItem.setText(receivedSwapBody.getFromName());
                userShiftTimeAcceptedListItem.setText(receivedSwapBody.getFromShiftTime());
                userShiftDayAcceptedListItem.setText(receivedSwapBody.getFromShiftDay());
                userShiftDateAcceptedListItem.setText(receivedSwapBody.getFromShiftDate());
                if (receivedSwapBody.getFromImageUrl() != null){
                    progressBarAcceptedListItemImg2.setVisibility(View.VISIBLE);
                    Glide.with(userImageAcceptedListItem.getContext())
                            .load(receivedSwapBody.getFromImageUrl())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBarAcceptedListItemImg2.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(userImageAcceptedListItem);
                } else {
                    progressBarAcceptedListItemImg2.setVisibility(View.GONE);
                    // set the swapper Image to default if no image provided
                    Resources resources = context.getResources();
                    Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                    userImageAcceptedListItem.setImageDrawable(photoUrl);
                }

            }

            //if current user is the receiver then show sender data
            if (receivedSwapBody.getToID().equals(currentUserId)){

                NameAcceptedItem.setText(receivedSwapBody.getFromName());
                ShiftTimeAcceptedListItem.setText(receivedSwapBody.getFromShiftTime());
                shiftDayAcceptedListItem.setText(receivedSwapBody.getFromShiftDay());
                shiftDateAcceptedListItem.setText(receivedSwapBody.getFromShiftDate());
                if (receivedSwapBody.getFromImageUrl() != null){
                    progressBarAcceptedListItemImg1.setVisibility(View.VISIBLE);
                    Glide.with(ImageAcceptedListItem.getContext())
                            .load(receivedSwapBody.getFromImageUrl())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBarAcceptedListItemImg1.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(ImageAcceptedListItem);
                } else {
                    progressBarAcceptedListItemImg1.setVisibility(View.GONE);
                    // set the swapper Image to default if no image provided
                    Resources resources = context.getResources();
                    Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                    ImageAcceptedListItem.setImageDrawable(photoUrl);
                }

                UserNameAcceptedItem.setText(receivedSwapBody.getToName());
                userShiftTimeAcceptedListItem.setText(receivedSwapBody.getToShiftTime());
                userShiftDayAcceptedListItem.setText(receivedSwapBody.getToShiftDay());
                userShiftDateAcceptedListItem.setText(receivedSwapBody.getToShiftDate());
                if (receivedSwapBody.getToImageUrl() != null){
                    progressBarAcceptedListItemImg2.setVisibility(View.VISIBLE);
                    Glide.with(userImageAcceptedListItem.getContext())
                            .load(receivedSwapBody.getToImageUrl())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBarAcceptedListItemImg2.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(userImageAcceptedListItem);
                } else {
                    progressBarAcceptedListItemImg2.setVisibility(View.GONE);
                    // set the swapper Image to default if no image provided
                    Resources resources = context.getResources();
                    Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                    userImageAcceptedListItem.setImageDrawable(photoUrl);
                }

            }

        }


        return convertView;
    }

}
