package ru.dst.analyze.realtime.handlers;

public class TempEnvHandler extends Handler{
    //must be Singleton
    private static volatile TempEnvHandler instance;

    private String name;
    private int startBit;
    private int length;
    private int multiply;
    private int shift;

    private TempEnvHandler() {
        name = "Environment temperature";
        startBit = 0;
        length = 8;
        multiply = 1;
        shift = -40;
    }

    public static TempEnvHandler getInstance(){
        TempEnvHandler result = instance;
        if (result == null) {
            synchronized (TempEnvHandler.class) {
                result = instance;
                if (result == null) {
                    instance = result = new TempEnvHandler();
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
