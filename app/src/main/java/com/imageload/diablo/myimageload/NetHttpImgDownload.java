package com.imageload.diablo.myimageload;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 作者： Diablo on 16/1/4.
 */
public class NetHttpImgDownload
{
    private Activity mActivity;

    public static DiskLruCache mDiskLruCache;

    private DiskCacheHelp mDiskCacheHelp;

    public NetHttpImgDownload(Activity activity)
    {
        this.mActivity = activity;
        mDiskCacheHelp = new DiskCacheHelp();
        initDiskCache();
    }

    /**
     * 网络图片加载
     *
     * @param shortSide 设置图片的显示短边，根据短边判断是否需要缩放以及缩放比例
     *                  如果不需要压缩，可以赋值:0
     * @param imageUrl  图片地址
     * @param imageView 目标view
     */
    public void setImgBitmap(final String imageUrl, final ImageView
            imageView, final int shortSide)
    {

        Bitmap getBitmap = getBitmapFromeCache(imageUrl);

        if (getBitmap != null)
        {
            LogUtile.systemOut("从缓存中获取资源+" + imageUrl);
            setImgView(imageView, getBitmap);
        } else
        {
            NetConfig.MYPOOL.execute(new Runnable()
            {

                @Override
                public void run()
                {
                    if (IsInternetConnect.isNetworkAvailable(mActivity))
                    {

                        try
                        {
                            URL url = new URL(imageUrl);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            // 设置是否从httpUrlConnection读入，默认情况下是true;
                            connection.setDoInput(true);
                            connection.setRequestMethod("GET");
                            //设置请求超时
                            connection.setConnectTimeout(NetConfig.CONNTIMEOUT);
                            connection.setReadTimeout(NetConfig.READTIMEOUT);
                            int resultCode = connection.getResponseCode();
                            LogUtile.systemOut("图片请求码：" + resultCode);
                            if (resultCode == HttpURLConnection.HTTP_OK)
                            {
                                InputStream is = new BufferedInputStream(connection
                                        .getInputStream());
                                is.mark(10 * 1024 * 1024);
                                BitmapFactory.Options opts = new BitmapFactory.Options();
                                opts.inJustDecodeBounds = true;
                                Bitmap bitmap = BitmapFactory.decodeStream(is, null, opts);
                                LogUtile.systemOut("网络图片原始宽+" + opts.outWidth + ":网络图片原始高+" + opts
                                        .outHeight);
                                int shortTargetSide = (opts.outWidth <= opts.outHeight) ? opts
                                        .outWidth : opts.outHeight;
                                int scal = getScalSize(shortTargetSide, shortSide);
                                opts.inSampleSize = scal;
                                LogUtile.systemOut("图片缩放比例=" + scal);
                                opts.inJustDecodeBounds = false;
                                is.reset();
                                bitmap = BitmapFactory.decodeStream(is, null, opts);
                                is.close();
                                if (imageView == null)
                                {
                                    LogUtile.systemOut("imageView=null");
                                    return;
                                }
                                LogUtile.systemOut("downloadImg+" + imageUrl + "+完成");
                                setImgView(imageView, bitmap);
                                addBitmapToMemoryCache(imageUrl, bitmap);
                                mDiskCacheHelp.setDiskCacheBitmap(mDiskLruCache, imageUrl);
                            } else
                            {
                                showErro(mActivity.getResources().getString(R.string.httpErrorResponseCode) + resultCode);
                            }
                        } catch (IOException e)
                        {
                            LogUtile.systemOut("图片请求异常===>" + e.getMessage());
                            e.printStackTrace();
                        }

                    } else
                    {
                        showErro(mActivity.getResources().getString(R.string.httpInternetIsNotUseful));
                    }
                }
            });
        }

    }

    /**
     * 从内存缓存中获取图片 Tile:getBitmapFromCache
     *
     * @param url
     * @return Bitmap
     */
    private Bitmap getBitmapFromMemoryCache(String url)
    {
        return NetConfig.MEMORYLRUCACHE.get(url);
    }

    /**
     * 将图片增加到内存缓存 Tile:addBitmapToMemoryCache
     *
     * @param url
     * @param bitmap void
     */
    private void addBitmapToMemoryCache(String url, Bitmap bitmap)
    {
        if (getBitmapFromMemoryCache(url) == null && bitmap != null)
        {
            NetConfig.MEMORYLRUCACHE.put(url, bitmap);
            LogUtile.systemOut("将图片加入到内存缓存+" + url);
        }
    }

    /**
     * 初始化磁盘缓存
     */
    private void initDiskCache()
    {
        if (NetHttpImgDownload.mDiskLruCache == null)
        {
            try
            {
                String cachePath = mActivity.getCacheDir().getPath();
                //缓存路径
                File cacheDir = new File(cachePath + File.separator + "imagecache");
                if (!cacheDir.exists())
                {
                    cacheDir.mkdirs();
                }
                mDiskLruCache = DiskLruCache.open(cacheDir, mDiskCacheHelp.getAppVersion(mActivity),
                        1, NetConfig.DISK_CACHE_SIZE);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void showErro(final String info)
    {
        mActivity.runOnUiThread(new Runnable()
        {

            @Override
            public void run()
            {
                ToastUtile.showToast(mActivity, info);
            }
        });

    }

    private void setImgView(final ImageView imgView, final Bitmap bitmap)
    {
        mActivity.runOnUiThread(new Runnable()
        {

            @Override
            public void run()
            {
                imgView.setBackgroundResource(0);
                imgView.setScaleType(ImageView.ScaleType.FIT_XY);
                imgView.setImageBitmap(bitmap);
            }
        });
    }

    /**
     * 获取图片的bitmap资源
     *
     * @param imageUrl
     * @return
     */
    private Bitmap getBitmapFromeCache(final String imageUrl)
    {
        //判断软缓存里面是否存在
        Bitmap cacheBitmap = NetConfig.MEMORYLRUCACHE.get(imageUrl);
        if (cacheBitmap != null)
        {
            LogUtile.systemOut("从内存缓存中拿到资源");
            return cacheBitmap;
        }
        //判断文件缓存里面是否存在
        else if ((cacheBitmap = mDiskCacheHelp.getBitmapFromeDiskCache(mDiskLruCache, imageUrl))
                != null)
        {
            LogUtile.systemOut("从磁盘缓存中拿到资源");
            addBitmapToMemoryCache(imageUrl, cacheBitmap);
            return cacheBitmap;
        }
        return null;
    }

    /**
     * 计算图片采样比例
     *
     * @param shortTargetSide 获取到的图片的短边
     * @param shortSide       图片显示较短的边
     * @return 采样比例
     */
    private int getScalSize(int shortTargetSide, int shortSide)
    {
        float scal = shortTargetSide / (float) shortSide;
        if (scal <= 1 || shortSide == 0)
        {
            scal = 1;
        } else
        {
            scal = shortTargetSide / shortSide + 1;
        }
        return (int) scal;
    }
}