package com.example.android.bakingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.example.android.bakingapp.Utils.INGREDIENTS_TABLE_NAME;

/**
 * Created by Alessandro on 11/04/2018.
 */

@Entity(tableName = INGREDIENTS_TABLE_NAME)
public class Ingredients implements Parcelable {

    public static final String ID = "id";
    public static final String QUANTITY = "quantity";
    public static final String MEASURE = "measure";
    public static final String INGREDIENT = "ingredient";

    @PrimaryKey(autoGenerate = true)
    int id;

    @SerializedName(QUANTITY)
    @ColumnInfo(name = QUANTITY)
    double quantity;

    @SerializedName(MEASURE)
    @ColumnInfo(name = MEASURE)
    String measure;

    @SerializedName(INGREDIENT)
    @ColumnInfo(name = INGREDIENT)
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

        if (values.containsKey(ID)) {
            ingredients.setId(values.getAsInteger(ID));
        }
        if (values.containsKey(QUANTITY)) {
            ingredients.setQuantity(values.getAsDouble(QUANTITY));
        }
        if (values.containsKey(MEASURE)) {
            ingredients.setMeasure(values.getAsString(MEASURE));
        }
        if (values.containsKey(INGREDIENT)) {
            ingredients.setIngredient(values.getAsString(INGREDIENT));
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
