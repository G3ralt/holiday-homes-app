package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.*;

public class JSONConverter {

    private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

    public static String getJSONfromObject(Object object) {
        return GSON.toJson(object);
    }
    
    public static Place getPlaceFromJSON(String json) {
        return GSON.fromJson(json, Place.class);
    }

    public static User getUserFromJSON(String json) {
        return GSON.fromJson(json, User.class);
    }
}
