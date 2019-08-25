package com.mobi.videoplayer.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Video implements Parcelable {

    @SerializedName("duration")
    private int duration;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("description")
    private String description;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    protected Video(Parcel in) {
        duration = in.readInt();
        thumbnail = in.readString();
        description = in.readString();
        title = in.readString();
        url = in.readString();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(duration);
        dest.writeString(thumbnail);
        dest.writeString(description);
        dest.writeString(title);
        dest.writeString(url);
    }
}