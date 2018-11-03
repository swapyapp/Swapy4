package com.app.muhammadgamal.swapy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Common {

    public static FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    public static String currentUserId = currentUser.getUid();

    public static boolean isNetworkAvailable(Context ctx) {
        NetworkInfo info = getActiveNetworkInfo(ctx);
        return info != null && info.isAvailable();
    }

    public static boolean isWifiAvailable(Context ctx) {
        NetworkInfo info = getActiveNetworkInfo(ctx);
        return info != null && info.isAvailable()
                && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    private static NetworkInfo getActiveNetworkInfo(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info;
    }

}
