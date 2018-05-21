package com.example.android.bakingapp.database;

import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.android.bakingapp.utils.StringUtils.DESCRIPTION;
import static com.example.android.bakingapp.utils.StringUtils.ID;
import static com.example.android.bakingapp.utils.StringUtils.IMAGE;
import static com.example.android.bakingapp.utils.StringUtils.INGREDIENT;
import static com.example.android.bakingapp.utils.StringUtils.INGREDIENT_ID;
import static com.example.android.bakingapp.utils.StringUtils.MEASURE;
import static com.example.android.bakingapp.utils.StringUtils.NAME;
import static com.example.android.bakingapp.utils.StringUtils.QUANTITY;
import static com.example.android.bakingapp.utils.StringUtils.RECIPE;
import static com.example.android.bakingapp.utils.StringUtils.RECIPE_ID;
import static com.example.android.bakingapp.utils.StringUtils.SERVINGS;
import static com.example.android.bakingapp.utils.StringUtils.SHORT_DESCRIPTION;
import static com.example.android.bakingapp.utils.StringUtils.STEP_ID;
import static com.example.android.bakingapp.utils.StringUtils.TABLE_INGREDIENTS;
import static com.example.android.bakingapp.utils.StringUtils.TABLE_STEPS;
import static com.example.android.bakingapp.utils.StringUtils.THUMBNAIL_URL;
import static com.example.android.bakingapp.utils.StringUtils.VIDEO_URL;

/**
 * Created by Alessandro on 16/04/2018.
 */

public class BakingContract {

    /** The authority of this content provider. */
    public static final String CONTENT_AUTHORITY = "com.example.android.bakingapp";

    /** The URI for the table. */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class RecipeEntry implements BaseColumns{

        public static final String TABLE_NAME = RECIPE;
        public static final String COLUMN_ID = ID;
        public static final String COLUMN_NAME = NAME;
        public static final String COLUMN_SERVINGS = SERVINGS;
        public static final String COLUMN_IMAGE = IMAGE;

        private static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME)
                .build();

    }

    public static final class IngredientsEntry implements BaseColumns{

        public static final String TABLE_NAME = TABLE_INGREDIENTS;
        public static final String COLUMN_ID = INGREDIENT_ID;
        public static final String COLUMN_QUANTITY = QUANTITY;
        public static final String COLUMN_MEASURE = MEASURE;
        public static final String COLUMN_INGREDIENT = INGREDIENT;
        public static final String COLUMN_RECIPE_ID = RECIPE_ID;

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME)
                .build();

        public static final Uri.Builder CONTENT_ITEM_URI =
                BASE_CONTENT_URI.buildUpon()
                        .appendPath(TABLE_NAME);

    }

    public static final class StepsEntry implements BaseColumns{

        public static final String TABLE_NAME = TABLE_STEPS;
        public static final String COLUMN_ID = STEP_ID;
        public static final String COLUMN_SHORT_DESCRIPTION = SHORT_DESCRIPTION;
        public static final String COLUMN_DESCRIPTION = DESCRIPTION;
        public static final String COLUMN_VIDEO = VIDEO_URL;
        public static final String COLUMN_THUMBNAIL = THUMBNAIL_URL;

        private static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_NAME)
                .build();

    }



}
