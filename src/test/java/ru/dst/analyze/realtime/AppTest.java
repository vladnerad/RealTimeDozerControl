package ru.dst.analyze.realtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
//    @Test
//    public void shouldAnswerWithTrue()
//    {
//        assertTrue( true );
//    }

    @Test
    public void testGetDieselStarts(){
        int startedRpm = 700;
        int stoppedRpm = 0;
        int[][] testArr = new int[123][AnalogInParams.MOTOR_HOUS.ordinal()];

        for (int[] ints : testArr) {
            Arrays.fill(ints, stoppedRpm);
        }
        assertEquals(App.getDieselStarts(testArr), -1);
        for (int[] ints : testArr) {
            Arrays.fill(ints, startedRpm);
        }
        assertEquals(App.getDieselStarts(testArr), 0);

        testArr[3][AnalogInParams.SPEED_ENGINE.ordinal()] = stoppedRpm;
        testArr[50][AnalogInParams.SPEED_ENGINE.ordinal()] = stoppedRpm;
        testArr[97][AnalogInParams.SPEED_ENGINE.ordinal()] = stoppedRpm;
        testArr[122][AnalogInParams.SPEED_ENGINE.ordinal()] = stoppedRpm;
        assertEquals(App.getDieselStarts(testArr), 4);
    }
}
