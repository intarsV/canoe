package com.initex.canoe.services.utils.results;

public class CalcPenalties {

    private CalcPenalties() {
    }

    public static int countPenalties(int[] penaltyList) {
        int penaltySeconds = 0;
        for (int i : penaltyList) {
            penaltySeconds = penaltySeconds + i;
        }
        return penaltySeconds;
    }
}
