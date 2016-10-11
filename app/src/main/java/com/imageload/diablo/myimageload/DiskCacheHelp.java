package com.imageload.diablo.myimageload;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 作者： Diablo on 16/1/5.
 */
public class DiskCacheHelp
{
    /**
     * 获取当前应用程序的版本号。
     */
    public int getAppVersion(Context context)
    {
        try
        {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 从磁盘缓存里面读取图片信息
     *
     * @param mDiskLruCache
     * @param imageUrl
     * @return
     */
    public Bitmap getBitmapFromeDiskCache(DiskLruCache mDiskLruCache, String imageUrl)
    {
        //判断文件缓存里面是否存在
        try
        {
            String key = hashKeyForDisk(imageUrl);
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
            if (snapShot != null)
            {
                InputStream is = snapShot.getInputStream(0);
                Bitmap fileBitmap = BitmapFactory.decodeStream(is);
                is.close();
                return fileBitmap;
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 将图片添加到磁盘缓存中
     *
     * @param mDiskLruCache
     * @param imageUrl
     */
    public void setDiskCacheBitmap(DiskLruCache mDiskLruCache, String imageUrl)
    {
        String key = hashKeyForDisk(imageUrl);
        try
        {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null)
            {
                OutputStream outputStream = editor.newOutputStream(0);
                boolean tmp = downloadUrlToStream(imageUrl, outputStream);
                outputStream.close();
                if (tmp)
                {
                    editor.commit();
                } else
                {
                    editor.abort();
                }
            }
            mDiskLruCache.flush();
            LogUtile.systemOut("将图片加入到磁盘缓存+" + imageUrl);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private boolean downloadUrlToStream(String urlString, OutputStream outputStream)
    {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        InputStream inputStream = null;
        try
        {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            inputStream = urlConnection.getInputStream();
            in = new BufferedInputStream(inputStream, 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1)
            {
                out.write(b);
            }
            in.close();
            out.close();
            urlConnection.disconnect();
            inputStream.close();
            return true;
        } catch (final IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private String hashKeyForDisk(String key)
    {
        String cacheKey;
        try
        {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e)
        {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++)
        {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1)
            {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
