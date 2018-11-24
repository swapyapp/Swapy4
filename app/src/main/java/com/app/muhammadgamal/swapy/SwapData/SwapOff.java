package com.app.muhammadgamal.swapy.SwapData;

public class SwapOff extends SwapDetails {

    private String preferedOff;
    private String offDay;
    private String swapOffDate;

    public SwapOff (String preferedOff, String offDay, String swapOffDate){
        this.preferedOff = preferedOff;
        this.offDay = offDay;
        this.swapOffDate = swapOffDate;
    }

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
