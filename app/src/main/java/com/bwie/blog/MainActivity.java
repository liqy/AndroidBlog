package com.bwie.blog;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bwie.blog.adapter.FeedAdapter;
import com.bwie.blog.jsoup.JsoupHelper;
import com.bwie.blog.model.Feed;
import com.bwie.blog.model.WeeklyFeed;
import com.bwie.blog.model.WeeklyIssue;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements FeedAdapter.OnItemClickListener {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.feed_recycler)
    RecyclerView recyclerView;
    FeedAdapter feedAdapter;

    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        getWebData(page);

//        getWeeklyData(page);

        getWeeklyIssue();
    }

    private void getWeeklyIssue() {
        JsoupHelper.getWeeklyIssue("/android-dev-weekly-issue-145/").issue()
                .filter(new Predicate<WeeklyIssue>() {
                    @Override
                    public boolean test(@NonNull WeeklyIssue weeklyIssue) throws Exception {
                        return !TextUtils.isEmpty(weeklyIssue.title);
                    }
                })
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<WeeklyIssue>>() {
                    @Override
                    public void accept(List<WeeklyIssue> weeklyIssues) throws Exception {
                        for (WeeklyIssue w :
                                weeklyIssues) {
                            Log.d(getLocalClassName(), w.toString());
                        }
                    }
                });
    }

    private void getWeeklyData(int page) {
        JsoupHelper.getWeeklyFeed(page).feeds().toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<WeeklyFeed>>() {
                    @Override
                    public void accept(List<WeeklyFeed> weeklyFeeds) throws Exception {
                        for (WeeklyFeed w :
                                weeklyFeeds) {
                            Log.d(getLocalClassName(), w.toString());
                        }
                    }
                });


    }

    private void getWebData(final int page) {
        JsoupHelper.getBlogFeed(page).feeds()
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Feed>>() {
                    @Override
                    public void accept(List<Feed> feeds) throws Exception {
                        if (feedAdapter == null) {
                            feedAdapter = new FeedAdapter();
                            feedAdapter.setListener(MainActivity.this);
                            recyclerView.setAdapter(feedAdapter);
                        }
                        feedAdapter.setFeeds(feeds, page);
                        refreshLayout.finishLoadmore();
                    }
                });

    }


    private void initView() {

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getWebData(page);

            }
        });

    }

    @Override
    public void onItemClick(View view, Feed feed) {
        ArticleListActivity.openActivity(MainActivity.this, feed);
    }
}
