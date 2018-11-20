package com.app.muhammadgamal.swapy.SwapData;

public class SwapShift extends SwapDetails {

    private String swapperShiftTime;
    private String swapperShiftDay;

    public SwapShift (String swapperShiftDay, String swapperShiftTime){
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
}
