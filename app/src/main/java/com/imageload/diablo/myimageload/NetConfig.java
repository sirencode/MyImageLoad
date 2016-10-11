package com.imageload.diablo.myimageload;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者： Diablo on 15/12/31.
 */
public class NetConfig
{
    // 获取最大可用内存
    private static long maxMemory = Runtime.getRuntime().maxMemory();
    //设置内存缓存大小
    private static int cacheSize = (int) (maxMemory / 4);
    public static LruCache<String, Bitmap> MEMORYLRUCACHE= new LruCache<String, Bitmap>(cacheSize)
    {

        @Override
        protected int sizeOf(String key, Bitmap value)
        {
            // 在每次存入缓存的时候调用
            return value.getByteCount();
        }
    };

    /**
     * 设置硬缓存大小
     */
    public static final int DISK_CACHE_SIZE = 1024 * 1024 * 10;
    /**
     * 使用线程池，防止频繁调用导致内存不足
     */
    public static ExecutorService MYPOOL = Executors.newFixedThreadPool(8);
    /**
     * 网络连接超时 10秒
     */
    public static final int READTIMEOUT = 100 * 1000;
    /**
     * 网络连接超时 10秒
     */
    public static final int CONNTIMEOUT = 10 * 1000;
    /**
     * 文件宽处理，建立白名单过滤
     */
    public static HashMap<String, String> FILEPATHMAP = new HashMap<String, String>();
    static
    {
        NetConfig.FILEPATHMAP.put("a", "a");
        NetConfig.FILEPATHMAP.put("b", "b");
        NetConfig.FILEPATHMAP.put("c", "c");
        NetConfig.FILEPATHMAP.put("d", "d");
        NetConfig.FILEPATHMAP.put("e", "e");
        NetConfig.FILEPATHMAP.put("f", "f");
        NetConfig.FILEPATHMAP.put("g", "g");
        NetConfig.FILEPATHMAP.put("h", "h");
        NetConfig.FILEPATHMAP.put("i", "i");
        NetConfig.FILEPATHMAP.put("j", "j");
        NetConfig.FILEPATHMAP.put("k", "k");
        NetConfig.FILEPATHMAP.put("l", "l");
        NetConfig.FILEPATHMAP.put("m", "m");
        NetConfig.FILEPATHMAP.put("n", "n");
        NetConfig.FILEPATHMAP.put("o", "o");
        NetConfig.FILEPATHMAP.put("p", "p");
        NetConfig.FILEPATHMAP.put("q", "q");
        NetConfig.FILEPATHMAP.put("r", "r");
        NetConfig.FILEPATHMAP.put("s", "s");
        NetConfig.FILEPATHMAP.put("t", "t");
        NetConfig.FILEPATHMAP.put("u", "u");
        NetConfig.FILEPATHMAP.put("v", "v");
        NetConfig.FILEPATHMAP.put("w", "w");
        NetConfig.FILEPATHMAP.put("x", "x");
        NetConfig.FILEPATHMAP.put("y", "y");
        NetConfig.FILEPATHMAP.put("z", "z");

        NetConfig.FILEPATHMAP.put("A", "A");
        NetConfig.FILEPATHMAP.put("B", "B");
        NetConfig.FILEPATHMAP.put("C", "C");
        NetConfig.FILEPATHMAP.put("D", "D");
        NetConfig.FILEPATHMAP.put("E", "E");
        NetConfig.FILEPATHMAP.put("F", "F");
        NetConfig.FILEPATHMAP.put("G", "G");
        NetConfig.FILEPATHMAP.put("H", "H");
        NetConfig.FILEPATHMAP.put("I", "I");
        NetConfig.FILEPATHMAP.put("J", "J");
        NetConfig.FILEPATHMAP.put("K", "K");
        NetConfig.FILEPATHMAP.put("L", "L");
        NetConfig.FILEPATHMAP.put("M", "M");
        NetConfig.FILEPATHMAP.put("N", "N");
        NetConfig.FILEPATHMAP.put("O", "O");
        NetConfig.FILEPATHMAP.put("P", "P");
        NetConfig.FILEPATHMAP.put("Q", "Q");
        NetConfig.FILEPATHMAP.put("R", "R");
        NetConfig.FILEPATHMAP.put("S", "S");
        NetConfig.FILEPATHMAP.put("T", "T");
        NetConfig.FILEPATHMAP.put("U", "U");
        NetConfig.FILEPATHMAP.put("V", "V");
        NetConfig.FILEPATHMAP.put("W", "W");
        NetConfig.FILEPATHMAP.put("X", "X");
        NetConfig.FILEPATHMAP.put("Y", "Y");
        NetConfig.FILEPATHMAP.put("Z", "Z");

        NetConfig.FILEPATHMAP.put("0", "0");
        NetConfig.FILEPATHMAP.put("1", "1");
        NetConfig.FILEPATHMAP.put("2", "2");
        NetConfig.FILEPATHMAP.put("3", "3");
        NetConfig.FILEPATHMAP.put("4", "4");
        NetConfig.FILEPATHMAP.put("5", "5");
        NetConfig.FILEPATHMAP.put("6", "6");
        NetConfig.FILEPATHMAP.put("7", "7");
        NetConfig.FILEPATHMAP.put("8", "8");
        NetConfig.FILEPATHMAP.put("9", "9");
    }

}
