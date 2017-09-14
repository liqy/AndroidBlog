package com.bwie.blog.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.blog.R;
import com.bwie.blog.model.Article;
import com.bwie.blog.model.Feed;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liqy on 2017/9/10.
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<Article> articles;
    OnItemClickListener listener;

    public ArticleAdapter() {
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setArticles(List<Article> articles) {
        if (this.articles == null) {
            this.articles = new ArrayList<>();
        }
        this.articles.addAll(articles);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Article article = this.articles.get(position);
        if (holder instanceof ArticleViewHolder) {
            ArticleViewHolder articleViewHolder = (ArticleViewHolder) holder;
            articleViewHolder.title.setText(article.title);
            articleViewHolder.desc.setText(article.subTitle);

            articleViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.onItemClick(view,article);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return articles==null?0:articles.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.feed_title)
        TextView title;
        @BindView(R.id.feed_desc)
        TextView desc;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view,Article article);
    }

}

