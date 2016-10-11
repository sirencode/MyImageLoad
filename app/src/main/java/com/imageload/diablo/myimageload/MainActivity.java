package com.imageload.diablo.myimageload;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Diablo
 */

public class MainActivity extends Activity implements View.OnClickListener, NetHttpsGetAndPost
        .OnHttpsGetAndPostListener
{

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews()
    {
        textView = (TextView) findViewById(R.id.tv_info);
        Button urlGet, urlPost, getImg;
        urlGet = (Button) findViewById(R.id.btn_urlGet);
        urlPost = (Button) findViewById(R.id.btn_urlPost);
        getImg = (Button) findViewById(R.id.btn_getImg);
        urlGet.setOnClickListener(this);
        urlPost.setOnClickListener(this);
        getImg.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v)
    {
        final List<BaseKeyValueDto> headers = new ArrayList<>();
        BaseKeyValueDto header = new BaseKeyValueDto("zoneCode", "0575");
        BaseKeyValueDto header1 = new BaseKeyValueDto("token",
                "58621268-04f3-4cc7-a57b-6471f41eb116");
        headers.add(header);
        headers.add(header1);
        NetHttpsGetAndPost httpsGetAndPost = new NetHttpsGetAndPost(getBaseContext());
        httpsGetAndPost.setOnHttpsGetAndPostListener(this);

        switch (v.getId())
        {
            case R.id.btn_urlGet:
                httpsGetAndPost.doGet(Config.URL, headers, "zhengshu.bks", Config.BKSFILEKEY);
                break;

            case R.id.btn_urlPost:
                httpsGetAndPost.doPost(Config.POSTURL, Config.POSTPARAM, headers, "zhengshu.bks",
                        Config.BKSFILEKEY);
                break;

            case R.id.btn_getImg:
                Intent intent = new Intent(MainActivity.this,ListViewActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }

    }

    @Override
    public void OnHttpsGetAndPostSucceed(final String result)
    {
        runOnUiThread(new Runnable()
        {

            @Override
            public void run()
            {
                textView.setText(result);
            }
        });

    }

    @Override
    public void OnHttpsGetAndPostError(final String errorInfo)
    {
        runOnUiThread(new Runnable()
        {

            @Override
            public void run()
            {
                ToastUtile.showToast(getBaseContext(), errorInfo);
            }
        });

    }
}
