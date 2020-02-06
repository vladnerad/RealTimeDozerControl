package ru.dst.analyze.realtime.handlers;

import ru.dst.analyze.realtime.AnalogInParams;
import ru.dst.analyze.realtime.DataParser;

public class DataHandler {

    private DataHandler() {
    }

    public static int[] convertData(int[] rawData) {
        //raw data
        //0-LJoy; 1-Press L/R/B; 2-RJoy; 3-PressAtt/Fdr; 4-tEnv/tTurbo; 5-errP3
        int[] result = new int[19];
        for (int i = 0; i < rawData.length; i++) {
            if (i == 0) {
                result[AnalogInParams.JOY_MOVE_FORW_REV.ordinal()] = JoyMoveHandler.getForwRev(rawData[i]);
                result[AnalogInParams.JOY_MOVE_LEFT_RIGHT.ordinal()] = JoyMoveHandler.getLeftRight(rawData[i]);
                continue;
            }
            if (i == 1) {
                result[AnalogInParams.PRESS_PUMP_LEFT.ordinal()] = DataParser.getNumberFromByte(rawData[i], LeftPressHandler.getInstance());
                result[AnalogInParams.PRESS_PUMP_RIGHT.ordinal()] = DataParser.getNumberFromByte(rawData[i], RightPressHandler.getInstance());
                result[AnalogInParams.PRESS_BRAKE.ordinal()] = DataParser.getNumberFromByte(rawData[i], BrakePressHandler.getInstance());
                continue;
            }
            if (i == 2) {
                result[AnalogInParams.FUEL_LEVEL.ordinal()] = rawData[i];
                continue;
            }
            if (i == 3) {
                result[AnalogInParams.JOY_ATTACH_FORW_REV.ordinal()] = JoyAttachHandler.getForwRev(rawData[i]);
                result[AnalogInParams.JOY_ATTACH_LEFT_RIGHT.ordinal()] = JoyAttachHandler.getLeftRight(rawData[i]);
                continue;
            }
            if (i == 4) {
                result[AnalogInParams.PRESS_ATTACH.ordinal()] = DataParser.getNumberFromByte(rawData[i], AttPressHandler.getInstance());
                result[AnalogInParams.PRESS_FAN_DRIVE.ordinal()] = DataParser.getNumberFromByte(rawData[i], FanDrivePressHandler.getInstance());
                continue;
            }
            if (i == 5) {
                result[AnalogInParams.TEMP_ENVIR.ordinal()] = DataParser.getNumberFromByte(rawData[i], TempEnvHandler.getInstance());
                result[AnalogInParams.TEMP_TURBO.ordinal()] = DataParser.getNumberFromByte(rawData[i], TempTurboHandler.getInstance());
                continue;
            }
            if (i == 7) {
                result[AnalogInParams.TEMP_HYD_OIL.ordinal()] = DataParser.getNumberFromByte(rawData[i], TempHydOilHandler.getInstance());
                continue;
            }
            if (i == 8) {
                result[AnalogInParams.SPEED_HM_LEFT.ordinal()] = DataParser.getNumberFromByte(rawData[i], HMSpeedHandler.getInstance());
                continue;
            }
            if (i == 9) {
                result[AnalogInParams.SPEED_HM_RIGHT.ordinal()] = DataParser.getNumberFromByte(rawData[i], HMSpeedHandler.getInstance());
                continue;
            }
            if (i == 12) {
                result[AnalogInParams.SPEED_ENGINE.ordinal()] = DataParser.getNumberFromByte(rawData[i], EngineSpeedHanler.getInstance());
                continue;
            }
            if (i == 13) {
                result[AnalogInParams.TEMP_COOLANT.ordinal()] = DataParser.getNumberFromByte(rawData[i], TempHydOilHandler.getInstance());
                continue;
            }
            if (i == 14) {
                result[AnalogInParams.PRESS_ENGINE_OIL.ordinal()] = DataParser.getNumberFromByte(rawData[i], EngineOilPressHandler.getInstance());
            }
            if (i == 15) {
                //it is possible to show tenths, but we need double
                result[AnalogInParams.MOTOR_HOUS.ordinal()] = DataParser.getNumberFromByte(rawData[i], MotoHoursHandler.getInstance());
            }
        }
        return result;
    }

    public static String getErrors(int[] rawData) {
        if (rawData.length > 11)
            return DataParser.errorsToString(DataParser.getErrors(rawData[10], rawData[11], rawData[6]));
        else return "rawData.length < 11";
    }

    public static String getStingFromDigInData(boolean[] digInData, boolean isHumanText) {
        StringBuilder sb = new StringBuilder();
        if (isHumanText) {
            for (int i = 0; i < digInData.length; i++) {
                String buff = "";
                switch (i) {
                    case 0:
                        if (digInData[i]) buff = "auto";
                        else buff = "off/reverse";
                        break;
                    case 1:
                        if (digInData[i]) buff = "released";
                        else buff = "pressed";
                        break;
                    case 2:
                        if (digInData[i]) buff = "transport";
                        else buff = "work";
                        break;
                    case 3:
                        if (digInData[i]) buff = "low";
                        else buff = "normal";
                        break;
                    case 4:
                        if (digInData[i]) buff = "ripper";
                        else buff = "blade";
                        break;
                    case 5:
                        if (digInData[i]) buff = "swimON";
                        else buff = "swimOFF";
                        break;
                    case 6:
                        if (digInData[i]) buff = "parkON";
                        else buff = "parkOFF";
                        break;
                    case 7:
                        if (digInData[i]) buff = "towingON";
                        else buff = "towingOFF";
                        break;
                    default:
                        System.out.println("error getStingFromDigInData");
                        break;
                }
                sb.append(buff);
                if (i != digInData.length - 1) {
                    sb.append(",");
                }
            }
        } else {
            for (int i = 0; i < digInData.length; i++) {
                sb.append(digInData[i]);
                if (i != digInData.length - 1) {
                    sb.append(",");
                }
            }
        }
        return sb.toString();
    }
}
