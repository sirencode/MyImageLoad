package com.imageload.diablo.myimageload;

import android.content.Context;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 作者： Diablo on 15/12/29.
 * 自从2.3以后对httpurlconnection做了优化(GZIP)，并且性能能够也比Httpclient更佳
 * 在API23中就不再支持httpclient
 */
public class NetHttpsGetAndPost
{

    private Context mContext;

    private OnHttpsGetAndPostListener mOnHttpsGetAndPostListener;

    public NetHttpsGetAndPost(Context context)
    {
        this.mContext = context;
    }

    public void setOnHttpsGetAndPostListener(OnHttpsGetAndPostListener onHttpsGetAndPostListener)
    {
        this.mOnHttpsGetAndPostListener = onHttpsGetAndPostListener;
    }

    private String doHttpURLGet(final String urlPath, List<BaseKeyValueDto> headers, String
            bksName, String bksPAW) throws IOException
    {
        boolean isHttps = urlPath.startsWith("https");
        HttpURLConnection conn = null;
        if (isHttps)
        {
            InputStream inputStream = mContext.getAssets().open(bksName);
            conn = trustOrNot(urlPath, inputStream, bksPAW);
        }

        URL url = new URL(urlPath);
        //利用HttpURLConnection对象从网络中获取网页数据
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        //不使用缓存
        conn.setUseCaches(false);
        //设置请求超时
        conn.setConnectTimeout(NetConfig.CONNTIMEOUT);
        conn.setReadTimeout(NetConfig.READTIMEOUT);

        if ((headers != null) && headers.size() > 0)
        {
            for (int i = 0; i < headers.size(); i++)
            {
                conn.setRequestProperty(headers.get(i).getKey(), headers.get(i).getValue());
            }
        }

        int resultCode = conn.getResponseCode();
        LogUtile.systemOut("<======resultCode========>" + resultCode);
        if (resultCode == HttpURLConnection.HTTP_OK)
        {
            InputStream is = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String response = "";
            String readLine = null;
            while ((readLine = br.readLine()) != null)
            {
                response = response + readLine;
            }
            br.close();
            inputStreamReader.close();
            is.close();
            conn.disconnect();
            LogUtile.systemOut("httpsGet===>" + response);
            return response;
        } else
        {
            mOnHttpsGetAndPostListener.OnHttpsGetAndPostError(mContext.getResources().getString(R.string.httpErrorResponseCode));
            return null;
        }
    }

    /**
     * httpurlconnecton get请求，自动判断是否是https请求，如果是 还可以通过参数
     *
     * @param urlPath 请求地址
     * @param headers 请求头列表
     * @param bksName bks文件名，将bks放到assets目录(如果是http请求 设置为null）
     * @param bksPAW  bks文件对应的密码(如果是http请求 设置为null）
     */
    public void doGet(final String urlPath, final List<BaseKeyValueDto> headers, final String
            bksName, final String bksPAW)
    {
        NetConfig.MYPOOL.execute(new Runnable()
        {

            @Override
            public void run()
            {
                try
                {
                    if (IsInternetConnect.isNetworkAvailable(mContext))
                    {
                        String result = doHttpURLGet(urlPath, headers, bksName, bksPAW);
                        if (result != null)
                        {
                            mOnHttpsGetAndPostListener.OnHttpsGetAndPostSucceed(result);
                        } else
                        {
                            mOnHttpsGetAndPostListener.OnHttpsGetAndPostError(mContext.getResources().getString(R.string.httpErrorResult));
                        }
                    } else
                    {
                        mOnHttpsGetAndPostListener.OnHttpsGetAndPostError(mContext.getResources().getString(R.string.httpInternetIsNotUseful));
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                    mOnHttpsGetAndPostListener.OnHttpsGetAndPostError(mContext.getResources().getString(R.string.httpRequestException));
                }
            }
        });

    }

    private String doHttpURLPost(final String urlPath, String parameter,
                                 List<BaseKeyValueDto> headers, String bksName,
                                 String bksPAW) throws IOException
    {
        boolean isHttps = urlPath.startsWith("https");
        HttpURLConnection conn = null;
        if (isHttps)
        {
            InputStream inputStream = mContext.getAssets().open(bksName);
            conn = trustOrNot(urlPath, inputStream, bksPAW);
        }

        URL url = new URL(urlPath);
        //利用HttpURLConnection对象从网络中获取网页数据
        conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(false);
        //设置请求超时
        conn.setConnectTimeout(NetConfig.CONNTIMEOUT);
        conn.setReadTimeout(NetConfig.READTIMEOUT);

        if ((headers != null) && headers.size() > 0)
        {
            for (int i = 0; i < headers.size(); i++)
            {
                conn.setRequestProperty(headers.get(i).getKey(), headers.get(i).getValue());
            }
        }
        conn.connect();
        OutputStream outputStream = conn.getOutputStream();
        DataOutputStream out = new DataOutputStream(outputStream);
        out.writeBytes(parameter);
        out.flush();
        out.close();
        outputStream.close();
        int resultCode = conn.getResponseCode();
        LogUtile.systemOut("<======resultCode========>" + resultCode);
        if (resultCode == HttpURLConnection.HTTP_OK)
        {
            InputStream is = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(inputStreamReader);
            inputStreamReader.close();
            String response = "";
            String readLine = null;
            while ((readLine = br.readLine()) != null)
            {
                response = response + readLine;
            }
            is.close();
            br.close();
            conn.disconnect();
            LogUtile.systemOut("httpsPost===>" + response);
            return response;
        } else
        {
            String info = mContext.getResources().getString(R.string.httpErrorResponseCode);
            mOnHttpsGetAndPostListener.OnHttpsGetAndPostError(info);
            LogUtile.systemOut(info);
            return null;
        }
    }

    /**
     * httpurlconnecton post请求 自动判断是否是https请求
     *
     * @param urlPath   请求地址
     * @param parameter 请求参数
     * @param headers   请求头列表
     * @param bksName   bks文件名，将bks放到assets目录(如果是http请求 设置为null）
     * @param bksPAW    bks文件对应的密码(如果是http请求 设置为null）
     */
    public void doPost(final String urlPath, final String parameter,
                       final List<BaseKeyValueDto> headers, final String bksName,
                       final String bksPAW)
    {
        NetConfig.MYPOOL.execute(new Runnable()
        {

            @Override
            public void run()
            {
                try
                {
                    if (IsInternetConnect.isNetworkAvailable(mContext))
                    {
                        String result = doHttpURLPost(urlPath, parameter, headers, bksName, bksPAW);
                        if (result != null)
                        {
                            mOnHttpsGetAndPostListener.OnHttpsGetAndPostSucceed(result);
                        } else
                        {
                            mOnHttpsGetAndPostListener.OnHttpsGetAndPostError(mContext.getResources().getString(R.string.httpErrorResult));
                        }
                    } else
                    {
                        mOnHttpsGetAndPostListener.OnHttpsGetAndPostError(mContext.getResources().getString(R.string.httpInternetIsNotUseful));
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                    mOnHttpsGetAndPostListener.OnHttpsGetAndPostError(mContext.getResources().getString(R.string.httpRequestException));
                }
            }
        });
    }

    /**
     * 动态配置是否验证https的证书
     *
     * @param urlPath
     * @param inputStream
     * @param bksPAW
     * @return
     */
    private HttpURLConnection trustOrNot(String urlPath, InputStream
            inputStream, String bksPAW)
    {
        HttpURLConnection conn = null;
        try
        {
            conn = new SetHttpsCer().verifyCertificste(urlPath, inputStream, bksPAW);
        } catch (Exception e)
        {
            e.printStackTrace();
            LogUtile.systemOut("证书校验异常" + e.getMessage());
        }
        return conn;
    }

    public interface OnHttpsGetAndPostListener
    {
        /**
         * 请求数据成功。
         *
         * @param result 返回请求到的数据
         */
        abstract void OnHttpsGetAndPostSucceed(final String result);

        /**
         * 请求失败。
         */
        abstract void OnHttpsGetAndPostError(final String errorInfo);
    }

}
