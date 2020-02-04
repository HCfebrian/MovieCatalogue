package com.example.moviecataloguefordicoding.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "tv")
public class ModelTV implements Parcelable {
    public static final Creator<ModelTV> CREATOR = new Creator<ModelTV>() {
        @Override
        public ModelTV createFromParcel(Parcel source) {
            return new ModelTV(source);
        }

        @Override
        public ModelTV[] newArray(int size) {
            return new ModelTV[size];
        }
    };
    @SerializedName("id")
    @PrimaryKey
    private int id;
    @SerializedName("name")
    @ColumnInfo(name = "title")
    private String title;
    @SerializedName("overview")
    @ColumnInfo(name = "description")
    private String description;
    @SerializedName("genre")
    @ColumnInfo(name = "genre")
    private String genre;
    @SerializedName("popularity")
    @ColumnInfo(name = "popularity")
    private String popularity;
    @SerializedName("vote_average")
    @ColumnInfo(name = "rating")
    private String rating;
    @SerializedName("first_air_date")
    @ColumnInfo(name = "release")
    private String release;
    @SerializedName("poster_path")
    @ColumnInfo(name = "photo")
    private String photo;
    @SerializedName("backdrop_path")
    @ColumnInfo(name = "background")
    private String background;
    @ColumnInfo(name = "fav")
    private int fav = 0;
    private int tv = 1;

    public ModelTV() {
    }


    protected ModelTV(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.genre = in.readString();
        this.popularity = in.readString();
        this.rating = in.readString();
        this.release = in.readString();
        this.photo = in.readString();
        this.background = in.readString();

        this.fav = in.readInt();
    }

    public int getTv() {
        return tv;
    }

    public void setTv(int tv) {
        this.tv = tv;
    }

    public int getFav() {
        return fav;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.genre);
        dest.writeString(this.popularity);
        dest.writeString(this.rating);
        dest.writeString(this.release);
        dest.writeString(this.photo);
        dest.writeString(this.background);

        dest.writeInt(this.fav);
    }
}

