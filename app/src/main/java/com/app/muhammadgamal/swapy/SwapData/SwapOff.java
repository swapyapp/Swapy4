package com.app.muhammadgamal.swapy.SwapData;

import android.os.Parcel;
import android.os.Parcelable;

public class SwapOff extends SwapDetails implements Parcelable {

    private String preferedOff;
    private String offDay;
    private String swapOffDate;

    public SwapOff(){}

    public SwapOff (String preferredOff,
                    String offDay,
                    String swapOffDate,
                    String swapperID,
                    String swapperName,
                    String swapperEmail,
                    String swapperPhone,
                    String swapperCompanyBranch,
                    String swapperAccount,
                    String swapperImageUrl)
    {
        super(swapperID,swapperName,swapperEmail,swapperPhone,swapperCompanyBranch,swapperAccount, swapperImageUrl);
        this.preferedOff = preferredOff;
        this.offDay = offDay;
        this.swapOffDate = swapOffDate;
    }

//    public SwapOff(String userId,
//                   String swapperName,
//                   String swapperEmail,
//                   String swapperPhone,
//                   String currentUserCompanyBranch,
//                   String currentUserAccount,
//                   String swapperImageUrl,
//                   String offDay,
//                   String offDate,
//                   String preferredOff) {
//        super(userId,swapperName,swapperEmail,swapperPhone,currentUserAccount,
//                currentUserCompanyBranch,swapperImageUrl);
//        this.offDay = offDay;
//        this.preferedOff = preferredOff;
//        this.swapOffDate = offDate;
//
//    }


    protected SwapOff(Parcel in) {
        super(in);
        preferedOff = in.readString();
        offDay = in.readString();
        swapOffDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(preferedOff);
        dest.writeString(offDay);
        dest.writeString(swapOffDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SwapOff> CREATOR = new Creator<SwapOff>() {
        @Override
        public SwapOff createFromParcel(Parcel in) {
            return new SwapOff(in);
        }

        @Override
        public SwapOff[] newArray(int size) {
            return new SwapOff[size];
        }
    };

    public String getPreferedOff() {
        return preferedOff;
    }

    public String getOffDay() {
        return offDay;
    }

    public String getSwapOffDate() {
        return swapOffDate;
    }

    public void setPreferedOff(String preferedOff) {
        this.preferedOff = preferedOff;
    }

    public void setOffDay(String offDay) {
        this.offDay = offDay;
    }

    public void setSwapOffDate(String swapOffDate) {
        this.swapOffDate = swapOffDate;
    }
}
