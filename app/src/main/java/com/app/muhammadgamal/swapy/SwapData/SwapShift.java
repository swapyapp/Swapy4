package com.app.muhammadgamal.swapy.SwapData;

public class SwapShift extends SwapDetails {

    private String swapperShiftTime;
    private String swapperShiftDay;

    public SwapShift(String swapperShiftDay, String swapperShiftTime) {
        this.swapperShiftDay = swapperShiftDay;
        this.swapperShiftTime = swapperShiftTime;
    }

    @Override
    public String getSwapperShiftDay() {
        return swapperShiftDay;
    }

    @Override
    public String getSwapperShiftTime() {
        return swapperShiftTime;
    }

    @Override
    public void setSwapperShiftTime(String swapperShiftTime) {
        this.swapperShiftTime = swapperShiftTime;
    }

    @Override
    public void setSwapperShiftDay(String swapperShiftDay) {
        this.swapperShiftDay = swapperShiftDay;
    }
}
