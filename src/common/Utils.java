package common;

import java.util.Date;

public abstract class Utils {
    public static long getNowTime(){
//        return new Date().getTime();
        return System.currentTimeMillis();
    }
}
