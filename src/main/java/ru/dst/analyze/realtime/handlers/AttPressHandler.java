package ru.dst.analyze.realtime.handlers;

public class AttPressHandler extends Handler {
    //must be Singleton
    private static volatile AttPressHandler instance;

    private String name;
    private int startBit;
    private int length;
    private int multiply;
    private int shift;

    private AttPressHandler() {
        name = "Attachments pressure";
        startBit = 6;
        length = 10;
        multiply = 1;
        shift = 0;
    }

    public static AttPressHandler getInstance(){
        AttPressHandler result = instance;
        if (result == null) {
            synchronized (AttPressHandler.class) {
                result = instance;
                if (result == null) {
                    instance = result = new AttPressHandler();
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
