package com.sushmobile.albumslist.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedHashMap;

public class Album implements Parcelable{

    private int albumId;
    private String title;
    private User user;
    private LinkedHashMap<String, String> urlImages;

    public Album(Parcel parcel)
    {
        setAlbumId(parcel.readInt());
        setTitle(parcel.readString());
        setUser((User) parcel.readParcelable(User.class.getClassLoader()));
    }

    public Album(int albumId, String title, User user) {
        this.albumId = albumId;
        this.title = title;
        this.user = user;
    }

    public int getAlbumId() {
        return albumId;
    }

    private void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public LinkedHashMap<String, String> getUrlImages() {
        return urlImages;
    }

    public void setUrlImageLarge(LinkedHashMap<String, String> urlImages) { this.urlImages = urlImages; }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) { this.title = title; }

    public User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() {
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(albumId);
        parcel.writeString(title);
        parcel.writeParcelable(user, i);
    }
}
