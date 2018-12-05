package com.app.muhammadgamal.swapy.SwapData;

public class SwapOff extends SwapDetails {

    private String preferedOff;
    private String offDay;
    private String swapOffDate;

    public SwapOff(){}

    public SwapOff (String preferedOff,
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
        this.preferedOff = preferedOff;
        this.offDay = offDay;
        this.swapOffDate = swapOffDate;
    }

<<<<<<< HEAD
=======
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

>>>>>>> 4c3f71744ad8ae02b830a249fecfcb0056d96b7b

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
