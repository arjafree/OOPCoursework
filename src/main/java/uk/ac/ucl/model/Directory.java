package uk.ac.ucl.model;

import java.util.ArrayList;

public class Directory {
    private String name;
    private Directory parent;
    private ArrayList<Directory> subdirectories;
    private ArrayList<Note> notes;

    public Directory(String name) {
        this.name = name;
        this.subdirectories = new ArrayList<>();
        this.notes = new ArrayList<>();
    }
    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
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
        subdirectory.parent = this;
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

    public Directory getParent(){
        return parent;
    }

    public String getPath() {
        if (parent == null) {
            return name; // Root directory
        }
        return parent.getPath() + "/" + name;
    }

}
