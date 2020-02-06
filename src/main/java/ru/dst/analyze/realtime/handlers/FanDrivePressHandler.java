package ru.dst.analyze.realtime.handlers;

public class FanDrivePressHandler extends Handler{
    //must be Singleton
    private static volatile FanDrivePressHandler instance;

    private String name;
    private int startBit;
    private int length;
    private int multiply;
    private int shift;

    private FanDrivePressHandler() {
        name = "Fan Drive pressure";
        startBit = 22;
        length = 10;
        multiply = 1;
        shift = 0;
    }

    public static FanDrivePressHandler getInstance(){
        FanDrivePressHandler result = instance;
        if (result == null) {
            synchronized (FanDrivePressHandler.class) {
                result = instance;
                if (result == null) {
                    instance = result = new FanDrivePressHandler();
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
