package com.bwie.blog.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.blog.R;
import com.bwie.blog.model.Feed;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liqy on 2017/9/10.
 */

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Feed> feeds;
    OnItemClickListener listener;

    public FeedAdapter() {

    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setFeeds(List<Feed> feeds, int page) {
        if (this.feeds == null) {
            this.feeds = new ArrayList<>();
        }
        int size=getItemCount();
        this.feeds.addAll(feeds);
        notifyItemRangeChanged(size,feeds.size());

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Feed feed = this.feeds.get(position);
        if (holder instanceof FeedViewHolder) {
            FeedViewHolder feedViewHolder = (FeedViewHolder) holder;
            feedViewHolder.author.setText(feed.time + feed.author);
            feedViewHolder.desc.setText(feed.desc);
            feedViewHolder.title.setText(feed.title);

            feedViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(view, feed);
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return feeds == null ? 0 : feeds.size();
    }

    static class FeedViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.feed_title)
        TextView title;
        @BindView(R.id.feed_author)
        TextView author;
        @BindView(R.id.feed_desc)
        TextView desc;

        public FeedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Feed feed);
    }

}
