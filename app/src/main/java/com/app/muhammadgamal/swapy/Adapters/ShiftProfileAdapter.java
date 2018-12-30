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
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class ShiftProfileAdapter extends ArrayAdapter<SwapDetails> {

    private  SwapDetails swapBody;

    public ShiftProfileAdapter(Context context, int resource, List<SwapDetails> sampleArrayList) {
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
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shift_profile_list_item, parent, false);
        }

        final Context context = convertView.getContext();

        swapBody = getItem(position);
        ImageView swapper_imageShiftProfileChooseDialog = convertView.findViewById(R.id.swapper_imageShiftProfileChooseDialog);
        TextView swapper_nameShiftProfileChooseDialog = convertView.findViewById(R.id.swapper_nameShiftProfileChooseDialog);
        TextView swapper_shift_timeShiftProfileChooseDialog = convertView.findViewById(R.id.swapper_shift_timeShiftProfileChooseDialog);
        TextView swapper_shift_dayShiftProfileChooseDialog = convertView.findViewById(R.id.swapper_shift_dayShiftProfileChooseDialog);
        TextView swapper_preferred_shiftShiftProfileChooseDialog = convertView.findViewById(R.id.swapper_preferred_shiftShiftProfileChooseDialog);
        TextView swapper_shift_dateShiftProfileChooseDialog = convertView.findViewById(R.id.swapper_shift_dateShiftProfileChooseDialog);

        final ProgressBar progressBarShiftProfileChooseDialogListItem = convertView.findViewById(R.id.progressBarShiftProfileChooseDialogListItem);

        if (swapBody != null) {

            swapper_shift_timeShiftProfileChooseDialog.setText(swapBody.getSwapperShiftTime());
            swapper_shift_dayShiftProfileChooseDialog.setText(swapBody.getSwapperShiftDay());
            swapper_preferred_shiftShiftProfileChooseDialog.setText(swapBody.getSwapperPreferredShift());
            swapper_shift_dateShiftProfileChooseDialog.setText(swapBody.getSwapShiftDate());
            swapper_nameShiftProfileChooseDialog.setText(swapBody.getSwapperName());
            if (swapBody.getSwapperImageUrl()!= null){
                progressBarShiftProfileChooseDialogListItem.setVisibility(View.VISIBLE);
                Glide.with(swapper_imageShiftProfileChooseDialog.getContext())
                        .load(swapBody.getSwapperImageUrl())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBarShiftProfileChooseDialogListItem.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(swapper_imageShiftProfileChooseDialog);
            }else {
                progressBarShiftProfileChooseDialogListItem.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = context.getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                swapper_imageShiftProfileChooseDialog.setImageDrawable(photoUrl);
            }
        }

        return convertView;

    }

}
