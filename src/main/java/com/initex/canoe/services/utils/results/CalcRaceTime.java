package com.initex.canoe.services.utils.results;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalcRaceTime {

    private CalcRaceTime() {
    }

    public static BigDecimal calcRaceTime(long startTime, long finishTime) {
        if (startTime > 0 && finishTime > 0) {
            return new BigDecimal(finishTime - startTime)
                    .divide(new BigDecimal(1000),2, RoundingMode.HALF_UP)
                    .setScale(2, RoundingMode.HALF_UP);
        }
        return new BigDecimal("0.00");
    }
}
