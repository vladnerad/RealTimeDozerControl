package ru.dst.analyze.realtime.handlers;

import ru.dst.analyze.realtime.DataParser;

public class DataHandlerOld {

    private DataHandlerOld() {
    }

    public static int[] convertData(int[] rawData) {
        //raw data
        //0-LJoy; 1-Press L/R/B; 2-RJoy; 3-PressAtt/Fdr; 4-tEnv/tTurbo; 5-errP3
        int[] result = new int[15];
        for (int i = 0; i < rawData.length; i++) {
            if (i == 0) {
                result[0] = JoyMoveHandler.getForwRev(rawData[i]);
                result[1] = JoyMoveHandler.getLeftRight(rawData[i]);
                continue;
            }
            if (i == 1) {
                result[2] = rawData[i] / 20;
                continue;
            }
            if (i == 2) {
                result[3] = rawData[i];
                continue;
            }
            if (i == 3) {
                result[4] = rawData[i] / 20;
                continue;
            }
//            if (i == 4) {
//                result[5] = rawData[i];
//                continue;
//            }
            if (i == 5) {
                result[5] = (int)(rawData[i] / 6.25);
                continue;
            }
//            if (i == 6){
//                result[12] = rawData[i];
//                continue;
//            }
            if (i == 7) {
                result[6] = rawData[i] - 40;
                continue;
            }
            if (i == 8) {
                result[7] = rawData[i] / 2;
                continue;
            }
            if (i == 9) {
                result[8] = rawData[i] / 2;
                continue;
            }
            if (i == 10){
                result[9] = rawData[i] - 125;
                continue;
            }
            if (i == 11){
                result[10] = (int)(rawData[i] * 0.4);
                continue;
            }
            if (i == 12) {
                result[11] = DataParser.getNumberFromByte(rawData[i], EngineSpeedHanler.getInstance());
                continue;
            }
            if (i == 13) {
                result[12] = DataParser.getNumberFromByte(rawData[i], TempHydOilHandler.getInstance());
                continue;
            }
            if (i == 14) {
                result[13] = DataParser.getNumberFromByte(rawData[i], EngineOilPressHandler.getInstance());
            }
            if (i == 15) {
                //it is possible to show tenths, but we need double
                result[14] = DataParser.getNumberFromByte(rawData[i], MotoHoursHandler.getInstance());
            }
        }
        return result;
    }
}
