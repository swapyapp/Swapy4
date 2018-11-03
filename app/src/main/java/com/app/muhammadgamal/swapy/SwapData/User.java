package com.app.muhammadgamal.swapy.SwapData;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String mUsername,mEmail, mCompany, mBranch, mAccount, mCurrentShift, mProfilePhotoURL, mPhoneNumber, mLoginID;
    private int mSentRequests, mReceivedRequests, mAcceptedRequests;

    public  User(){

    }

    public User(String username,
                String email,
                String loginID,
                String phoneNumber,
                String company,
                String branch,
                String account,
                String currentShift,
                String profilePhotoURL,
                int sentRequests,
                int receivedRequests,
                int acceptedRequests){
        mLoginID = loginID;
        mUsername = username;
        mEmail = email;
        mPhoneNumber = phoneNumber;
        mCompany = company;
        mBranch = branch;
        mAccount = account;
        mCurrentShift = currentShift;
        mProfilePhotoURL = profilePhotoURL;
        mSentRequests = sentRequests;
        mReceivedRequests = receivedRequests;
        mAcceptedRequests = acceptedRequests;
    }

    protected User(Parcel in) {
        mUsername = in.readString();
        mEmail = in.readString();
        mLoginID = in.readString();
        mCompany = in.readString();
        mBranch = in.readString();
        mAccount = in.readString();
        mCurrentShift = in.readString();
        mProfilePhotoURL = in.readString();
        mPhoneNumber = in.readString();
        mSentRequests = in.readInt();
        mReceivedRequests = in.readInt();
        mAcceptedRequests = in.readInt();
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

    public String getmLoginID() {
        return mLoginID;
    }

    public void setmLoginID(String mLoginID) {
        this.mLoginID = mLoginID;
    }

    public String getmUsername() {
        return mUsername;
    }

    public String getmCompany() {
        return mCompany;
    }

    public String getmBranch() {
        return mBranch;
    }

    public String getmAccount() {
        return mAccount;
    }

    public String getmCurrentShift() {
        return mCurrentShift;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public String getmProfilePhotoURL() {
        return mProfilePhotoURL;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public void setmCompany(String mCompany) {
        this.mCompany = mCompany;
    }

    public void setmBranch(String mBranch) {
        this.mBranch = mBranch;
    }

    public void setmAccount(String mAccount) {
        this.mAccount = mAccount;
    }

    public void setmCurrentShift(String mCurrentShift) {
        this.mCurrentShift = mCurrentShift;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public void setmProfilePhotoURL(String mProfilePhotoURL) {
        this.mProfilePhotoURL = mProfilePhotoURL;
    }

    public int getmSentRequests() {
        return mSentRequests;
    }

    public void setmSentRequests(int mSentRequests) {
        this.mSentRequests = mSentRequests;
    }

    public int getmReceivedRequests() {
        return mReceivedRequests;
    }

    public void setmReceivedRequests(int mReceivedRequests) {
        this.mReceivedRequests = mReceivedRequests;
    }

    public int getmAcceptedRequests() {
        return mAcceptedRequests;
    }

    public void setmAcceptedRequests(int mAcceptedRequests) {
        this.mAcceptedRequests = mAcceptedRequests;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUsername);
        parcel.writeString(mEmail);
        parcel.writeString(mCompany);
        parcel.writeString(mBranch);
        parcel.writeString(mAccount);
        parcel.writeString(mCurrentShift);
        parcel.writeString(mProfilePhotoURL);
        parcel.writeString(mPhoneNumber);
        parcel.writeInt(mSentRequests);
        parcel.writeInt(mReceivedRequests);
        parcel.writeInt(mAcceptedRequests);
    }
}
