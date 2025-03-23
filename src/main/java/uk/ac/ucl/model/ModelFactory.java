package uk.ac.ucl.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

public class ModelFactory {
    private static Model model;
    private static ArrayList<Note> notes;
    private static ArrayList<Category> categories;
    private static final String NOTES_JSON_FILE_PATH = "data/notes.json";
    private static final String CATEGORIES_JSON_FILE_PATH = "data/categories.json";

    public static Model getModel() throws IOException {
        if (model == null) {
            model = new Model();
            loadNotes();
            loadCategories();
            loadDirectories();
        }
        return model;
    }

    private static void loadDirectories() {
        for (Note note : notes) {
            if (note == null) {
                continue;
            }
            String directoryPath = note.getDirectoryPath();
            Directory directory = model.findDirectory(directoryPath);

            //if the directory does not exist, create it
            if (directory == null) {
                createDirectoriesForPath(directoryPath);
            }

            model.addNoteToDirectory(note, directoryPath);

        }
    }

    //create directories for a given path
    private static void createDirectoriesForPath(String path) {
        String[] parts = path.split("/");
        Directory current = model.getRootDirectory();

        for (String part : parts) {
            if (part.isEmpty() || part.equals("Root")) {
                continue;
            }

            Directory subdirectory = current.getSubdirectories().stream()
                    .filter(dir -> dir.getName().equals(part))
                    .findFirst()
                    .orElse(null);

            if (subdirectory == null) {
                subdirectory = new Directory(part, current);
                current.addSubdirectory(subdirectory);
            }

            //move to the next level in the directory tree
            current = subdirectory;
        }
    }

    private static void loadNotes() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(NOTES_JSON_FILE_PATH)) {
            Type noteListType = new TypeToken<List<Note>>() {
            }.getType();
            notes = gson.fromJson(reader, noteListType);
            if (notes != null) {
                model.setNotes(notes);
            } else {
                notes = new ArrayList<>();
            }
        } catch (IOException e) {
            notes = new ArrayList<>();
        }
    }

    private static void loadCategories() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(CATEGORIES_JSON_FILE_PATH)) {
            Type categoryListType = new TypeToken<List<Category>>() {
            }.getType();
            ArrayList<Category> categories = gson.fromJson(reader, categoryListType);
            if (categories != null) {
                model.setCategories(categories);
            } else {
                categories = new ArrayList<>();
            }
        } catch (IOException e) {
            categories = new ArrayList<>();
        }
    }
}
