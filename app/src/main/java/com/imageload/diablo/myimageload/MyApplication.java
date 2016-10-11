package com.imageload.diablo.myimageload;

import android.app.Application;

/**
 * 作者： Diablo on 16/1/11.
 */
public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        LogUtile.LOGOPEN = true;
    }
}
