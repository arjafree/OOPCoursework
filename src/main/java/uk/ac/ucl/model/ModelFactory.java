package uk.ac.ucl.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


// This class gives access to the model to any other class that needs it.
// Calling the static method getModel (i.e., ModelFactory.getModel()) returns
// an initialised Model object. This version limits the program to one model object,
// which is returned whenever getModel is called.
// The factory also illustrates how a data file can be passed to the model.

public class ModelFactory
{
  private static Model model;
  private static ArrayList<Note> notes;
  private static ArrayList<Category> categories;
  private static String NOTES_JSON_FILE_PATH = "data/notes.json";
  private static String CATEGORIES_JSON_FILE_PATH = "data/categories.json";

  public static Model getModel() throws IOException
  {
    if (model == null)
    {
      model = new Model();
      loadNotes();
      loadCategories();
      // Note where the data file is stored in the data directory,
      // and the pathname to locate it.
      // The data should be read the file once, not every time the model is accessed!
    }
    return model;
  }
  private static void loadNotes() {
    Gson gson = new Gson();
    try (FileReader reader = new FileReader(NOTES_JSON_FILE_PATH)) {
      Type noteListType = new TypeToken<List<Note>>() {}.getType();
      notes = gson.fromJson(reader, noteListType);
      if (notes != null) {
        model.setNotes(notes);
      } else {
        notes = new ArrayList<>(); // Initialize if the file is empty
      }
    } catch (IOException e) {
      notes = new ArrayList<>(); // Initialize if the file is not found
    }
  }
  private static void loadCategories() {
    Gson gson = new Gson();
    try (FileReader reader = new FileReader(CATEGORIES_JSON_FILE_PATH)) {
      Type categoryListType = new TypeToken<List<Category>>() {}.getType();
      categories = gson.fromJson(reader, categoryListType);
      if (categories != null) {
        model.setCategories(categories);
      } else {
        categories = new ArrayList<>(); // Initialize if the file is empty
      }
    } catch (IOException e) {
      categories = new ArrayList<>(); // Initialize if the file is not found
    }
  }
}
