package ru.dst.analyze.realtime.handlers;

public class RightPressHandler extends Handler {
    //must be Singleton
    private static volatile RightPressHandler instance;

    private String name;
    private int startBit;
    private int length;
    private int multiply;
    private int shift;

    private RightPressHandler() {
        name = "Right pump pressure";
        startBit = 12;
        length = 10;
        multiply = 1;
        shift = 0;
    }

    public static RightPressHandler getInstance(){
        RightPressHandler result = instance;
        if (result == null) {
            synchronized (RightPressHandler.class) {
                result = instance;
                if (result == null) {
                    instance = result = new RightPressHandler();
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
