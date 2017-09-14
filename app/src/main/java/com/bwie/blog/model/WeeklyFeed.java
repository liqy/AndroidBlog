package com.bwie.blog.model;

import com.github.florent37.retrojsoup.annotations.JsoupHref;
import com.github.florent37.retrojsoup.annotations.JsoupText;

/**
 * Created by liqy on 2017/9/10.
 */

public class WeeklyFeed {

    @JsoupText("h2.h4,h2.title")
    public String title;

    @JsoupHref("h2.h4 > a")
    public String url;

    @Override
    public String toString() {
        return "WeeklyFeed{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
