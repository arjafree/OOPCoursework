package uk.ac.ucl.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Model {
  private Index index;
  private Directory rootDirectory;
  private static final String NOTES_FILE_PATH = "data/notes.json";

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
                index.addNote(note);
                return;
            }
        }
        // If category does not exist, create it and add the note
        Category newCategory = new Category(categoryName);
        newCategory.addNote(note);
        index.addCategory(newCategory);
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
  public void removeNoteFromCategory(Note note, String categoryName) {
        for (Category category : index.getCategories()) {
            if (category.getName().equals(categoryName)) {
                category.removeNote(note);
                index.removeNote(note);
                return;
            }
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
