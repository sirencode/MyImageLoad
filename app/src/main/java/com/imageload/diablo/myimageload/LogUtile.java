package com.imageload.diablo.myimageload;

import android.util.Log;

/**
 * 作者： Diablo on 16/1/8.
 */
public class LogUtile
{
    public static boolean LOGOPEN = true;

    public static void logV(String tag, String msg)
    {
        if (LogUtile.LOGOPEN)
        {
            Log.v(tag, msg);
        }
    }

    public static void logD(String tag, String msg)
    {
        if (LogUtile.LOGOPEN)
        {
            Log.d(tag, msg);
        }
    }

    public static void logI(String tag, String msg)
    {
        if (LogUtile.LOGOPEN)
        {
            Log.i(tag, msg);
        }
    }

    public static void logW(String tag, String msg)
    {
        if (LogUtile.LOGOPEN)
        {
            Log.w(tag, msg);
        }
    }

    public static void logE(String tag, String msg)
    {
        if (LogUtile.LOGOPEN)
        {
            Log.e(tag, msg);
        }
    }

    public static void systemOut(String msg)
    {
        if (LogUtile.LOGOPEN)
        {
            System.out.println(msg);
        }
    }
}
