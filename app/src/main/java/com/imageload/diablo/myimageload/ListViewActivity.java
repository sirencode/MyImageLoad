package com.imageload.diablo.myimageload;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者： Diablo on 16/1/12.
 */
public class ListViewActivity extends Activity
{
    private List<String> mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listimg);
        ListView listView = (ListView) findViewById(R.id.listview);
        getDate();
        MyAdapter myAdapter = new MyAdapter(ListViewActivity.this);
        listView.setAdapter(myAdapter);
    }

    private void getDate()
    {
        mDate = new ArrayList<>();
//        mDate.add("http://b.hiphotos.baidu.com/zhidao/pic/item/4afbfbedab64034f218260e4aac379310b551d80.jpg");
//        mDate.add("http://pic.paopaoche.net/up/201405/105742_1934079825878.jpg");
//        mDate.add("http://www.bz55.com/uploads/allimg/110919/0T2303439-1.jpg");
        mDate.add("http://img2.imgtn.bdimg.com/it/u=3677754239,3263454553&fm=21&gp=0.jpg");
        mDate.add("http://img4.imgtn.bdimg.com/it/u=4131892735,3748845901&fm=21&gp=0.jpg");
        mDate.add("http://img0.imgtn.bdimg.com/it/u=574987393,2125016987&fm=21&gp=0.jpg");
        mDate.add("http://img2.imgtn.bdimg.com/it/u=317338926,1517517944&fm=21&gp=0.jpg");
        mDate.add("http://img0.imgtn.bdimg.com/it/u=1115863207,544277183&fm=21&gp=0.jpg");
        mDate.add("http://img5.imgtn.bdimg.com/it/u=2232065429,3024630520&fm=21&gp=0.jpg");
        mDate.add("http://img2.imgtn.bdimg.com/it/u=2647093936,4111308860&fm=21&gp=0.jpg");
        mDate.add("http://img5.imgtn.bdimg.com/it/u=340877906,2317876029&fm=21&gp=0.jpg");
        mDate.add("http://img1.imgtn.bdimg.com/it/u=2177387723,3195388590&fm=21&gp=0.jpg");
        mDate.add("http://img3.imgtn.bdimg.com/it/u=695441189,2277807280&fm=21&gp=0.jpg");
        mDate.add("http://img3.imgtn.bdimg.com/it/u=229495,28260110&fm=21&gp=0.jpg");
        mDate.add("http://img2.imgtn.bdimg.com/it/u=1294110515,1798378659&fm=21&gp=0.jpg");
        mDate.add("http://img4.imgtn.bdimg.com/it/u=1912971562,4065400264&fm=21&gp=0.jpg");
        mDate.add("http://img4.imgtn.bdimg.com/it/u=1514664523,535660869&fm=21&gp=0.jpg");
        mDate.add("http://img2.imgtn.bdimg.com/it/u=626727211,3466281121&fm=21&gp=0.jpg");
        mDate.add("http://img2.imgtn.bdimg.com/it/u=851712417,277072547&fm=21&gp=0.jpg");
        mDate.add("http://img4.imgtn.bdimg.com/it/u=3232335751,798701403&fm=21&gp=0.jpg");
        mDate.add("http://img3.imgtn.bdimg.com/it/u=861775334,4151366117&fm=21&gp=0.jpg");
        mDate.add("http://img0.imgtn.bdimg.com/it/u=3710348459,2970387773&fm=21&gp=0.jpg");
        mDate.add("http://img3.imgtn.bdimg.com/it/u=1894021492,4185603231&fm=21&gp=0.jpg");
        mDate.add("http://img1.imgtn.bdimg.com/it/u=209613493,3027825468&fm=21&gp=0.jpg");
        mDate.add("http://img2.imgtn.bdimg.com/it/u=1428654695,4099024897&fm=21&gp=0.jpg");
        mDate.add("http://img4.imgtn.bdimg.com/it/u=2967018767,3750560889&fm=21&gp=0.jpg");
        mDate.add("http://img3.imgtn.bdimg.com/it/u=2055954865,2226849956&fm=21&gp=0.jpg");
        mDate.add("http://img1.imgtn.bdimg.com/it/u=3705774160,858244387&fm=21&gp=0.jpg");
        mDate.add("http://img4.imgtn.bdimg.com/it/u=987946719,1466495407&fm=21&gp=0.jpg");
        mDate.add("http://img5.imgtn.bdimg.com/it/u=1506830482,923237899&fm=21&gp=0.jpg");
        mDate.add("http://img0.imgtn.bdimg.com/it/u=1469442855,1379640099&fm=21&gp=0.jpg");
        mDate.add("http://img4.imgtn.bdimg.com/it/u=1598427350,2482094282&fm=21&gp=0.jpg");
        mDate.add("http://img3.imgtn.bdimg.com/it/u=1192558330,1421640593&fm=21&gp=0.jpg");
        mDate.add("http://img1.imgtn.bdimg.com/it/u=937162787,575547566&fm=21&gp=0.jpg");
        mDate.add("http://img5.imgtn.bdimg.com/it/u=2954290589,2043102304&fm=21&gp=0.jpg");
        mDate.add("http://img0.imgtn.bdimg.com/it/u=73164068,980110120&fm=21&gp=0.jpg");
        mDate.add("http://img3.imgtn.bdimg.com/it/u=674509045,2357703680&fm=21&gp=0.jpg");
        mDate.add("http://img4.imgtn.bdimg.com/it/u=4110314872,2385616720&fm=21&gp=0.jpg");
        mDate.add("http://img4.imgtn.bdimg.com/it/u=2049727680,2846882852&fm=21&gp=0.jpg");
        mDate.add("http://img5.imgtn.bdimg.com/it/u=1175666296,1367725087&fm=21&gp=0.jpg");
        mDate.add("http://img1.imgtn.bdimg.com/it/u=3177570836,28089341&fm=21&gp=0.jpg");
        mDate.add("http://img0.imgtn.bdimg.com/it/u=1723952624,3865523978&fm=21&gp=0.jpg");
        mDate.add("http://img1.imgtn.bdimg.com/it/u=3945447858,2394733367&fm=21&gp=0.jpg");
        mDate.add("http://img1.imgtn.bdimg.com/it/u=1828649096,2052635267&fm=21&gp=0.jpg");
        mDate.add("http://img5.imgtn.bdimg.com/it/u=3060398667,1261913250&fm=21&gp=0.jpg");
        mDate.add("http://img5.imgtn.bdimg.com/it/u=1879985713,1894892380&fm=21&gp=0.jpg");
        mDate.add("http://img3.imgtn.bdimg.com/it/u=1273830296,3767151329&fm=21&gp=0.jpg");
        mDate.add("http://img4.imgtn.bdimg.com/it/u=1094324233,3641691967&fm=21&gp=0.jpg");
        mDate.add("http://img4.imgtn.bdimg.com/it/u=2961294910,127166370&fm=21&gp=0.jpg");
        mDate.add("http://img1.imgtn.bdimg.com/it/u=1588835297,3519548466&fm=21&gp=0.jpg");
        mDate.add("http://img5.imgtn.bdimg.com/it/u=2819893759,3910013402&fm=21&gp=0.jpg");
        mDate.add("http://img2.imgtn.bdimg.com/it/u=3619184182,3413103728&fm=21&gp=0.jpg");
        mDate.add("http://img1.imgtn.bdimg.com/it/u=529018256,523247915&fm=21&gp=0.jpg");
        mDate.add("http://img0.imgtn.bdimg.com/it/u=1520741295,2576626257&fm=21&gp=0.jpg");
        mDate.add("http://img2.imgtn.bdimg.com/it/u=1891323530,2201666208&fm=21&gp=0.jpg");
        mDate.add("http://img1.imgtn.bdimg.com/it/u=3092155721,2481959010&fm=21&gp=0.jpg");
        mDate.add("http://img5.imgtn.bdimg.com/it/u=3860138775,167638982&fm=21&gp=0.jpg");
        mDate.add("http://img3.imgtn.bdimg.com/it/u=3228751775,4205711948&fm=21&gp=0.jpg");
        mDate.add("http://img4.imgtn.bdimg.com/it/u=414380661,3248104825&fm=21&gp=0.jpg");
        mDate.add("http://img2.imgtn.bdimg.com/it/u=3814607960,351950688&fm=21&gp=0.jpg");
        mDate.add("http://img4.imgtn.bdimg.com/it/u=3287880519,2909812331&fm=21&gp=0.jpg");

    }

    public final class ViewHolder
    {
        public ImageView img;
    }

    class MyAdapter extends BaseAdapter
    {
        LayoutInflater mInflater;
        NetHttpImgDownload httpsDownloadImg;

        public MyAdapter(Activity context)
        {
            mInflater = LayoutInflater.from(context);
            httpsDownloadImg = new NetHttpImgDownload(context);
        }

        @Override
        public int getCount()
        {
            return mDate.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder holder = null;
            if (convertView == null)
            {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.listitem, null);
                holder.img = (ImageView) convertView.findViewById(R.id.iv_item);
                convertView.setTag(holder);
            } else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.img.setTag(mDate.get(position));
            httpsDownloadImg.setImgBitmap(mDate.get(position), holder.img,150);
            return convertView;
        }
    }

}
