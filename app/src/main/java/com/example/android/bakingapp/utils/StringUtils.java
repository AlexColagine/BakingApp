package com.example.android.bakingapp.utils;

/**
 * Created by Alessandro on 11/04/2018.
 */

public class StringUtils {

    /**
     * Entity Name
     */
    public static final String RECIPE = "Recipe";
    public static final String TABLE_INGREDIENTS = "Ingredients";
    public static final String TABLE_STEPS = "Steps";

    /**
     * Entity Recipe
     * INGREDIENTS and STEPS are used also for the Key ArrayList of Recipe
     */
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String INGREDIENTS = "ingredients";
    public static final String STEPS = "steps";
    public static final String SERVINGS = "servings";
    public static final String IMAGE = "image";
    public static final String RECIPE_ID = "recipe_ID";

    /**
     * Entity Ingredients
     */
    public static final String INGREDIENT_ID = "id";
    public static final String QUANTITY = "quantity";
    public static final String MEASURE = "measure";
    public static final String INGREDIENT = "ingredient";

    /**
     * Entity Steps
     * DESCRIPTION - VIDEO_URL - THUMBNAIL_URL are used also for the Key ArrayList of Steps.
     */
    public static final String STEP_ID = "id";
    public static final String SHORT_DESCRIPTION = "shortDescription";
    public static final String DESCRIPTION = "description";
    public static final String VIDEO_URL = "videoURL";
    public static final String THUMBNAIL_URL = "thumbnailURL";
    public static final String STEPS_ARRAYLIST = "step_arraylist";

    /**
     * Key used for the ArrayList of Recipe
     */
    public static final String RECIPE_KEY = "Recipe";

    /**
     * Strings used to save the state
     */
    public static final String PLAYER_POSITION = "saved_player_position";
    public static final String PLAYER_STATE = "saved_player_state";
    public static final String RECYCLER_INGREDIENTS_STATE = "ingredients_state";
    public static final String RECYCLER_STEPS_STATE = "steps_state";

    public static final String TABLET = "tablet";
}
