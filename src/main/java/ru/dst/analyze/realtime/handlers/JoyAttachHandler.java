package ru.dst.analyze.realtime.handlers;

import ru.dst.analyze.realtime.DataParser;

public class JoyAttachHandler {

    public static int getForwRev(int n){
        boolean[] bits = new boolean[32];
        boolean isBackward = false;
        boolean isNeutral = false;
        for (int i = 31; i >= 0; i--) {
            bits[i] = (n & (1 << i)) != 0;
        }
        if (JoyMoveHandler.validate(bits)) {
            //Defining the direction FNR
            if (bits[20] && !bits[18]) isBackward = true;
            else if (!bits[20] && bits[18]) isBackward = false;
            else isNeutral = true;
            //Defining percentage of tilt
            int joyY = DataParser.getNumberFromByte(n, 22, 10);

            if (isNeutral) return 0;
            else if (isBackward) return joyY - (joyY * 2);
            else return joyY;
        }
        else return 111;
    }

    public static int getLeftRight(int n){
        boolean[] bits = new boolean[32];
        boolean isLeft = false;
        boolean isNeutral = false;
        for (int i = 31; i >= 0; i--) {
            bits[i] = (n & (1 << i)) != 0;
        }
        if (JoyMoveHandler.validate(bits)) {
            //Defining the direction left/right
            if (bits[2] && !bits[4]) isLeft = true;
            else if (!bits[2] && bits[4]) isLeft = false;
            else isNeutral = true;
            //Defining percentage of tilt
            int joyX = DataParser.getNumberFromByte(n, 6, 10);

            if (isNeutral) return 0;
            else if (isLeft) return joyX - (joyX * 2);
            else return joyX;
        } else return 111;
    }
}
