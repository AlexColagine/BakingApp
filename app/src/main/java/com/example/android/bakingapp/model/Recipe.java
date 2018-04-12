package com.example.android.bakingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import static com.example.android.bakingapp.Utils.RECIPE_TABLE_NAME;

/**
 * Created by Alessandro on 11/04/2018.
 */

@Entity(tableName = RECIPE_TABLE_NAME)
public class Recipe implements Parcelable {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String INGREDIENTS = "ingredients";
    public static final String STEPS = "steps";
    public static final String SERVINGS = "servings";
    public static final String IMAGE = "image";

    @SerializedName(ID)
    @PrimaryKey
    int id;

    @SerializedName(NAME)
    @ColumnInfo(name = NAME)
    String name;

    @SerializedName(INGREDIENTS)
    ArrayList<Ingredients> ingredient;

    @SerializedName(STEPS)
    ArrayList<Steps> step;

    @SerializedName(SERVINGS)
    @ColumnInfo(name = SERVINGS)
    int servings;

    @SerializedName(IMAGE)
    @ColumnInfo(name = IMAGE)
    String image;

    public Recipe() {
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

    /**
     * Create a new {@link Recipe} from the specified {@link ContentValues}.
     *
     * @param values A {@link ContentValues} that at least contain COLUMN_NAME.
     * @return A newly created {@link Recipe} instance.
     */

    public static Recipe fromContentValues(ContentValues values) {
        if (values == null) return null;

        final Recipe recipe = new Recipe();

        if (values.containsKey(ID)) {
            recipe.setId(values.getAsInteger(ID));
        }
        if (values.containsKey(NAME)) {
            recipe.setName(values.getAsString(NAME));
        }
        if (values.containsKey(SERVINGS)) {
            recipe.setServings(values.getAsInteger(SERVINGS));
        }
        if (values.containsKey(IMAGE)) {
            recipe.setImage(values.getAsString(IMAGE));
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
        dest.writeList(this.ingredient);
        dest.writeList(this.step);
        dest.writeInt(this.servings);
        dest.writeString(this.image);
    }

    protected Recipe(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.ingredient = new ArrayList<Ingredients>();
        in.readList(this.ingredient, Ingredients.class.getClassLoader());
        this.step = new ArrayList<Steps>();
        in.readList(this.step, Steps.class.getClassLoader());
        this.servings = in.readInt();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
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
