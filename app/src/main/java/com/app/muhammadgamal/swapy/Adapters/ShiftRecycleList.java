package com.app.muhammadgamal.swapy.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestShift;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class ShiftRecycleList extends RecyclerView.ViewHolder {

    private FirebaseAuth mAuth;
    private String currentUserId, userName;
    private DatabaseReference userDatabaseReference, notificationDB;
    private DatabaseReference swapRequestsDb;
    private SwapRequestShift swapRequestShift;
    private final ProgressBar progressBarListItem;
    ImageView swapperImage;
    TextView swapperName;
    TextView swapperShiftTime;
    TextView swapperShiftDay;
    TextView swapperPreferredShift;
    TextView swapperShiftDate;
    private String toID, toLoginID, toName, toShiftDate, toShiftDay, toPhone, toShiftTime, toAccount,
            toCompanyBranch, toEmail, toImageUrl, toPreferredShift;
    private String fromID, fromLoginID, fromName, fromShiftDate, fromShiftDay, fromPhone, fromShiftTime, fromAccount, fromCompanyBranch,
            fromEmail, fromImageUrl, fromPreferredShift;

    private String swapperID, swapperLoginID, currentUserLoginID, swapperPreferredShiftS,
            swapperTeamLeader, swapperShiftTimeS, swapShiftDate, swapperShiftDayS, swapperImageUrl, swapperAccount,
            swapperCompanyBranch, swapperPhone, swapperEmail, swapperNameS;

    private SwapDetails swapBody;
    private Context context;
    private User user;


    public ShiftRecycleList(Context context, View itemView) {
        super(itemView);

        this.context = context;

        swapperImage = itemView.findViewById(R.id.swapper_image);
         swapperName = itemView.findViewById(R.id.swapper_name);
         swapperShiftTime = itemView.findViewById(R.id.swapper_shift_time);
         swapperShiftDay = itemView.findViewById(R.id.swapper_shift_day);
         swapperPreferredShift = itemView.findViewById(R.id.swapper_preferred_shift);
         swapperShiftDate = itemView.findViewById(R.id.swapper_shift_date);

        progressBarListItem = itemView.findViewById(R.id.progressBarOffListItem);
        // 3. Set the "onClick" listener of the holder
        itemView.setOnClickListener((View.OnClickListener) this);
    }

    public void bindSwapDetails (SwapDetails swapBody){
        this.swapBody = swapBody;

//        String userId = "";
        if (swapBody != null) {

            swapperShiftTime.setText(swapBody.getSwapperShiftTime());
            swapperShiftDay.setText(swapBody.getSwapperShiftDay());
            swapperPreferredShift.setText(swapBody.getSwapperPreferredShift());
            swapperShiftDate.setText(swapBody.getSwapShiftDate());
            swapperName.setText(swapBody.getSwapperName());
            if (swapBody.getSwapperImageUrl()!= null){
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
            }else {
                progressBarListItem.setVisibility(View.GONE);
                // set the swapper Image to default if no image provided
                Resources resources = context.getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                swapperImage.setImageDrawable(photoUrl);
            }
//            userId = swapBody.getSwapperID();
        }
    }


}
