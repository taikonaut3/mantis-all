package io.github.astro.mantis.transport.okhttp;

import io.github.astro.mantis.transport.http.AbstractHttpClient;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/6 16:40
 */
public class OkHttpClient extends AbstractHttpClient {

    private okhttp3.OkHttpClient okHttpClient;

    public OkHttpClient(){
        okHttpClient=new okhttp3.OkHttpClient();
    }


}
