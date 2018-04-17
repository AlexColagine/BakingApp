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

@Entity(tableName = BakingContract.IngredientsEntry.TABLE_NAME)
public class Ingredients implements Parcelable {


    @PrimaryKey(autoGenerate = true)
    int id;

    @SerializedName(BakingContract.IngredientsEntry.COLUMN_QUANTITY)
    @ColumnInfo(name = BakingContract.IngredientsEntry.COLUMN_QUANTITY)
    double quantity;

    @SerializedName(BakingContract.IngredientsEntry.COLUMN_MEASURE)
    @ColumnInfo(name = BakingContract.IngredientsEntry.COLUMN_MEASURE)
    String measure;

    @SerializedName(BakingContract.IngredientsEntry.COLUMN_INGREDIENT)
    @ColumnInfo(name = BakingContract.IngredientsEntry.COLUMN_INGREDIENT)
    String ingredient;


    public Ingredients() {
    }

    public int getId() {
        return id;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * Create a new {@link Ingredients} from the specified {@link ContentValues}.
     *
     * @param values A {@link ContentValues} that at least contain COLUMN_NAME.
     * @return A newly created {@link Ingredients} instance.
     */

    public static Ingredients fromContentValues(ContentValues values) {
        if (values == null) return null;

        final Ingredients ingredients = new Ingredients();

        if (values.containsKey(BakingContract.IngredientsEntry.COLUMN_ID)) {
            ingredients.setId(values.getAsInteger(BakingContract.IngredientsEntry.COLUMN_ID));
        }
        if (values.containsKey(BakingContract.IngredientsEntry.COLUMN_QUANTITY)) {
            ingredients.setQuantity(values.getAsDouble(BakingContract.IngredientsEntry.COLUMN_QUANTITY));
        }
        if (values.containsKey(BakingContract.IngredientsEntry.COLUMN_MEASURE)) {
            ingredients.setMeasure(values.getAsString(BakingContract.IngredientsEntry.COLUMN_MEASURE));
        }
        if (values.containsKey(BakingContract.IngredientsEntry.COLUMN_INGREDIENT)) {
            ingredients.setIngredient(values.getAsString(BakingContract.IngredientsEntry.COLUMN_INGREDIENT));
        }

        return ingredients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeDouble(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
    }

    protected Ingredients(Parcel in) {
        this.id = in.readInt();
        this.quantity = in.readDouble();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    public static final Parcelable.Creator<Ingredients> CREATOR = new Parcelable.Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel source) {
            return new Ingredients(source);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };
}
