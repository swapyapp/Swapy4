package com.app.muhammadgamal.swapy.SwapData;

import android.os.Parcel;
import android.os.Parcelable;

public class SwapRequestShift implements Parcelable {

    private String toID, toName, toShiftDate, toShiftDay, toPhone, toShiftTime, toAccount, toCompanyBranch, toEmail, toImageUrl, toPreferredShift;
    private String fromID, fromName, fromShiftDate, fromShiftDay, fromPhone, fromShiftTime, fromAccount, fromCompanyBranch, fromEmail, fromImageUrl, fromPreferredShift;
    private int accepted, approved; //true = 1, false = 0, waiting = -1

    public SwapRequestShift() {
    }

    public SwapRequestShift(String toID,
                            String toImageUrl,
                            String toName,
                            String toPhone,
                            String toEmail,
                            String toCompanyBranch,
                            String toAccount,
                            String toShiftDate,
                            String toShiftDay,
                            String toShiftTime,
                            String toPreferredShift,
                            String fromID,
                            String fromImageUrl,
                            String fromName,
                            String fromPhone,
                            String fromEmail,
                            String fromCompanyBranch,
                            String fromAccount,
                            String fromShiftDate,
                            String fromShiftDay,
                            String fromShiftTime,
                            String fromPreferredShift,
                            int accepted,
                            int approved) {
        this.toID = toID;
        this.toImageUrl = toImageUrl;
        this.toName = toName;
        this.toPhone = toPhone;
        this.toEmail = toEmail;
        this.toCompanyBranch = toCompanyBranch;
        this.toAccount = toAccount;
        this.toShiftDate = toShiftDate;
        this.toShiftDay = toShiftDay;
        this.toShiftTime = toShiftTime;
        this.toPreferredShift = toPreferredShift;
        this.fromID = fromID;
        this.fromImageUrl = fromImageUrl;
        this.fromName = fromName;
        this.fromPhone = fromPhone;
        this.fromEmail = fromEmail;
        this.fromCompanyBranch = fromCompanyBranch;
        this.fromAccount = fromAccount;
        this.fromShiftDate = fromShiftDate;
        this.fromShiftDay = fromShiftDay;
        this.fromShiftTime = fromShiftTime;
        this.fromPreferredShift = fromPreferredShift;
        this.accepted = accepted;
        this.approved = approved;
    }

    public SwapRequestShift(String toID,
                            String fromID,
                            int accepted,
                            int approved) {
        this.toID = toID;
        this.fromID = fromID;
        this.accepted = accepted;
        this.approved = approved;
    }

    protected SwapRequestShift(Parcel in) {
        toID = in.readString();
        toName = in.readString();
        toShiftDate = in.readString();
        toShiftDay = in.readString();
        toPhone = in.readString();
        toShiftTime = in.readString();
        toAccount = in.readString();
        toCompanyBranch = in.readString();
        toEmail = in.readString();
        toImageUrl = in.readString();
        toPreferredShift = in.readString();
        fromID = in.readString();
        fromName = in.readString();
        fromShiftDate = in.readString();
        fromShiftDay = in.readString();
        fromPhone = in.readString();
        fromShiftTime = in.readString();
        fromAccount = in.readString();
        fromCompanyBranch = in.readString();
        fromEmail = in.readString();
        fromImageUrl = in.readString();
        fromPreferredShift = in.readString();
        accepted = in.readInt();
        approved = in.readInt();
    }

    public static final Creator<SwapRequestShift> CREATOR = new Creator<SwapRequestShift>() {
        @Override
        public SwapRequestShift createFromParcel(Parcel in) {
            return new SwapRequestShift(in);
        }

        @Override
        public SwapRequestShift[] newArray(int size) {
            return new SwapRequestShift[size];
        }
    };

    public String getToID() {
        return toID;
    }

    public void setToID(String toID) {
        this.toID = toID;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getToShiftDate() {
        return toShiftDate;
    }

    public void setToShiftDate(String toShiftDate) {
        this.toShiftDate = toShiftDate;
    }

    public String getToShiftDay() {
        return toShiftDay;
    }

    public void setToShiftDay(String toShiftDay) {
        this.toShiftDay = toShiftDay;
    }

    public String getToPhone() {
        return toPhone;
    }

    public void setToPhone(String toPhone) {
        this.toPhone = toPhone;
    }

    public String getToShiftTime() {
        return toShiftTime;
    }

    public void setToShiftTime(String toShiftTime) {
        this.toShiftTime = toShiftTime;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getToCompanyBranch() {
        return toCompanyBranch;
    }

    public void setToCompanyBranch(String toCompanyBranch) {
        this.toCompanyBranch = toCompanyBranch;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getToImageUrl() {
        return toImageUrl;
    }

    public void setToImageUrl(String toImageUrl) {
        this.toImageUrl = toImageUrl;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromShiftDate() {
        return fromShiftDate;
    }

    public void setFromShiftDate(String fromShiftDate) {
        this.fromShiftDate = fromShiftDate;
    }

    public String getFromShiftDay() {
        return fromShiftDay;
    }

    public void setFromShiftDay(String fromShiftDay) {
        this.fromShiftDay = fromShiftDay;
    }

    public String getFromPhone() {
        return fromPhone;
    }

    public void setFromPhone(String fromPhone) {
        this.fromPhone = fromPhone;
    }

    public String getFromShiftTime() {
        return fromShiftTime;
    }

    public void setFromShiftTime(String fromShiftTime) {
        this.fromShiftTime = fromShiftTime;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getFromCompanyBranch() {
        return fromCompanyBranch;
    }

    public void setFromCompanyBranch(String fromCompanyBranch) {
        this.fromCompanyBranch = fromCompanyBranch;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getFromImageUrl() {
        return fromImageUrl;
    }

    public void setFromImageUrl(String fromImageUrl) {
        this.fromImageUrl = fromImageUrl;
    }

    public String getToPreferredShift() {
        return toPreferredShift;
    }

    public void setToPreferredShift(String toPreferredShift) {
        this.toPreferredShift = toPreferredShift;
    }

    public String getFromPreferredShift() {
        return fromPreferredShift;
    }

    public void setFromPreferredShift(String fromPreferredShift) {
        this.fromPreferredShift = fromPreferredShift;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(toID);
        parcel.writeString(toName);
        parcel.writeString(toShiftDate);
        parcel.writeString(toShiftDay);
        parcel.writeString(toPhone);
        parcel.writeString(toShiftTime);
        parcel.writeString(toAccount);
        parcel.writeString(toCompanyBranch);
        parcel.writeString(toEmail);
        parcel.writeString(toImageUrl);
        parcel.writeString(toPreferredShift);
        parcel.writeString(fromID);
        parcel.writeString(fromName);
        parcel.writeString(fromShiftDate);
        parcel.writeString(fromShiftDay);
        parcel.writeString(fromPhone);
        parcel.writeString(fromShiftTime);
        parcel.writeString(fromAccount);
        parcel.writeString(fromCompanyBranch);
        parcel.writeString(fromEmail);
        parcel.writeString(fromImageUrl);
        parcel.writeString(fromPreferredShift);
        parcel.writeInt(accepted);
        parcel.writeInt(approved);
    }
}
