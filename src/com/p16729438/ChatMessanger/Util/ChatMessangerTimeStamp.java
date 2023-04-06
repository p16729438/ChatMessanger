package com.p16729438.ChatMessanger.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessangerTimeStamp {
    public static String getTimeStamp() {
        return new SimpleDateFormat("[yyyy-MM-dd (EEE)  a hh:mm:ss]     ").format(new Date());
    }
}