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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapOff;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class SwapOffAdapter extends ArrayAdapter<SwapOff> {

    public SwapOffAdapter(Context context, int resource, List<SwapOff> sampleArrayList) {
        super(context, resource, sampleArrayList);
    }

    @Nullable
    @Override
    public SwapOff getItem(int position) {
        //latest swaps on top of the list
        return super.getItem(getCount() - position - 1);
    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.swap_off_list_item, parent, false);
            Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            convertView.startAnimation(animation);
            lastPosition = position;
        }

        final Context context = convertView.getContext();

        final SwapOff swapBody = getItem(position);
        ImageView swapperImage = convertView.findViewById(R.id.swapper_off_image);
        TextView swapperName = convertView.findViewById(R.id.swapper_name_off_swap);
        TextView swapperOffDay = convertView.findViewById(R.id.swapper_shift_off);
        TextView swapperPreferredOff = convertView.findViewById(R.id.swapper_preferred_shift_off);
        TextView swapperOffDate = convertView.findViewById(R.id.swapper_shift_off_date);
        final ProgressBar progressBarListItem = convertView.findViewById(R.id.progressBarOffListItem);


//        String userId = "";
        if (swapBody != null) {

            swapperOffDay.setText(swapBody.getOffDay());
            swapperPreferredOff.setText(swapBody.getPreferedOff());
            swapperOffDate.setText(swapBody.getSwapOffDate());
            swapperName.setText(swapBody.getSwapperName());
            if (swapBody.getSwapperImageUrl() != null) {
                progressBarListItem.setVisibility(View.VISIBLE);
                Glide.with(swapperImage.getContext())
                        .load(swapBody.getSwapperImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBarListItem.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(swapperImage);
            } else {
                progressBarListItem.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = context.getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                swapperImage.setImageDrawable(photoUrl);
            }
        }

        return convertView;
    }
}

