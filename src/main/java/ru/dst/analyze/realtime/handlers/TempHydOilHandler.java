package ru.dst.analyze.realtime.handlers;

public class TempHydOilHandler extends Handler {
    //must be Singleton
    private static volatile TempHydOilHandler instance;

    private String name;
    private int startBit;
    private int length;
    private double multiply;
    private int shift;

    private TempHydOilHandler() {
        name = "Hydraulic oil temperature";
        startBit = 0;
        length = 8;
        multiply = 1;
        shift = -40;
    }

    public static TempHydOilHandler getInstance(){
        TempHydOilHandler result = instance;
        if (result == null) {
            synchronized (TempHydOilHandler.class) {
                result = instance;
                if (result == null) {
                    instance = result = new TempHydOilHandler();
                }
            }
        }
        return instance;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getStartBit() {
        return startBit;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public double getMultiply() {
        return multiply;
    }

    @Override
    public int getShift() {
        return shift;
    }
}
