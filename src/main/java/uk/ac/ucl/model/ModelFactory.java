package uk.ac.ucl.model;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


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
      loadDirectories();

      // Note where the data file is stored in the data directory,
      // and the pathname to locate it.
      // The data should be read the file once, not every time the model is accessed!
    }
    return model;
  }
  private static void loadDirectories() {
    for (Note note : notes) {
        if(note == null){
            continue;
        }
        String directoryPath = note.getDirectoryPath();
        Directory directory = model.findDirectory(directoryPath);

        // If the directory does not exist, create it
        if (directory == null) {
            createDirectoriesForPath(directoryPath);
        }

        // Add the note to the directory
        model.addNoteToDirectory(note, directoryPath);
        
    }
  }

  // Helper method to create directories for a given path
  private static void createDirectoriesForPath(String path) {
      String[] parts = path.split("/");
      Directory current = model.getRootDirectory();

      for (String part : parts) {
          if (part.isEmpty()||part.equals("Root")) {
              continue; // Skip empty parts (e.g., leading "/")
          }

          // Check if the subdirectory already exists
          Directory subdirectory = current.getSubdirectories().stream()
              .filter(dir -> dir.getName().equals(part))
              .findFirst()
              .orElse(null);

          // If the subdirectory does not exist, create it
          if (subdirectory == null) {
              subdirectory = new Directory(part, current);
              current.addSubdirectory(subdirectory);
          }

          // Move to the next level in the directory tree
          current = subdirectory;
      }
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
