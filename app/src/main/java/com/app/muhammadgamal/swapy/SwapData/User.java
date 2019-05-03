package com.app.muhammadgamal.swapy.SwapData;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String mUsername,mEmail, mCompany, mBranch, mAccount, mProfilePhotoURL,mCoverPhotoURL, mPhoneNumber;

    public  User(){

    }
    public User(String mUsername,
                String mEmail,
                String mCompany,
                String mBranch,
                String mAccount,
                String mProfilePhotoURL,
                String mPhoneNumber) {
        this.mUsername = mUsername;
        this.mEmail = mEmail;
        this.mCompany = mCompany;
        this.mBranch = mBranch;
        this.mAccount = mAccount;
        this.mProfilePhotoURL = mProfilePhotoURL;
        this.mPhoneNumber = mPhoneNumber;
    }

    public User(String mUsername,
                String mEmail,
                String mCompany,
                String mBranch,
                String mAccount,
                String mProfilePhotoURL,
                String mCoverPhotoURL,
                String mPhoneNumber) {
        this.mUsername = mUsername;
        this.mEmail = mEmail;
        this.mCompany = mCompany;
        this.mBranch = mBranch;
        this.mAccount = mAccount;
        this.mProfilePhotoURL = mProfilePhotoURL;
        this.mCoverPhotoURL = mCoverPhotoURL;
        this.mPhoneNumber = mPhoneNumber;
    }

    protected User(Parcel in) {
        mUsername = in.readString();
        mEmail = in.readString();
        mCompany = in.readString();
        mBranch = in.readString();
        mAccount = in.readString();
        mProfilePhotoURL = in.readString();
        mCoverPhotoURL = in.readString();
        mPhoneNumber = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setmCoverPhotoURL(String mCoverPhotoURL) {
        this.mCoverPhotoURL = mCoverPhotoURL;
    }

    public String getmCoverPhotoURL() {
        return mCoverPhotoURL;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmCompany() {
        return mCompany;
    }

    public void setmCompany(String mCompany) {
        this.mCompany = mCompany;
    }

    public String getmBranch() {
        return mBranch;
    }

    public void setmBranch(String mBranch) {
        this.mBranch = mBranch;
    }

    public String getmAccount() {
        return mAccount;
    }

    public void setmAccount(String mAccount) {
        this.mAccount = mAccount;
    }

    public String getmProfilePhotoURL() {
        return mProfilePhotoURL;
    }

    public void setmProfilePhotoURL(String mProfilePhotoURL) {
        this.mProfilePhotoURL = mProfilePhotoURL;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUsername);
        dest.writeString(mEmail);
        dest.writeString(mCompany);
        dest.writeString(mBranch);
        dest.writeString(mAccount);
        dest.writeString(mProfilePhotoURL);
        dest.writeString(mPhoneNumber);
    }
}
