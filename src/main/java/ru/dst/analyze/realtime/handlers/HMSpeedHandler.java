package ru.dst.analyze.realtime.handlers;

public class HMSpeedHandler extends Handler {
    //must be Singleton
    private static volatile HMSpeedHandler instance;

    private String name;
    private int startBit;
    private int length;
    private double multiply;
    private int shift;

    private HMSpeedHandler() {
        name = "Speed Motor";
        startBit = 0;
        length = 16;
        multiply = 0.5;
        shift = 0;
    }

    public static HMSpeedHandler getInstance(){
        HMSpeedHandler result = instance;
        if (result == null) {
            synchronized (HMSpeedHandler.class) {
                result = instance;
                if (result == null) {
                    instance = result = new HMSpeedHandler();
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
