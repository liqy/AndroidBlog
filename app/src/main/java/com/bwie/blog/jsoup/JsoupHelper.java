package com.bwie.blog.jsoup;

import com.bwie.blog.Constants;
import com.github.florent37.retrojsoup.RetroJsoup;

import org.jsoup.Jsoup;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by liqy on 2017/9/10.
 */

public class JsoupHelper {
    private static OkHttpClient mOkHttpClient;

    static {
        initOkHttpClient();
    }

    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private static void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (Jsoup.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder()
                            .addInterceptor(interceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }

    private static <T> T createApi(Class<T> clazz, String webUrl) {
        return new RetroJsoup.Builder()
                .url(webUrl)
                .client(new OkHttpClient())
                .build()
                .create(clazz);
    }

    public static BlogApi getBlogFeed(int page) {
        return createApi(BlogApi.class, Constants.getIndexUrl(page));
    }

    public static WeeklyApi getWeeklyFeed(int page) {
        return createApi(WeeklyApi.class, Constants.getWeeklyUrl(page));
    }

    public static WeeklyApi getWeeklyIssue(String url) {
        return createApi(WeeklyApi.class, Constants.getWeeklyIssue(url));
    }



}
