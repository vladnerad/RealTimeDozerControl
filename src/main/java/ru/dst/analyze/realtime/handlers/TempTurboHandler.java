package ru.dst.analyze.realtime.handlers;

public class TempTurboHandler extends Handler {
    //must be Singleton
    private static volatile TempTurboHandler instance;

    private String name;
    private int startBit;
    private int length;
    private int multiply;
    private int shift;

    private TempTurboHandler() {
        name = "Turbo charger temperature";
        startBit = 8;
        length = 8;
        multiply = 1;
        shift = -40;
    }

    public static TempTurboHandler getInstance(){
        TempTurboHandler result = instance;
        if (result == null) {
            synchronized (TempTurboHandler.class) {
                result = instance;
                if (result == null) {
                    instance = result = new TempTurboHandler();
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
