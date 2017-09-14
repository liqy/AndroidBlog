package com.bwie.blog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.bwie.blog.adapter.ArticleAdapter;
import com.bwie.blog.jsoup.BlogApi;
import com.bwie.blog.model.Article;
import com.bwie.blog.model.Feed;
import com.github.florent37.retrojsoup.RetroJsoup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

public class ArticleListActivity extends BaseActivity implements ArticleAdapter.OnItemClickListener {

    public static void openActivity(Activity activity, Feed feed) {
        Intent intent = new Intent(activity, ArticleListActivity.class);
        intent.putExtra("Feed", feed);
        activity.startActivity(intent);
    }

    @BindView(R.id.article_recycler)
    RecyclerView article_recycler;

    ArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        ButterKnife.bind(this);
        Feed feed = getIntent().getParcelableExtra("Feed");

        article_recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        article_recycler.setLayoutManager(new LinearLayoutManager(this));


        final BlogApi api = new RetroJsoup.Builder()
                .url(Constants.getBlogArticleUrl(feed.url))
                .client(new OkHttpClient())
                .build()
                .create(BlogApi.class);

        api.details()
                .filter(new Predicate<Article>() {
                    @Override
                    public boolean test(@NonNull Article article) throws Exception {
                        return !TextUtils.isEmpty(article.title);
                    }
                })
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Article>>() {
                    @Override
                    public void accept(List<Article> articles) throws Exception {
                        if (adapter == null) {
                            adapter = new ArticleAdapter();
                            adapter.setListener(ArticleListActivity.this);
                            article_recycler.setAdapter(adapter);
                        }
                        adapter.setArticles(articles);
                    }
                });

    }

    @Override
    public void onItemClick(View view, Article article) {
        ArticleWebActivity.open(this, article);
    }
}
