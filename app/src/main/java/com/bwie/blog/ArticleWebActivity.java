package com.bwie.blog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.bwie.blog.model.Article;
import com.just.library.AgentWeb;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleWebActivity extends AppCompatActivity {

    @BindView(R.id.web)
    LinearLayout linearLayout;

    AgentWeb agentWeb;

    public static void open(Activity activity, Article article) {
        Intent intent = new Intent(activity, ArticleWebActivity.class);
        intent.putExtra("Article", article);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_web);
        ButterKnife.bind(this);

        Article article=getIntent().getParcelableExtra("Article");

        agentWeb = AgentWeb.with(this)//
                .setAgentWebParent(linearLayout, new LinearLayout.LayoutParams(-1, -1))//
                .useDefaultIndicator()//
                .defaultProgressBarColor()
                .createAgentWeb()//
                .ready()
                .go(article.url);
    }


}
