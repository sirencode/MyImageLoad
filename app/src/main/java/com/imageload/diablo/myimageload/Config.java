package com.imageload.diablo.myimageload;

/**
 * 作者： Diablo on 16/1/18.
 */
public class Config
{
    /**
     * 证书文件密码
     */
    public static final String BKSFILEKEY = "paic1024";
    /**
     * get请求的url
     */
    public static final String URL = "https://test2-mhis-siapp.pingan.com" +
            ".cn:20443/mhis-siapp/open/policyQuery/indistinctlist" +
            ".do?start=0&limit=20&typeID=0&title=";
    /**
     * post请求的url地址
     */
    public static final String POSTURL = "https://test2-mhis-siapp.pingan.com" +
            ".cn:20443/mhis-siapp/security/continueAnagraph/applyAnagraph.do";
    /**
     * post请求的地址
     */
    public static final String POSTPARAM = "{\"drugDetail\":\"[{\\\"itemCoding" +
            "\\\":\\\"10808063109024840101\\\",\\\"imageUrl\\\":\\\"http:\\/\\/7xolri.com1.z0.glb" +
            ".clouddn.com\n" +
            "\\/79.png\\\",\\\"itemName\\\":\\\"拜唐苹\\\",\\\"specifications\\\":\\\"50毫克\\/30片   " +
            "\\\"," +
            "\\\"number\n" +
            "\\\":4}]\",\"diseaseType\":\"5\",\"talkContent\":\"hcuufufhxhxhxhdyx\"}";
}
