package ru.dst.analyze.realtime.handlers;

public class EngineOilPressHandler extends Handler {
    //must be Singleton
    private static volatile EngineOilPressHandler instance;

    private String name;
    private int startBit;
    private int length;
    private double multiply;
    private int shift;

    private EngineOilPressHandler() {
        name = "Engine oil pressure";
        startBit = 0;
        length = 8;
        multiply = 0.04;
        shift = 0;
    }

    public static EngineOilPressHandler getInstance(){
        EngineOilPressHandler result = instance;
        if (result == null) {
            synchronized (EngineOilPressHandler.class) {
                result = instance;
                if (result == null) {
                    instance = result = new EngineOilPressHandler();
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
