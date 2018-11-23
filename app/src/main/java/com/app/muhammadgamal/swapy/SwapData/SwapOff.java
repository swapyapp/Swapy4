package com.app.muhammadgamal.swapy.SwapData;

public class SwapOff extends SwapDetails {

    private String desiredOff;
    private String offDay;
    private String swapOffDate;

    public SwapOff (String desiredOff, String offDay, String swapOffDate){
        this.desiredOff = desiredOff;
        this.offDay = offDay;
        this.swapOffDate = swapOffDate;
    }

    public String getDesiredOff() {
        return desiredOff;
    }

    public String getOffDay() {
        return offDay;
    }

    public String getSwapOffDate() {
        return swapOffDate;
    }

    public void setDesiredOff(String desiredOff) {
        this.desiredOff = desiredOff;
    }

    public void setOffDay(String offDay) {
        this.offDay = offDay;
    }

    public void setSwapOffDate(String swapOffDate) {
        this.swapOffDate = swapOffDate;
    }
}
