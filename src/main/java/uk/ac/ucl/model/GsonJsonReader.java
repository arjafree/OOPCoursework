package uk.ac.ucl.model;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonJsonReader {
    public static void main(String[] args) {
        Gson gson = new Gson();
        Type categoryListType = new TypeToken<ArrayList<Category>>() {}.getType();

        try (FileReader reader = new FileReader("data/categories.json")) {
            // Convert JSON file into an ArrayList<Category>
            ArrayList<Category> categories = gson.fromJson(reader, categoryListType);

            // Print the result
            for (Category category : categories) {
                System.out.println(category.getName());
                System.out.println(category.getNoteIDs());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
