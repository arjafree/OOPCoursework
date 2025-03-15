package uk.ac.ucl.model;

import java.util.ArrayList;

public class Directory {
    private String name;
    private ArrayList<Directory> subdirectories;
    private ArrayList<Note> notes;

    public Directory(String name) {
        this.name = name;
        this.subdirectories = new ArrayList<>();
        this.notes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Directory> getSubdirectories() {
        return subdirectories;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void addSubdirectory(Directory subdirectory) {
        subdirectories.add(subdirectory);
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void removeSubdirectory(Directory subdirectory) {
        subdirectories.remove(subdirectory);
    }

    public void removeNote(Note note) {
        notes.remove(note);
    }

}
