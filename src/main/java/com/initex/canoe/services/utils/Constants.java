package com.initex.canoe.services.utils;

import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final String DATABASE_UPDATE_ERROR = "Database update error";
    public static final String DATABASE_SAVE_ERROR = "Database save error";
    public static final String DATABASE_READ_ERROR = "Database read error";
    public static final String DUPLICATE_DATA_EXISTS = "Duplicate data exists!";
    public static final String NO_SUCH_RECORD_ERROR = "No such record error";
    public static final List<String> BOAT_CLASS = Arrays.asList("C1W", "C1M", "K1W", "K1M");
    public static final String HEAT_1 = "Heat1";
    public static final String HEAT_2 = "Heat2";
    public static final String SEMI_FINAL = "Semi-final";
    public static final String FINAL = "Final";

    private Constants() {
    }

    public static String getDatabaseUpdateError() {
        return DATABASE_UPDATE_ERROR;
    }

    public static String getDatabaseSaveError() {
        return DATABASE_SAVE_ERROR;
    }

    public static String getDatabaseReadError() {
        return DATABASE_READ_ERROR;
    }

    public static String getDuplicateDataExists() {
        return DUPLICATE_DATA_EXISTS;
    }

    public static String getNoSuchRecordError() {
        return NO_SUCH_RECORD_ERROR;
    }

    public static List<String> getBoatClass() {
        return BOAT_CLASS;
    }

    public static String getHeat1() {
        return HEAT_1;
    }

    public static String getHeat2() {
        return HEAT_2;
    }

    public static String getSemiFinal() {
        return SEMI_FINAL;
    }

    public static String getFINAL() {
        return FINAL;
    }
}
