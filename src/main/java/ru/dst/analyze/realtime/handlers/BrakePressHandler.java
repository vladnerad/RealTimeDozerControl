package ru.dst.analyze.realtime.handlers;

public class BrakePressHandler extends Handler {
    //must be Singleton
    private static volatile BrakePressHandler instance;

    private String name;
    private int startBit;
    private int length;
    private int multiply;
    private int shift;

    private BrakePressHandler() {
        name = "Brake line pressure";
        startBit = 24;
        length = 8;
        multiply = 1;
        shift = 0;
    }

    public static BrakePressHandler getInstance(){
        BrakePressHandler result = instance;
        if (result == null) {
            synchronized (BrakePressHandler.class) {
                result = instance;
                if (result == null) {
                    instance = result = new BrakePressHandler();
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
