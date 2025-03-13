package uk.ac.ucl.model;

import java.util.ArrayList;

public class Model {
  private Index index;

  public Model() {
    index = new Index();
  }

  public Index getIndex() {
    return index;
  }

  public Note getNoteByID(int id){
      return index.getNotes().get(id);
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

  public ArrayList<Note> getNotesFromIDs(ArrayList<Integer> ids){
      ArrayList<Note> notes = new ArrayList<>();
      for(int id:ids){
          notes.add(getNoteByID(id));
      }
      return notes;
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
}
