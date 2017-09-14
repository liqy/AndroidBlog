package com.bwie.blog.jsoup;

import com.bwie.blog.model.Feed;
import com.bwie.blog.model.WeeklyFeed;
import com.bwie.blog.model.WeeklyIssue;
import com.github.florent37.retrojsoup.annotations.Select;

import io.reactivex.Observable;

/**
 * Created by liqy on 2017/9/10.
 */

public interface WeeklyApi {
    @Select("article")
    Observable<WeeklyFeed> feeds();

    @Select("ol")
    Observable<WeeklyIssue> issue();

}
