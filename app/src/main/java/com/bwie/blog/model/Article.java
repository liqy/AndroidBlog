package com.bwie.blog.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.florent37.retrojsoup.annotations.JsoupHref;
import com.github.florent37.retrojsoup.annotations.JsoupText;

/**
 * Created by liqy on 2017/9/9.
 */

public class Article implements Parcelable {


    @JsoupText("h2")
    public String title;
    @JsoupHref("a")
    public String url;

    @JsoupText("h2 + div")
    public String subTitle;

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", subTitle='" + subTitle + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.subTitle);
    }

    public Article() {
    }

    protected Article(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
        this.subTitle = in.readString();
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
