package ru.dst.analyze.realtime.handlers;

public class EngineSpeedHanler extends Handler {
    //must be Singleton
    private static volatile EngineSpeedHanler instance;

    private String name;
    private int startBit;
    private int length;
    private double multiply;
    private int shift;

    private EngineSpeedHanler() {
        name = "Engine speed";
        startBit = 0;
        length = 16;
        multiply = 0.125;
        shift = 0;
    }

    public static EngineSpeedHanler getInstance(){
        EngineSpeedHanler result = instance;
        if (result == null) {
            synchronized (EngineSpeedHanler.class) {
                result = instance;
                if (result == null) {
                    instance = result = new EngineSpeedHanler();
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
