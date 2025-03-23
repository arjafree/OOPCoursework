package uk.ac.ucl.model;

import java.util.ArrayList;

public class Index {
    ArrayList<Note> notes;
    //access notes via id [0.. ]
    ArrayList<Category> categories;

    public Index() {
        notes = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void removeNote(Note note) {
        notes.set(note.getId() - 1, null);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
    }


}
