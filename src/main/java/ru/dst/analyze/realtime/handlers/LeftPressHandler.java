package ru.dst.analyze.realtime.handlers;

public class LeftPressHandler extends Handler {
    //must be Singleton
    private static volatile LeftPressHandler instance;

    private String name;
    private int startBit;
    private int length;
    private int multiply;
    private int shift;

    private LeftPressHandler() {
        name = "Left pump pressure";
        startBit = 0;
        length = 10;
        multiply = 1;
        shift = 0;
    }

    public static LeftPressHandler getInstance(){
        LeftPressHandler result = instance;
        if (result == null) {
            synchronized (LeftPressHandler.class) {
                result = instance;
                if (result == null) {
                    instance = result = new LeftPressHandler();
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
