package ru.dst.analyze.realtime.handlers;

public class MotoHoursHandler extends Handler {
    //must be Singleton
    private static volatile MotoHoursHandler instance;

    private String name;
    private int startBit;
    private int length;
    private double multiply;
    private int shift;

    private MotoHoursHandler() {
        name = "Moto Hours";
        startBit = 0;
        length = 32;
        multiply = 0.1;
        shift = 0;
    }

    public static MotoHoursHandler getInstance(){
        MotoHoursHandler result = instance;
        if (result == null) {
            synchronized (MotoHoursHandler.class) {
                result = instance;
                if (result == null) {
                    instance = result = new MotoHoursHandler();
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
