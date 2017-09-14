package com.bwie.blog;

/**
 * Created by liqy on 2017/9/10.
 */

public class Constants {
    public static final String BLOG_BASE_URL = "http://www.androidblog.cn";
    public static final String WEEKLY_BASE_URL = "http://androidweekly.cn";

    public static String getBlogArticleUrl(String path) {
        return BLOG_BASE_URL + path;
    }

    public static String getIndexUrl(int page) {
        return BLOG_BASE_URL + "/index.php/Index/index/p/" + page;
    }

    public static String getWeeklyUrl(int page) {
        return WEEKLY_BASE_URL + "/page/" + page+"/";
    }

    public static String getWeeklyIssue(String url) {
        return WEEKLY_BASE_URL + url;
    }
}
