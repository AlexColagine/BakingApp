package com.example.android.bakingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.example.android.bakingapp.Utils.STEPS_TABLE_NAME;

/**
 * Created by Alessandro on 11/04/2018.
 */

@Entity(tableName = STEPS_TABLE_NAME)
public class Steps implements Parcelable {

    public static final String ID = "id";
    public static final String SHORT_DESCRIPTION = "shortDescription";
    public static final String DESCRIPTION = "description";
    public static final String VIDEO_URL = "videoURL";
    public static final String THUMBNAIL_URL = "thumbnailURL";

    @SerializedName(ID)
    @PrimaryKey
    int id;

    @SerializedName(SHORT_DESCRIPTION)
    @ColumnInfo(name = SHORT_DESCRIPTION)
    String shortDescription;

    @SerializedName(DESCRIPTION)
    @ColumnInfo(name = DESCRIPTION)
    String description;

    @SerializedName(VIDEO_URL)
    @ColumnInfo(name = VIDEO_URL)
    String video;

    @SerializedName(THUMBNAIL_URL)
    @ColumnInfo(name = THUMBNAIL_URL)
    String thumbnail;

    public Steps() {
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideo() {
        return video;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * Create a new {@link Ingredients} from the specified {@link ContentValues}.
     *
     * @param values A {@link ContentValues} that at least contain COLUMN_NAME.
     * @return A newly created {@link Ingredients} instance.
     */

    public static Steps fromContentValues(ContentValues values) {
        if (values == null) return null;

        final Steps steps = new Steps();

        if (values.containsKey(ID)) {
            steps.setId(values.getAsInteger(ID));
        }
        if (values.containsKey(SHORT_DESCRIPTION)) {
            steps.setShortDescription(values.getAsString(SHORT_DESCRIPTION));
        }
        if (values.containsKey(DESCRIPTION)) {
            steps.setDescription(values.getAsString(DESCRIPTION));
        }
        if (values.containsKey(VIDEO_URL)) {
            steps.setVideo(values.getAsString(VIDEO_URL));
        }
        if (values.containsKey(THUMBNAIL_URL)) {
            steps.setThumbnail(values.getAsString(THUMBNAIL_URL));
        }

        return steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.video);
        dest.writeString(this.thumbnail);
    }

    protected Steps(Parcel in) {
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.video = in.readString();
        this.thumbnail = in.readString();
    }

    public static final Parcelable.Creator<Steps> CREATOR = new Parcelable.Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel source) {
            return new Steps(source);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };
}
