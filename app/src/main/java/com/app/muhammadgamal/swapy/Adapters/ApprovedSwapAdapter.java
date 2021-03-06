package com.app.muhammadgamal.swapy.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ApprovedSwapAdapter extends ArrayAdapter<SwapRequestShift> {

    public ApprovedSwapAdapter(Context context, int resource, List<SwapRequestShift> sampleArrayList) {
        super(context, resource, sampleArrayList);
    }

    @Nullable
    @Override
    public SwapRequestShift getItem(int position) {
        //latest swaps on top of the list
        return super.getItem(getCount() - position - 1);
    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.approved_list_item, parent, false);
            Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            convertView.startAnimation(animation);
            lastPosition = position;
        }

        final Context context = convertView.getContext();
        SwapRequestShift receivedSwapBody = getItem(position);

        ImageView ImageApprovedListItem =convertView.findViewById(R.id.ImageApprovedListItem);
        TextView NameApprovedItem = convertView.findViewById(R.id.NameApprovedItem);
        TextView ShiftTimeApprovedListItem = convertView.findViewById(R.id.ShiftTimeApprovedListItem);
        TextView shiftDayApprovedListItem = convertView.findViewById(R.id.shiftDayApprovedListItem);
        TextView preferredShiftApprovedListItem = convertView.findViewById(R.id.preferredShiftApprovedListItem);
        TextView shiftDateApprovedListItem = convertView.findViewById(R.id.shiftDateApprovedListItem);
        final ProgressBar progressBarApprovedListItem = convertView.findViewById(R.id.progressBarApprovedListItem);

        if (receivedSwapBody != null){
            //if current user is the sender then show receiver data
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String currentUserId = currentUser.getUid();
            if (receivedSwapBody.getFromID().equals(currentUserId)){

                NameApprovedItem.setText(receivedSwapBody.getToName());
                ShiftTimeApprovedListItem.setText(receivedSwapBody.getToShiftTime());
                shiftDayApprovedListItem.setText(receivedSwapBody.getToShiftDay());
                preferredShiftApprovedListItem.setText(receivedSwapBody.getToPreferredShift());
                shiftDateApprovedListItem.setText(receivedSwapBody.getToShiftDate());
                if (receivedSwapBody.getToImageUrl() != null){
                    progressBarApprovedListItem.setVisibility(View.VISIBLE);
                    Glide.with(ImageApprovedListItem.getContext())
                            .load(receivedSwapBody.getToImageUrl())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBarApprovedListItem.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(ImageApprovedListItem);
                } else {
                    progressBarApprovedListItem.setVisibility(View.GONE);
                    // set the swapper Image to default if no image provided
                    Resources resources = context.getResources();
                    Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                    ImageApprovedListItem.setImageDrawable(photoUrl);
                }

            }

            //if current user is the receiver then show sender data
            if (receivedSwapBody.getToID().equals(currentUserId)){

                NameApprovedItem.setText(receivedSwapBody.getFromName());
                ShiftTimeApprovedListItem.setText(receivedSwapBody.getFromShiftTime());
                shiftDayApprovedListItem.setText(receivedSwapBody.getFromShiftDay());
                preferredShiftApprovedListItem.setText(receivedSwapBody.getFromPreferredShift());
                shiftDateApprovedListItem.setText(receivedSwapBody.getFromShiftDate());
                if (receivedSwapBody.getFromImageUrl() != null){
                    progressBarApprovedListItem.setVisibility(View.VISIBLE);
                    Glide.with(ImageApprovedListItem.getContext())
                            .load(receivedSwapBody.getFromImageUrl())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBarApprovedListItem.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(ImageApprovedListItem);
                } else {
                    progressBarApprovedListItem.setVisibility(View.GONE);
                    // set the swapper Image to default if no image provided
                    Resources resources = context.getResources();
                    Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                    ImageApprovedListItem.setImageDrawable(photoUrl);
                }

            }

        }

        return  convertView;
    }


}
