package ru.dst.analyze.realtime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.dst.analyze.realtime.handlers.DataHandler;
import ru.dst.analyze.realtime.response.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static final int PUMP_PRESS_BORDER = 450;
    //    public static final int ENV_TEMP_BORDER = -20;
    public static final int HYD_OIL_TEMP_BORDER = 75;
    public static final int MOTOR_SPEED_BORDER = 3500;
    public static final int COOLANT_TEMP_BORDER = 90;
    public static final long TIME_GAP_MINUTES = 5;
//    public static final long TIME_GAP_MINUTES = 1;

    public static final String pressL = "Pump pressure issue LEFT: ";
    public static final String pressR = "Pump pressure issue RIGHT: ";
    public static final String envirTemp = "Env.Temp: ";
    public static final String tempHydOil = "HydOil temperature issue: ";
    public static final String hMSpeedL = "HM rpm issue LEFT: ";
    public static final String hMSpeedR = "HM rpm issue RIGHT: ";
    public static final String tempCoolant = "Coolant temperature issue: ";
    public static final String engineStart = "Engine started: %d times ";
    public static final String degC = "°C ";

    public static final int avEnvirTemp = -40;
    public static final int maxPressL = 0;
    public static final int maxPressR = 0;
    public static final int maxTempHydOil = -40;
    public static final int maxTempCoolant = -40;
    public static final int maxHMSpeedL = 0;
    public static final int maxHMSpeedR = 0;

    public static void main(String[] args) {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime from = now.minusHours(60);
//        Message message = getMessage(from, now);
//        if (message != null) {
//            getIssues(message);
//        }

        while (true) {
            LocalDateTime then = LocalDateTime.now();
            try {
                Thread.sleep(TIME_GAP_MINUTES * 30000); //halftime
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (true) {
                if (LocalDateTime.now().equals(then.plusMinutes(TIME_GAP_MINUTES))) {
//                    System.out.print(dtf.format(then) + " ");
                    issuesAndErrorsFromGap(then);
                    break;
                }
            }
        }
    }

    public static void issuesAndErrorsFromGap(LocalDateTime from) {
        LocalDateTime to = from.plusMinutes(TIME_GAP_MINUTES);
        Message message = getMessage(from, to);
        getIssues(message);
    }

    public static Message getMessage(LocalDateTime from, LocalDateTime to) {
        Message message = new JsonHelper("4NE023815", getLocarusDateTimeFormat(from.minusHours(5)), getLocarusDateTimeFormat(to.minusHours(5))).getMessage();
        if (message != null) {
            if (message.getDescription() == null) {
                return message;
            } else System.out.println(message.getDescription());
        } else System.out.println("Message is null");
        return null;
    }

    public static void getIssues(Message message) {
        Set<Integer> errors;
        if (message != null) {
//            String[] time = DataParser.getTimeArr(message);
            //getting analogIn from JSON response
            int[][] data = DataParser.getDataArray(message);
            //getting digitalIn from JSON response
//            boolean[][] digInData = DataParser.getDigitalInData(message);

            if (data != null) {
                int[][] clearData = Arrays.stream(data).map(DataHandler::convertData).toArray(int[][]::new);
                Map<String, Integer> issuesMap = fillMap(clearData);
                String issuesMessage = getIssuesMessage(issuesMap);
                errors = new HashSet<>();
                for (int[] rec : data) {
                    errors.addAll(DataParser.getErrorsFromRow(rec));
                }

                String logMess = envirTemp
                        .concat(String.valueOf(issuesMap.get(envirTemp)))
                        .concat(degC)
                        .concat(getDieselStartMessage(getDieselStarts(clearData)));
                if (issuesMessage.equals("") && errors.isEmpty()) {
                    logMess = logMess.concat("Dozer works fine");
                    logger.info(logMess);
                } else if (!issuesMessage.equals("") && !errors.isEmpty()) {
                    logMess = logMess.concat(" Issues: ")
                            .concat(issuesMessage)
                            .concat(" Errors: ")
                            .concat(errors.toString());
                    logger.warn(logMess);
                } else if (!issuesMessage.equals("")) {
                    logMess = logMess.concat("Issues: ")
                            .concat(issuesMessage);
                    logger.warn(logMess);
                } else {
                    logMess = logMess.concat("Errors: ")
                            .concat(errors.toString());
                    logger.warn(logMess);
                }
//                System.out.println(String.format(engineStart, getDieselStarts(clearData)));
            } else logger.info("Standstill");
        }
    }

    public static String getLocarusDateTimeFormat(LocalDateTime date) {
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");
        return dtf1.format(date) + "T" + dtf2.format(date) + "Z";
    }

    public static Map<String, Integer> fillMap(int[][] data) {
        Map<String, Integer> map = initParamsMap();
        for (int[] row : data) {
            map.replace(envirTemp, map.get(envirTemp), map.get(envirTemp) + row[AnalogInParams.TEMP_ENVIR.ordinal()]);

            if (row[AnalogInParams.PRESS_PUMP_LEFT.ordinal()] > map.get(pressL)) {
                map.replace(pressL, row[AnalogInParams.PRESS_PUMP_LEFT.ordinal()]);
            }
            if (row[AnalogInParams.PRESS_PUMP_RIGHT.ordinal()] > map.get(pressR)) {
                map.replace(pressR, row[AnalogInParams.PRESS_PUMP_RIGHT.ordinal()]);
            }
            if (row[AnalogInParams.TEMP_HYD_OIL.ordinal()] > map.get(tempHydOil)) {
                map.replace(tempHydOil, row[AnalogInParams.TEMP_HYD_OIL.ordinal()]);
            }
            if (row[AnalogInParams.SPEED_HM_LEFT.ordinal()] > map.get(hMSpeedL)) {
                map.replace(hMSpeedL, row[AnalogInParams.SPEED_HM_LEFT.ordinal()]);
            }
            if (row[AnalogInParams.SPEED_HM_RIGHT.ordinal()] > map.get(hMSpeedR)) {
                map.replace(hMSpeedR, row[AnalogInParams.SPEED_HM_RIGHT.ordinal()]);
            }
            if (row[AnalogInParams.TEMP_COOLANT.ordinal()] > map.get(tempCoolant)) {
                map.replace(tempCoolant, row[AnalogInParams.TEMP_COOLANT.ordinal()]);
            }
        }
        map.replace(envirTemp, map.get(envirTemp), map.get(envirTemp) / data.length);
        return map;
    }

    public static Map<String, Integer> initParamsMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put(pressL, maxPressL);
        map.put(pressR, maxPressR);
        map.put(envirTemp, avEnvirTemp);
        map.put(tempHydOil, maxTempHydOil);
        map.put(hMSpeedL, maxHMSpeedL);
        map.put(hMSpeedR, maxHMSpeedR);
        map.put(tempCoolant, maxTempCoolant);
        return map;
    }

    public static String getIssuesMessage(Map<String, Integer> map) {
        String spliter = "; ";
        StringBuilder sb = new StringBuilder();
        if (map.get(pressL) > PUMP_PRESS_BORDER) {
            sb.append(pressL).append(map.get(pressL)).append(spliter);
        }
        if (map.get(pressR) > PUMP_PRESS_BORDER) {
            sb.append(pressR).append(map.get(pressR)).append(spliter);
        }
        if (map.get(tempHydOil) > HYD_OIL_TEMP_BORDER) {
            sb.append(tempHydOil).append(map.get(tempHydOil)).append(spliter);
        }
        if (map.get(hMSpeedL) > MOTOR_SPEED_BORDER) {
            sb.append(hMSpeedL).append(map.get(hMSpeedL)).append(spliter);
        }
        if (map.get(hMSpeedR) > MOTOR_SPEED_BORDER) {
            sb.append(hMSpeedR).append(map.get(hMSpeedR)).append(spliter);
        }
        if (map.get(tempCoolant) > COOLANT_TEMP_BORDER) {
            sb.append(tempCoolant).append(map.get(tempCoolant)).append(spliter);
        }
        return sb.toString();
    }

    public static int getDieselStarts(int[][] clearData) {
        int j = AnalogInParams.SPEED_ENGINE.ordinal();
        boolean isStandstill = true;
        int result = 0;
        for (int i = 5; i < clearData.length; i = i + 5) {
            int currentSpeed = clearData[i][j];
            if (currentSpeed > 500) {
                isStandstill = false;
                if (i >= 5 &&
                        (clearData[i - 1][j] == 0
                                || clearData[i - 2][j] == 0
                                || clearData[i - 3][j] == 0
                                || clearData[i - 4][j] == 0
                                || clearData[i - 5][j] == 0)) {
                    result++;
                }
            }
        }
        int left = clearData.length % 5;
        if (left != 0){
            List<Integer> list = new ArrayList<>();
            int y = clearData.length - 1 - left;
            while (y < clearData.length) {
                list.add(clearData[y][j]);
                y++;
            }
            for (int x: list){
                if (x > 500 && list.contains(0)){
                    result++;
                    break;
                }
            }
        }
        if (isStandstill) return -1;
        return result;
    }

    public static String getDieselStartMessage(int i){
        if (i == -1){
            return "Diesel standstill ";
        }
        else if (i == 0){
            return "Diesel running ";
        }
        else {
            return String.format(engineStart, i);
        }
    }
}