package uk.ac.ucl.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Model {
  private Index index;
  private Directory rootDirectory;
  private static final String NOTES_FILE_PATH = "data/notes.json";
  private static final String CATEGORIES_FILE_PATH = "data/categories.json";

  public Model() {
    index = new Index();
    rootDirectory = new Directory("Root");
  }

  public Index getIndex() {
    return index;
  }

  public Directory getRootDirectory(){
      return rootDirectory;
  }

  public Note getNoteByID(int id){
      return index.getNotes().get(id-1);
  }

  public Directory findDirectory(String path) {

    String[] parts = path.split("/");
    Directory current = rootDirectory;
    if((parts.length == 1 && parts[0].equals("Root"))||path.equals("/")){
        return current;
    }

    for (String part : parts) {
        if (part.isEmpty()||part.equals("Root")) {
            continue;
        }
        boolean found = false;
        for (Directory subdirectory : current.getSubdirectories()) {
            if (subdirectory.getName().equals(part)) {
                current = subdirectory;
                found = true;
            }
        }
        if (!found) {
            System.out.println("this area");
            return null;
        }
    }
    return current;
  }

  public void setNotes(ArrayList<Note> notes) {
    index.setNotes(notes);
  }

  public void setCategories(ArrayList<Category> categories) {
    index.setCategories(categories);
  }
  public void addNoteToCategory(Note note, String categoryName) {
        for (Category category : index.getCategories()) {
            if (category.getName().equals(categoryName)) {
                category.addNote(note);
                saveCategoriesToFile();
                return;
            }
        }
        // If category does not exist, create it and add the note
        Category newCategory = new Category(categoryName);
        newCategory.addNote(note);
        addCategory(newCategory);
  }
  public void addNote(Note note){
      index.addNote(note);
  }

  public void addNoteToDirectory(Note note, String directoryPath){
      Directory directory = findDirectory(directoryPath);
      if (directory != null) { 
          note.setDirectory(directory);
          directory.addNote(note);
      } else {
          note.setDirectory(rootDirectory);
          rootDirectory.addNote(note);
      }
  }

  public ArrayList<Note> getNotesFromIDs(ArrayList<Integer> ids){
      ArrayList<Note> notes = new ArrayList<>();
      for(int id:ids){
          notes.add(getNoteByID(id));
      }
      return notes;
  }

  public ArrayList<ArrayList<Note>> getlistNotesFromCategories(){
      ArrayList<ArrayList<Note>> listoflists = new ArrayList<>();
      for(Category category:index.getCategories()) {
          ArrayList<Note> notes = new ArrayList<>();
          for (int i : category.getNoteIDs()) {
              notes.add(getNoteByID(i));
          }
          listoflists.add(notes);
      }
      return listoflists;
  }

  public Directory getParentDirectory(Directory d){
      return d.getParent();
  }

  public void createDirectory(String name, String parentPath) {
      if(parentPath.length()>1){
          parentPath = parentPath.substring(4);
      }
      Directory parent = findDirectory(parentPath);
      if (parent != null) {
            Directory newDirectory = new Directory(name, parent);
            parent.addSubdirectory(newDirectory);
      }
  }

  public void deleteNote(Note note) {
      //first, remove the note from all its categories
      for (String categoryName : note.getCategories()) {
        removeNoteFromCategory(note, categoryName);
      }
       //then, remove the note from its directory
       Directory directory = findDirectory(note.getDirectoryPath());
       removeNoteFromDirectory(note, directory);

       //then, delete the images that are associated with it
       deleteNoteImages(note);

       //then, remove the note from the index
       index.removeNote(note);
       //then, save the json file
       saveNotesToFile();

       //save categories as well
       saveCategoriesToFile();
  }

  // Helper method to delete images associated with a note
private void deleteNoteImages(Note note) {
    // Get the image paths from the note
    ArrayList<String> imagePaths = note.getImagePaths();
    if (imagePaths == null || imagePaths.isEmpty()) {
        return; // No images to delete
    }

    // Get the absolute path to the webapp/data/images directory
    String applicationPath = System.getProperty("user.dir"); // Base directory of the project
    String imagesPath = applicationPath + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "data" + File.separator + "images";

    // Delete each image file
    for (String relativePath : imagePaths) {
        try {
            File imageFile = new File(imagesPath, new File(relativePath).getName()); // Get the file name from the relative path
            if (imageFile.exists()) {
                if (imageFile.delete()) {
                    System.out.println("Deleted image: " + imageFile.getAbsolutePath());
                } else {
                    System.err.println("Failed to delete image: " + imageFile.getAbsolutePath());
                }
            } else {
                System.err.println("Image file not found: " + imageFile.getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Error deleting image file: " + e.getMessage());
        }
    }
}

  public void removeNoteFromDirectory(Note note, Directory d){
      d.removeNote(note);
  }

  public void removeNoteFromCategory(Note note, String categoryName) {
        for (Category category : index.getCategories()) {
            if (category.getName().equals(categoryName)) {
                category.removeNote(note);
                return;
            }
        }
  }
  public void addCategory(Category category) {
        index.addCategory(category);
        saveCategoriesToFile();
    }

    public void saveCategoriesToFile() {
        try (FileWriter writer = new FileWriter(CATEGORIES_FILE_PATH)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(index.getCategories(), writer);
            System.out.println("Categories saved to " + CATEGORIES_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error saving categories to file: " + e.getMessage());
        }
    }
  

  public void saveNotesToFile() {
        try (FileWriter writer = new FileWriter(NOTES_FILE_PATH)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(index.getNotes(), writer);
            System.out.println("Notes saved to " + NOTES_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error saving notes to file: " + e.getMessage());
        }
  }
}
