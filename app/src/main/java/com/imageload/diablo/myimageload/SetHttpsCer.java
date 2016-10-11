package com.imageload.diablo.myimageload;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * 作者： Diablo on 16/1/4.
 */
public class SetHttpsCer
{
    /**
     * https证书校验
     *
     * @param urlPath
     * @param inputStream
     * @param bksPAW
     * @return
     */
    public  HttpsURLConnection verifyCertificste(String urlPath, InputStream inputStream,
                                                        String bksPAW) throws
            NoSuchAlgorithmException, KeyManagementException, IOException, CertificateException,
            KeyStoreException, UnrecoverableKeyException
    {
        URL url = new URL(urlPath);
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

        //通过指定类型inputsream和提供者获取信任密钥库（KeyStore）实例
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        //信任密钥库实例使用约定的密码加载（Load）密钥库文件,加密码确保keyStore完整性
        keyStore.load(inputStream, bksPAW.toCharArray());

        //信任密钥管理器工厂实例使用约定的密码和密钥库进行初始化（Initialize）
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        //初始化KeyManagerFactory用keyStore以及证书密码
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
        kmf.init(keyStore, bksPAW.toCharArray());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        //不验证主机名，允许访问所有主机
        X509HostnameVerifier hostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
        urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());

        return urlConnection;
    }

}
