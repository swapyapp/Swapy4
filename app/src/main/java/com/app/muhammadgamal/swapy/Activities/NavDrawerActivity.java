package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.Fragments.AcceptedShiftSwapFragment;
import com.app.muhammadgamal.swapy.Fragments.AccountFragment;
import com.app.muhammadgamal.swapy.Fragments.HomeFragment;
import com.app.muhammadgamal.swapy.Fragments.OffSwapFragment;
import com.app.muhammadgamal.swapy.Fragments.ReceivedShiftSwapFragment;
import com.app.muhammadgamal.swapy.Fragments.ReceivedSwapsFragment;
import com.app.muhammadgamal.swapy.Fragments.SentShiftSwapFragment;
import com.app.muhammadgamal.swapy.Fragments.SentSwapsFragment;
import com.app.muhammadgamal.swapy.Fragments.SettingsFragment;
import com.app.muhammadgamal.swapy.Fragments.ShiftSwapFragment;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import de.hdodenhof.circleimageview.CircleImageView;

public class NavDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private TextView receivedSwapRequests, sentSwapRequests, acceptedSwapRequests, approvedSwapRequests, navUsername, navUserCompany, navUserCurrentShift;
    private FirebaseAuth mAuth;
    private CircleImageView userNavImage;
    private ImageView coverImageNavbar;
    private ProgressBar progressBarNav;
    public static String currentUserBranch, currentUserAccount;

    private static final String TAG = NavDrawerActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        mAuth = FirebaseAuth.getInstance();



        //to handle the menu item clicks in the navigation drawer
        //the activity must implement OnNavigationItemSelectedListener
        //then override onNavigationItemSelected method
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout =
                navigationView.inflateHeaderView(R.layout.nav_header);
        progressBarNav = (ProgressBar) headerLayout.findViewById(R.id.progressBarNav);
        userNavImage = (CircleImageView) headerLayout.findViewById(R.id.userNavImage);
        navUsername = (TextView) headerLayout.findViewById(R.id.navUsername);
        navUserCompany = (TextView) headerLayout.findViewById(R.id.navUserCompany);
        navUserCurrentShift = (TextView) headerLayout.findViewById(R.id.navUserCurrentShift);
        coverImageNavbar = findViewById(R.id.cover_image_nav_bar);

        //handle the toggle animation
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        userNavImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,
                                new AccountFragment())
                        .addToBackStack(null)
                        .commit();
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        //avoid recreating the fragment when the device is rotated
        if (savedInstanceState == null) {
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.fragment_container,
                            new HomeFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.home);
        }

//        receivedSwapRequests = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
//                findItem(R.id.nav_receivedSwapRequests));
        sentSwapRequests = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_sentSwapRequests));
//        acceptedSwapRequests = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
//                findItem(R.id.nav_acceptedSwapRequests));

        //handle the counter in the nav drawer
        initializeCountDrawer();

        loadUserInfo();


    }

    //load user info in the header of nav drawer
    private void loadUserInfo() {

        final String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        currentUserDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (dataSnapshot.exists()) {
                    if (user.getmProfilePhotoURL() != null) {
                        progressBarNav.setVisibility(View.VISIBLE);
                        Glide.with(getApplicationContext())
                                .load(user.getmProfilePhotoURL())
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        Log.e(TAG, "Load Image from fireBase failed");
                                        progressBarNav.setVisibility(View.GONE);
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                })
                                .into(userNavImage);
                    }
                    if (user.getmCoverPhotoURL() != null) {
                        Glide.with(getApplicationContext())
                                .load(user.getmCoverPhotoURL())
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        Log.e(TAG, "Load Image from fireBase failed");
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                })
                                .into(coverImageNavbar);
                    }

                    navUsername.setText(user.getmUsername());
                    currentUserBranch = user.getmBranch();
                    currentUserAccount = user.getmAccount();
                    navUserCompany.setText(currentUserBranch + ", " + currentUserAccount);
//                    receivedSwapRequests.setText(String.valueOf(user.getmReceivedRequests()));
//                    acceptedSwapRequests.setText(String.valueOf(user.getmAcceptedRequests()));
//                  approvedSwapRequests.setText(String.valueOf(user.getmApprovedRequests()));
                }
                navUsername.setText(user.getmUsername());
                currentUserBranch = user.getmBranch();
                currentUserAccount = user.getmAccount();
                navUserCompany.setText(currentUserBranch + ", " + currentUserAccount);
//                receivedSwapRequests.setText(String.valueOf(user.getmReceivedRequests()))
//                acceptedSwapRequests.setText(String.valueOf(user.getmAcceptedRequests()));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        //fragment container depends on the case result and will contain the specified fragment
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,
                                new HomeFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.nav_account:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,
                                new AccountFragment())
                        .addToBackStack(null)
                        .commit();
                break;
//            case R.id.nav_receivedSwapRequests:
//                getSupportFragmentManager().
//                        beginTransaction().
//                        replace(R.id.fragment_container,
//                                new ReceivedSwapsFragment())
//                        .addToBackStack(null)
//                        .commit();
//                break;
            case R.id.nav_sentSwapRequests:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,
                                new SentSwapsFragment())
                        .addToBackStack(null)
                        .commit();
                break;
//            case R.id.nav_acceptedSwapRequests:
//                getSupportFragmentManager().
//                        beginTransaction().
//                        replace(R.id.fragment_container,
//                                new AcceptedShiftSwapFragment())
//                        .addToBackStack(null)
//                        .commit();
//                break;
            case R.id.nav_settings:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,
                                new SettingsFragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    //This method will initialize the count value in the menu in the navigation drawer
    private void initializeCountDrawer() {
        //Gravity property aligns the text
//        receivedSwapRequests.setGravity(Gravity.CENTER_VERTICAL);
//        receivedSwapRequests.setTypeface(null, Typeface.BOLD);
//        receivedSwapRequests.setTextColor(getResources().getColor(R.color.red));
        //count is added
        sentSwapRequests.setGravity(Gravity.CENTER_VERTICAL);
        sentSwapRequests.setTypeface(null, Typeface.BOLD);
        sentSwapRequests.setTextColor(getResources().getColor(R.color.red));
        //count is added
//        acceptedSwapRequests.setGravity(Gravity.CENTER_VERTICAL);
  //      acceptedSwapRequests.setTypeface(null, Typeface.BOLD);
 //       acceptedSwapRequests.setTextColor(getResources().getColor(R.color.red));
        //count is added
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int count = getSupportFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                super.onBackPressed();
                //additional code
                getSupportFragmentManager().addOnBackStackChangedListener(
                        new FragmentManager.OnBackStackChangedListener() {
                            public void onBackStackChanged() {
                                getSupportFragmentManager().
                                        beginTransaction().
                                        replace(R.id.fragment_container,
                                                new HomeFragment())
                                        .commit();
                            }
                        });
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    protected void onStart() {
        // when updating the value from an API call or SQLite database
        initializeCountDrawer();
        super.onStart();
    }

    @Override
    protected void onResume() {
        // when updating the value from an API call or SQLite database
        initializeCountDrawer();
        super.onResume();
    }

    public void updateStatusBarColor(String color) {// Color must be in hexadecimal fromat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }


//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.search_icon:
//                Toast.makeText(this, "search Icon Click", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.notification_icon:
//                Toast.makeText(this, "noti Icon Click", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
