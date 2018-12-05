package com.app.muhammadgamal.swapy.Activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.Fragments.AcceptedSwapFragment;
import com.app.muhammadgamal.swapy.Fragments.AccountFragment;
import com.app.muhammadgamal.swapy.Fragments.ApprovedSwapFragment;
import com.app.muhammadgamal.swapy.Fragments.HomeFragment;
import com.app.muhammadgamal.swapy.Fragments.ShiftSwapFragment;
import com.app.muhammadgamal.swapy.Fragments.ReceivedSwapFragment;
import com.app.muhammadgamal.swapy.Fragments.SentSwapFragment;
import com.app.muhammadgamal.swapy.Fragments.SettingsFragment;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class NavDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private TextView receivedSwapRequests, sentSwapRequests, acceptedSwapRequests, approvedSwapRequests, navUsername, navUserCompany, navUserCurrentShift;
    private FirebaseAuth mAuth;
    private CircleImageView userNavImage;
    private ProgressBar progressBarNav;
    public static String currentUserBranch, currentUserAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        mAuth = FirebaseAuth.getInstance();

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        //handle the toggle animation
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //avoid recreating the fragment when the device is rotated
        if (savedInstanceState == null) {
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.fragment_container,
                            new HomeFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.home);
        }

        receivedSwapRequests = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_receivedSwapRequests));
        sentSwapRequests = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_sentSwapRequests));
        acceptedSwapRequests = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_acceptedSwapRequests));
        approvedSwapRequests = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_approvedSwapRequests));


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
                        Glide.with(NavDrawerActivity.this)
                                .load(user.getmProfilePhotoURL())
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        progressBarNav.setVisibility(View.GONE);
                                        return false;
                                    }
                                })
                                .into(userNavImage);
                    }
                    navUsername.setText(user.getmUsername());
                    currentUserBranch = user.getmBranch();
                    currentUserAccount = user.getmAccount();
                    navUserCompany.setText(currentUserBranch + ", " + currentUserAccount);
                    navUserCurrentShift.setText("Current Shift: " + user.getmCurrentShift());
                    receivedSwapRequests.setText(String.valueOf(user.getmReceivedRequests()));
                    sentSwapRequests.setText(String.valueOf(user.getmSentRequests()));
                    acceptedSwapRequests.setText(String.valueOf(user.getmAcceptedRequests()));
//                  approvedSwapRequests.setText(String.valueOf(user.getmApprovedRequests()));
                }
                navUsername.setText(user.getmUsername());
                currentUserBranch = user.getmBranch();
                currentUserAccount = user.getmAccount();
                navUserCompany.setText(currentUserBranch + ", " + currentUserAccount);
                navUserCurrentShift.setText("Current Shift: " + user.getmCurrentShift());
                receivedSwapRequests.setText(String.valueOf(user.getmReceivedRequests()));
                sentSwapRequests.setText(String.valueOf(user.getmSentRequests()));
                acceptedSwapRequests.setText(String.valueOf(user.getmAcceptedRequests()));
//                approvedSwapRequests.setText(String.valueOf(user.getmApprovedRequests()));


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
            case R.id.nav_receivedSwapRequests:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,
                                new ReceivedSwapFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.nav_sentSwapRequests:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,
                                new SentSwapFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.nav_acceptedSwapRequests:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,
                                new AcceptedSwapFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,
                                new SettingsFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.nav_approvedSwapRequests:
                getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,
                                new ApprovedSwapFragment())
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
        receivedSwapRequests.setGravity(Gravity.CENTER_VERTICAL);
        receivedSwapRequests.setTypeface(null, Typeface.BOLD);
        receivedSwapRequests.setTextColor(getResources().getColor(R.color.red));
        //count is added
        sentSwapRequests.setGravity(Gravity.CENTER_VERTICAL);
        sentSwapRequests.setTypeface(null, Typeface.BOLD);
        sentSwapRequests.setTextColor(getResources().getColor(R.color.red));
        //count is added
        acceptedSwapRequests.setGravity(Gravity.CENTER_VERTICAL);
        acceptedSwapRequests.setTypeface(null, Typeface.BOLD);
        acceptedSwapRequests.setTextColor(getResources().getColor(R.color.red));
        //count is added
        approvedSwapRequests.setGravity(Gravity.CENTER_VERTICAL);
        approvedSwapRequests.setTypeface(null, Typeface.BOLD);
        approvedSwapRequests.setTextColor(getResources().getColor(R.color.red));
        //count is added
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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
        }
        super.onBackPressed();
        finish();
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
}
