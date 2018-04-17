package com.example.android.bakingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.bakingapp.database.BakingContract;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import static com.example.android.bakingapp.Utils.INGREDIENTS;
import static com.example.android.bakingapp.Utils.STEPS;

/**
 * Created by Alessandro on 11/04/2018.
 */

@Entity(tableName = BakingContract.RecipeEntry.TABLE_NAME)
public class Recipe implements Parcelable {


    @SerializedName(BakingContract.RecipeEntry.COLUMN_ID)
    @PrimaryKey
    int id;

    @SerializedName(BakingContract.RecipeEntry.COLUMN_NAME)
    @ColumnInfo(name = BakingContract.RecipeEntry.COLUMN_NAME)
    String name;

    @SerializedName(INGREDIENTS)
            @Ignore
    ArrayList<Ingredients> ingredient = null;

    @SerializedName(STEPS)
            @Ignore
    ArrayList<Steps> step = null;

    @SerializedName(BakingContract.RecipeEntry.COLUMN_SERVINGS)
    @ColumnInfo(name = BakingContract.RecipeEntry.COLUMN_SERVINGS)
    int servings;

    boolean favorite;

    @SerializedName(BakingContract.RecipeEntry.COLUMN_IMAGE)
    @ColumnInfo(name = BakingContract.RecipeEntry.COLUMN_IMAGE)
    String image;

    public Recipe() {
    }

    public boolean getFavorite(){
        return favorite;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredients> getIngredient() {
        return ingredient;
    }

    public ArrayList<Steps> getStep() {
        return step;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredient(ArrayList<Ingredients> ingredient) {
        this.ingredient = ingredient;
    }

    public void setStep(ArrayList<Steps> step) {
        this.step = step;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setFavorite(boolean favorite){
        this.favorite = favorite;
    }



    /**
     * Create a new {@link Recipe} from the specified {@link ContentValues}.
     *
     * @param values A {@link ContentValues} that at least contain COLUMN_NAME.
     * @return A newly created {@link Recipe} instance.
     */

    public static Recipe fromContentValues(ContentValues values) {
        if (values == null) return null;

        final Recipe recipe = new Recipe();

        if (values.containsKey(BakingContract.RecipeEntry.COLUMN_ID)) {
            recipe.setId(values.getAsInteger(BakingContract.RecipeEntry.COLUMN_ID));
        }
        if (values.containsKey(BakingContract.RecipeEntry.COLUMN_NAME)) {
            recipe.setName(values.getAsString(BakingContract.RecipeEntry.COLUMN_NAME));
        }
        if (values.containsKey(BakingContract.RecipeEntry.COLUMN_SERVINGS)) {
            recipe.setServings(values.getAsInteger(BakingContract.RecipeEntry.COLUMN_SERVINGS));
        }
        if (values.containsKey(BakingContract.RecipeEntry.COLUMN_IMAGE)) {
            recipe.setImage(values.getAsString(BakingContract.RecipeEntry.COLUMN_IMAGE));
        }

        return recipe;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.ingredient);
        dest.writeTypedList(this.step);
        dest.writeInt(this.servings);
        dest.writeByte(this.favorite ? (byte) 1 : (byte) 0);
        dest.writeString(this.image);
    }

    protected Recipe(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.ingredient = in.createTypedArrayList(Ingredients.CREATOR);
        this.step = in.createTypedArrayList(Steps.CREATOR);
        this.servings = in.readInt();
        this.favorite = in.readByte() != 0;
        this.image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
