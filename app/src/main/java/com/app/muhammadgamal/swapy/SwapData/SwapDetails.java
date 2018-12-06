package com.app.muhammadgamal.swapy.SwapData;

import android.os.Parcel;
import android.os.Parcelable;

public class SwapDetails implements Parcelable {

    private String swapperImageUrl;
    private String swapperName;
    private String swapperEmail;
    private String swapperCompanyBranch;
    private String swapperAccount;
    private String swapperTl;
    private String swapperShiftTime;
    private String swapperShiftDay;
    private String swapperPreferredShift;
    private String swapperPhone;
    private String swapShiftDate;
    private String swapperTeamLeader;
    private String swapperID;
    private String swapperLoginID;


    public SwapDetails() {
    }

    public SwapDetails(String swapperID,
                       String swapperName,
                       String swapperEmail,
                       String swapperPhone,
                       String swapperCompanyBranch,
                       String swapperAccount,
                       String swapperImageUrl) {

        this.swapperID = swapperID;
        this.swapperName = swapperName;
        this.swapperImageUrl = swapperImageUrl;
        this.swapperEmail = swapperEmail;
        this.swapperPhone = swapperPhone;
        this.swapperCompanyBranch = swapperCompanyBranch;
        this.swapperAccount = swapperAccount;
    }

//    public SwapDetails(String swapperImageUrl, String swapperTl, String swapperShiftTime, String swapperShiftDay,
//                       String swapperPreferredShift, String swapperPhone, String swapShiftDate) {
//        this.swapperImageUrl = swapperImageUrl;
//        this.swapperTl = swapperTl;
//        this.swapperShiftTime = swapperShiftTime;
//        this.swapperShiftDay = swapperShiftDay;
//        this.swapperPreferredShift = swapperPreferredShift;
//        this.swapperPhone = swapperPhone;
//        this.swapShiftDate = swapShiftDate;
//    }

    public SwapDetails(String swapperID,
                       String swapperName,
                       String swapperEmail,
                       String swapperPhone,
                       String swapperCompanyBranch,
                       String swapperAccount,
                       String swapperImageUrl,
                       String swapperShiftDay,
                       String swapShiftDate,
                       String swapperShiftTime,
                       String swapperPreferredShift,
                       String swapperLoginID) {
        this.swapperLoginID = swapperLoginID;
        this.swapperShiftTime = swapperShiftTime;
        this.swapperShiftDay = swapperShiftDay;
        this.swapperPreferredShift = swapperPreferredShift;
        this.swapShiftDate = swapShiftDate;
        this.swapperTeamLeader = swapperTeamLeader;
        this.swapperID = swapperID;
        this.swapperName = swapperName;
        this.swapperImageUrl = swapperImageUrl;
        this.swapperEmail = swapperEmail;
        this.swapperPhone = swapperPhone;
        this.swapperCompanyBranch = swapperCompanyBranch;
        this.swapperAccount = swapperAccount;
    }


    protected SwapDetails(Parcel in) {
        swapperImageUrl = in.readString();
        swapperName = in.readString();
        swapperEmail = in.readString();
        swapperCompanyBranch = in.readString();
        swapperAccount = in.readString();
        swapperTl = in.readString();
        swapperShiftTime = in.readString();
        swapperShiftDay = in.readString();
        swapperPreferredShift = in.readString();
        swapperPhone = in.readString();
        swapShiftDate = in.readString();
        swapperTeamLeader = in.readString();
        swapperID = in.readString();
        swapperLoginID = in.readString();
    }

    public static final Creator<SwapDetails> CREATOR = new Creator<SwapDetails>() {
        @Override
        public SwapDetails createFromParcel(Parcel in) {
            return new SwapDetails(in);
        }

        @Override
        public SwapDetails[] newArray(int size) {
            return new SwapDetails[size];
        }
    };


    public String getSwapperID() {
        return swapperID;
    }

    public void setSwapperID(String swapperID) {
        this.swapperID = swapperID;
    }

    public String getSwapperImageUrl() {
        return swapperImageUrl;
    }

    public void setSwapperImageUrl(String swapperImageUrl) {
        this.swapperImageUrl = swapperImageUrl;
    }

    public String getSwapperTl() {
        return swapperTl;
    }

    public void setSwapperTl(String swapperTl) {
        this.swapperTl = swapperTl;
    }

    public String getSwapperShiftTime() {
        return swapperShiftTime;
    }

    public void setSwapperShiftTime(String swapperShiftTime) {
        this.swapperShiftTime = swapperShiftTime;
    }

    public String getSwapperShiftDay() {
        return swapperShiftDay;
    }

    public void setSwapperShiftDay(String swapperShiftDay) {
        this.swapperShiftDay = swapperShiftDay;
    }

    public String getSwapperPreferredShift() {
        return swapperPreferredShift;
    }

    public void setSwapperPreferredShift(String swapperPreferredShift) {
        this.swapperPreferredShift = swapperPreferredShift;
    }

    public String getSwapperPhone() {
        return swapperPhone;
    }

    public void setSwapperPhone(String swapperPhone) {
        this.swapperPhone = swapperPhone;
    }

    public String getSwapShiftDate() {
        return swapShiftDate;
    }

    public void setSwapShiftDate(String swapShiftDate) {
        this.swapShiftDate = swapShiftDate;
    }

    public String getSwapperTeamLeader() {
        return swapperTeamLeader;
    }

    public void setSwapperTeamLeader(String swapperTeamLeader) {
        this.swapperTeamLeader = swapperTeamLeader;
    }

    public String getSwapperName() {
        return swapperName;
    }

    public void setSwapperName(String swapperName) {
        this.swapperName = swapperName;
    }

    public String getSwapperEmail() {
        return swapperEmail;
    }

    public void setSwapperEmail(String swapperEmail) {
        this.swapperEmail = swapperEmail;
    }


    public String getSwapperCompanyBranch() {
        return swapperCompanyBranch;
    }

    public void setSwapperCompanyBranch(String swapperCompanyBranch) {
        this.swapperCompanyBranch = swapperCompanyBranch;
    }

    public String getSwapperAccount() {
        return swapperAccount;
    }

    public void setSwapperAccount(String swapperAccount) {
        this.swapperAccount = swapperAccount;
    }

    public String getSwapperLoginID() {
        return swapperLoginID;
    }

    public void setSwapperLoginID(String swapperLoginID) {
        this.swapperLoginID = swapperLoginID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(swapperImageUrl);
        parcel.writeString(swapperName);
        parcel.writeString(swapperEmail);
        parcel.writeString(swapperCompanyBranch);
        parcel.writeString(swapperAccount);
        parcel.writeString(swapperTl);
        parcel.writeString(swapperShiftTime);
        parcel.writeString(swapperShiftDay);
        parcel.writeString(swapperPreferredShift);
        parcel.writeString(swapperPhone);
        parcel.writeString(swapShiftDate);
        parcel.writeString(swapperTeamLeader);
        parcel.writeString(swapperID);
        parcel.writeString(swapperLoginID);
    }
}
