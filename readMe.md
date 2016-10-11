title: Android imageview加载实现
date: 2016-10-11

----

显示图片，作为APP开发中必不可少的一部分，也是问题大户。加载图片时主要分为两种，一种是加载网络图片另一种就是加载本地图片。在图片的加载中，我们为了快速的显示图片，肯定会用到缓存机制，为了避免大图出现OOM，还要对图片进行压缩。今天我们从各个角度分析一下在项目中，图片使用时会遇到的一些坑。

### 图片加载

#### 离线图片加载

加载资源目录下的文件：
BitmapFactory.decodeResource(Resources, resId, options);

加载其他目录下的文件：
BitmapFactory.decodeFile(path, options);

#### 在线图片加载

##### 首先要下载图片资源

    /**
     * 获取网络图片
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public Bitmap GetImageInputStream(String imageurl){
        URL url;
        HttpURLConnection connection=null;
        Bitmap bitmap=null;
        try {
            url = new URL(imageurl);
            connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(10000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream=connection.getInputStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

加载图片要放到子线程里面处理，所以再获取到图片后就要回调设置imageview的图片。

使用线程下载资源的时候记得开启一个线程池管理线程资源。(建议一核开三个线程，但是你的APP除了下载还需要做其他事情所以我建议线程池个数 = 核数*3/2)。


### 图片压缩

为什么要压缩图片？

因为在加载大图的时候可能出现oom异常，在这里需要注意的是，把本地资源图片放到正确的文件夹下，否则可能导致图片所占内存急剧指数倍曾加。

图片的压缩分为两种，一种叫做质量压缩，一种叫做大小压缩。
质量压缩是比较好的一种选择，因为质量压缩可以避免将大的bitmap放到内存中去处理，避免是内存吃紧。

#### 质量压缩实现
质量压缩的原理就是重新计算比例并设置bitmap的BitmapFactory.Options。

##### 压缩比例的获取
在图片处理的时候，我们大部分都是对图片的质量进行压缩，这也是我们所有需要的，你可能会好奇那为什么不直接把图片的扩大也做了。主要原因是，我们在app里面使用的大部分图片资源是矢量图，而矢量图的特点就是在压缩的时候不会失真，但是在扩大的时候就会失真，导致图片模糊。

在计算图片的压缩比例前，我们要知道资源bitmap的宽高以及目标imageview的宽高，之后用bitmap的宽高去除imageview的宽高，获取两个值中较大的一个作为压缩比例。

注意：在获取bitmap的宽高时，为了不把这个大的bitmap添加到内存中，options.inJustDecodeBounds = true; // 设置成了true,不占用内存，只获取bitmap宽高

    /**
     * @description 计算图片的压缩比率
     *
     * @param options 参数
     * @param reqWidth 目标的宽度
     * @param reqHeight 目标的高度
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


在获取到压缩比例后：

    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight); // 调用上面定义的方法计算inSampleSize值
    // 使用获取到的inSampleSize值再次解析图片
    options.inJustDecodeBounds = false;
    Bitmap src = BitmapFactory.decodeResource(res, resId, options); // 载入一个稍大的缩略图

### 图片缓存处理

#### 软缓存

软缓存，就是内存缓存，Lru算法实现：

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

#### 磁盘缓存

[DiskLruCache](https://github.com/JakeWharton/DiskLruCache)

### 自定义图片请求框架实现

* 1 下载图片前先去内存缓存中读取，读取不到再去磁盘缓存中读取，再没有再去网络上加载图片。
* 2 网络加载图片，计算压缩比例，并压缩bitmap,然后将bitmap放到内存缓存中和磁盘缓存中，设置图片。

### 注意
* 合理的线程池大小(核数*3/2)。
* 合理的内存缓存大小(1/8)。
* 合理的磁盘缓存大小(15M)。




