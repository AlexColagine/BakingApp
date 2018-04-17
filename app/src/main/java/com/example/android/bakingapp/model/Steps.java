package com.example.android.bakingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.bakingapp.database.BakingContract;
import com.google.gson.annotations.SerializedName;


/**
 * Created by Alessandro on 11/04/2018.
 */

@Entity(tableName = BakingContract.StepsEntry.TABLE_NAME)
public class Steps implements Parcelable {


    @SerializedName(BakingContract.StepsEntry.COLUMN_ID)
    @PrimaryKey
    int id;

    @SerializedName(BakingContract.StepsEntry.COLUMN_SHORT_DESCRIPTION)
    @ColumnInfo(name = BakingContract.StepsEntry.COLUMN_SHORT_DESCRIPTION)
    String shortDescription;

    @SerializedName(BakingContract.StepsEntry.COLUMN_DESCRIPTION)
    @ColumnInfo(name = BakingContract.StepsEntry.COLUMN_DESCRIPTION)
    String description;

    @SerializedName(BakingContract.StepsEntry.COLUMN_VIDEO)
    @ColumnInfo(name = BakingContract.StepsEntry.COLUMN_VIDEO)
    String video;

    @SerializedName(BakingContract.StepsEntry.COLUMN_THUMBNAIL)
    @ColumnInfo(name = BakingContract.StepsEntry.COLUMN_THUMBNAIL)
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

        if (values.containsKey(BakingContract.StepsEntry.COLUMN_ID)) {
            steps.setId(values.getAsInteger(BakingContract.StepsEntry.COLUMN_ID));
        }
        if (values.containsKey(BakingContract.StepsEntry.COLUMN_SHORT_DESCRIPTION)) {
            steps.setShortDescription(values.getAsString(BakingContract.StepsEntry.COLUMN_SHORT_DESCRIPTION));
        }
        if (values.containsKey(BakingContract.StepsEntry.COLUMN_DESCRIPTION)) {
            steps.setDescription(values.getAsString(BakingContract.StepsEntry.COLUMN_DESCRIPTION));
        }
        if (values.containsKey(BakingContract.StepsEntry.COLUMN_VIDEO)) {
            steps.setVideo(values.getAsString(BakingContract.StepsEntry.COLUMN_VIDEO));
        }
        if (values.containsKey(BakingContract.StepsEntry.COLUMN_THUMBNAIL)) {
            steps.setThumbnail(values.getAsString(BakingContract.StepsEntry.COLUMN_THUMBNAIL));
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
