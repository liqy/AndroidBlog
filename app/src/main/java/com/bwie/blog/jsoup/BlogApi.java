package com.bwie.blog.jsoup;

import com.bwie.blog.model.Article;
import com.bwie.blog.model.Feed;
import com.github.florent37.retrojsoup.annotations.Select;

import io.reactivex.Observable;

/**
 * Created by liqy on 2017/9/9.
 */

public interface BlogApi {

    @Select("li")
    Observable<Feed> feeds();

    @Select("article")
    Observable<Article> details();
}
