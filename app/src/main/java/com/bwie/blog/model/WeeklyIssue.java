package com.bwie.blog.model;

import com.github.florent37.retrojsoup.annotations.JsoupHref;
import com.github.florent37.retrojsoup.annotations.JsoupText;

/**
 * Created by liqy on 2017/9/10.
 */

public class WeeklyIssue {

    @JsoupText("li > p > a")
    public String title;

    @JsoupHref("li > p > a")
    public String url;

    @Override
    public String toString() {
        return "WeeklyIssue{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
