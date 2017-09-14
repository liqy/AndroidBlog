package com.bwie.blog.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.florent37.retrojsoup.annotations.JsoupHref;
import com.github.florent37.retrojsoup.annotations.JsoupText;

/**
 * Created by liqy on 2017/9/9.
 */

public class Feed implements Parcelable {

    @JsoupText("strong")
    public String title;

    @JsoupHref("a")
    public String url;

    @JsoupText("p")
    public String desc;

    @JsoupText("time")
    public String time;

    @JsoupText("em.status1")
    public String author;

    @Override
    public String toString() {
        return "Feed{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", desc='" + desc + '\'' +
                ", time='" + time + '\'' +
                ", author='" + author + '\'' +
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
        dest.writeString(this.desc);
        dest.writeString(this.time);
        dest.writeString(this.author);
    }

    public Feed() {
    }

    protected Feed(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
        this.desc = in.readString();
        this.time = in.readString();
        this.author = in.readString();
    }

    public static final Parcelable.Creator<Feed> CREATOR = new Parcelable.Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel source) {
            return new Feed(source);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };
}
