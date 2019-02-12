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
import com.app.muhammadgamal.swapy.SwapData.SwapRequestOff;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestShift;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class OffAcceptedSwapAdapter extends ArrayAdapter<SwapRequestOff> {

    public OffAcceptedSwapAdapter(Context context, int resource, List<SwapRequestOff> sampleArrayList) {
        super(context, resource, sampleArrayList);
    }

    @Nullable
    @Override
    public SwapRequestOff getItem(int position) {
        //latest swaps on top of the list
        return super.getItem(getCount() - position - 1);
    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.off_accepted_list_item, parent, false);
            Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            convertView.startAnimation(animation);
            lastPosition = position;
        }

        final Context context = convertView.getContext();
        SwapRequestOff receivedSwapBody = getItem(position);

        ImageView offImageAcceptedListItem = convertView.findViewById(R.id.offImageAcceptedListItem);
        TextView offNameAcceptedItem = convertView.findViewById(R.id.offNameAcceptedItem);
        TextView offDayAcceptedListItem = convertView.findViewById(R.id.offDayAcceptedListItem);
        TextView offDateAcceptedListItem = convertView.findViewById(R.id.offDateAcceptedListItem);
        final ProgressBar offProgressBarAcceptedListItemImg1 = convertView.findViewById(R.id.offProgressBarAcceptedListItemImg1);

        ImageView offUserImageAcceptedListItem = convertView.findViewById(R.id.offUserImageAcceptedListItem);
        TextView offUserNameAcceptedItem = convertView.findViewById(R.id.offUserNameAcceptedItem);
        TextView userOffDayAcceptedListItem = convertView.findViewById(R.id.userOffDayAcceptedListItem);
        TextView userOffDateAcceptedListItem = convertView.findViewById(R.id.userOffDateAcceptedListItem);
        final ProgressBar offProgressBarAcceptedListItemImg2 = convertView.findViewById(R.id.offProgressBarAcceptedListItemImg2);

        if (receivedSwapBody != null) {
            //if current user is the sender then show receiver data
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String currentUserId = currentUser.getUid();
            if (receivedSwapBody.getFromID().equals(currentUserId)) {

                offNameAcceptedItem.setText(receivedSwapBody.getToName());
                offDayAcceptedListItem.setText(receivedSwapBody.getToOffDay());
                offDateAcceptedListItem.setText(receivedSwapBody.getToOffDate());
                if (receivedSwapBody.getToImageUrl() != null) {
                    offProgressBarAcceptedListItemImg1.setVisibility(View.VISIBLE);
                    Glide.with(offImageAcceptedListItem.getContext())
                            .load(receivedSwapBody.getToImageUrl())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    offProgressBarAcceptedListItemImg1.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(offImageAcceptedListItem);
                } else {
                    offProgressBarAcceptedListItemImg1.setVisibility(View.GONE);
                    // set the swapper Image to default if no image provided
                    Resources resources = context.getResources();
                    Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                    offImageAcceptedListItem.setImageDrawable(photoUrl);
                }

                offUserNameAcceptedItem.setText(receivedSwapBody.getFromName());
                userOffDayAcceptedListItem.setText(receivedSwapBody.getFromOffDay());
                userOffDateAcceptedListItem.setText(receivedSwapBody.getFromOffDate());
                if (receivedSwapBody.getFromImageUrl() != null) {
                    offProgressBarAcceptedListItemImg2.setVisibility(View.VISIBLE);
                    Glide.with(offUserImageAcceptedListItem.getContext())
                            .load(receivedSwapBody.getFromImageUrl())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    offProgressBarAcceptedListItemImg2.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(offUserImageAcceptedListItem);
                } else {
                    offProgressBarAcceptedListItemImg2.setVisibility(View.GONE);
                    // set the swapper Image to default if no image provided
                    Resources resources = context.getResources();
                    Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                    offUserImageAcceptedListItem.setImageDrawable(photoUrl);
                }

            }

            //if current user is the receiver then show sender data
            if (receivedSwapBody.getToID().equals(currentUserId)) {

                offNameAcceptedItem.setText(receivedSwapBody.getFromName());
                offDayAcceptedListItem.setText(receivedSwapBody.getFromOffDay());
                offDateAcceptedListItem.setText(receivedSwapBody.getFromOffDate());
                if (receivedSwapBody.getFromImageUrl() != null) {
                    offProgressBarAcceptedListItemImg1.setVisibility(View.VISIBLE);
                    Glide.with(offImageAcceptedListItem.getContext())
                            .load(receivedSwapBody.getFromImageUrl())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    offProgressBarAcceptedListItemImg1.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(offImageAcceptedListItem);
                } else {
                    offProgressBarAcceptedListItemImg1.setVisibility(View.GONE);
                    // set the swapper Image to default if no image provided
                    Resources resources = context.getResources();
                    Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                    offImageAcceptedListItem.setImageDrawable(photoUrl);
                }

                offUserNameAcceptedItem.setText(receivedSwapBody.getToName());
                userOffDayAcceptedListItem.setText(receivedSwapBody.getToOffDay());
                userOffDateAcceptedListItem.setText(receivedSwapBody.getToOffDate());
                if (receivedSwapBody.getToImageUrl() != null) {
                    offProgressBarAcceptedListItemImg2.setVisibility(View.VISIBLE);
                    Glide.with(offUserImageAcceptedListItem.getContext())
                            .load(receivedSwapBody.getToImageUrl())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    offProgressBarAcceptedListItemImg2.setVisibility(View.GONE);
                                    return false;
                                }
                            }).into(offUserImageAcceptedListItem);
                } else {
                    offProgressBarAcceptedListItemImg2.setVisibility(View.GONE);
                    // set the swapper Image to default if no image provided
                    Resources resources = context.getResources();
                    Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                    offUserImageAcceptedListItem.setImageDrawable(photoUrl);
                }

            }

        }


        return convertView;
    }

}
