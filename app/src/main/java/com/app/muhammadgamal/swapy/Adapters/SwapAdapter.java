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
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.app.muhammadgamal.swapy.SwapData.SwapOff;
import com.app.muhammadgamal.swapy.SwapData.SwapRequestShift;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SwapAdapter extends ArrayAdapter<SwapDetails> {

    private FirebaseAuth mAuth;
    private String currentUserId, userName;
    private DatabaseReference userDatabaseReference, notificationDB;
    private DatabaseReference swapRequestsDb;
    private SwapRequestShift swapRequestShift;

    private String toID, toLoginID, toName, toShiftDate, toShiftDay, toPhone, toShiftTime, toAccount,
            toCompanyBranch, toEmail, toImageUrl, toPreferredShift;
    private String fromID, fromLoginID, fromName, fromShiftDate, fromShiftDay, fromPhone, fromShiftTime, fromAccount, fromCompanyBranch,
            fromEmail, fromImageUrl, fromPreferredShift;

    private String swapperID, swapperLoginID, currentUserLoginID, swapperPreferredShift,
            swapperTeamLeader, swapperShiftTime, swapShiftDate, swapperShiftDay, swapperImageUrl, swapperAccount,
            swapperCompanyBranch, swapperPhone, swapperEmail, swapperName;

    private  SwapDetails swapBody;


    public SwapAdapter(Context context, int resource, List<SwapDetails> sampleArrayList) {
        super(context, resource, sampleArrayList);
    }

    @Nullable
    @Override
    public SwapDetails getItem(int position) {
        //latest swaps on top of the list
        return super.getItem(getCount() - position - 1);
    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.home_list_item, parent, false);
            Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            convertView.startAnimation(animation);
            lastPosition = position;
        }

        final Context context = convertView.getContext();

         swapBody = getItem(position);
        final Button homeSwapButton = convertView.findViewById(R.id.btnOffSwapList);
        ImageView swapperImage = convertView.findViewById(R.id.swapper_image);
        TextView swapperName = convertView.findViewById(R.id.swapper_name);
        TextView swapperShiftTime = convertView.findViewById(R.id.swapper_shift_time);
        TextView swapperShiftDay = convertView.findViewById(R.id.swapper_shift_day);
        TextView swapperPreferredShift = convertView.findViewById(R.id.swapper_preferred_shift);
        TextView swapperShiftDate = convertView.findViewById(R.id.swapper_shift_date);

        final ProgressBar progressBarListItem = convertView.findViewById(R.id.progressBarOffListItem);
        final ProgressBar progressBarHomeListItemBtn = convertView.findViewById(R.id.progressBarHomeListItemBtn);


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

//        if (homeSwapButton != null) {
//            homeSwapButton.setTag(position);
//            homeSwapButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int ppsition = (Integer) view.getTag();
//                    mAuth = FirebaseAuth.getInstance();
//                    currentUserId = mAuth.getCurrentUser().getUid();
//                    userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
//                    userDatabaseReference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            progressBarHomeListItemBtn.setVisibility(View.VISIBLE);
//                            homeSwapButton.setVisibility(View.GONE);
//                            User user = dataSnapshot.getValue(User.class);
//                            userName = user.getmUsername();
//                            String requestMessage = userName + "" + " wants to swap his shift with your shift";
//                            Map<String, Object> notificationMessage = new HashMap<>();
//                            notificationMessage.put("message", requestMessage);
//                            notificationMessage.put("from", currentUserId);
//
//                            notificationDB.child(swapBody.getSwapperID()).push()
//                                    .setValue(notificationMessage).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(context, "Notification sent", Toast.LENGTH_LONG).show();
//                                        progressBarHomeListItemBtn.setVisibility(View.GONE);
//                                        swapRequestShift();
//                                    }
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
//                                }
//                            });
//
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
////                    int position = (Integer) view.getTag();
////                    Intent intent = new Intent(getContext(), ProfileActivityShift.class);
////                    intent.putExtra("swapper info", position);
////                    context.startActivity(intent);
//                }
//            });
//
//        }
//        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
//        userDb.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//                if (user.getmProfilePhotoURL() != null) {
//                    Glide.with(swapperImage.getContext())
//                            .load(user.getmProfilePhotoURL())
//                            .into(swapperImage);
//                } else {
//                    // set the swapper Image to default if no image provided
//                    Resources resources = context.getResources();
//                    Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
//                    swapperImage.setImageDrawable(photoUrl);
//                }
//                swapperName.setText(user.getmUsername());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });



        return convertView;
    }

    private void swapRequest() {
        toID = swapBody.getSwapperID() ;
        toLoginID = swapBody.getSwapperLoginID();
        toImageUrl = swapBody.getSwapperImageUrl();
        toName = swapBody.getSwapperName();
        toPhone = swapBody.getSwapperPhone();
        toEmail = swapBody.getSwapperEmail();
        toCompanyBranch = swapBody.getSwapperCompanyBranch();
        toAccount = swapBody.getSwapperAccount();
        toShiftDate = swapBody.getSwapShiftDate();
        toShiftDay = swapBody.getSwapperShiftDay();
        toShiftTime = swapBody.getSwapperShiftTime();
        toPreferredShift = swapBody.getSwapperPreferredShift();
        fromID = currentUserId;

        DatabaseReference swapDb = FirebaseDatabase.getInstance().getReference().child("swaps").child("shift_swaps");
        swapDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SwapOff swapDetails = dataSnapshot.getValue(SwapOff.class);
                if (dataSnapshot.exists()) {
                    if (swapDetails.getSwapperID().equals(fromID)) {
                        fromLoginID = swapDetails.getSwapperLoginID();
                        fromImageUrl = swapDetails.getSwapperImageUrl();
                        fromName = swapDetails.getSwapperName();
                        fromPhone = swapDetails.getSwapperPhone();
                        fromEmail = swapDetails.getSwapperEmail();
                        fromCompanyBranch = swapDetails.getSwapperCompanyBranch();
                        fromAccount = swapDetails.getSwapperAccount();
                        fromShiftDate = swapDetails.getSwapShiftDate();
                        fromShiftDay = swapDetails.getSwapperShiftDay();
                        fromShiftTime = swapDetails.getSwapperShiftTime();
                        fromPreferredShift = swapDetails.getSwapperPreferredShift();
                        swapRequestsDb = FirebaseDatabase.getInstance().getReference().child("Swap Requests");
                        swapRequestShift = new SwapRequestShift(toID,
                                toLoginID,
                                toImageUrl,
                                toName,
                                toPhone,
                                toEmail,
                                toCompanyBranch,
                                toAccount,
                                toShiftDate,
                                toShiftDay,
                                toShiftTime,
                                toPreferredShift,
                                fromID,
                                fromLoginID,
                                fromImageUrl,
                                fromName,
                                fromPhone,
                                fromEmail,
                                fromCompanyBranch,
                                fromAccount,
                                fromShiftDate,
                                fromShiftDay,
                                fromShiftTime,
                                fromPreferredShift,
                                -1,
                                -1);
                        swapRequestsDb.push().setValue(swapRequestShift);

                    }
//                    else {
//                        Toast.makeText(ProfileActivityShift.this, " You have to create a swap to be able to send a request", Toast.LENGTH_LONG).show();
//                    }
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }


}
