package uk.ac.ucl.model;

import java.util.ArrayList;

public class Note {
    //unique id
    private int id;
    private String name;
    private String text;

    //notes can have one or more categories
    private ArrayList<String> categories;
    //notes can have one or more images
    private ArrayList<String> imagePaths;

    private Directory directory;

    //constructor for notes with images
    public Note(int id, String name, String text, ArrayList<String> imagePaths, ArrayList<String> categories) {
        //unique identifier for note
        this.id = id;
        this.name = name;
        this.text = text;
        //if there is no imagepaths or categories passed, initialise them to empty arraylist
        this.imagePaths = imagePaths != null ? imagePaths : new ArrayList<>();
        this.categories = categories != null ? categories : new ArrayList<>();
        this.directory = null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public ArrayList<String> getImagePaths() {
        return imagePaths;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public void setImagePaths(ArrayList<String> imagePaths) {
        this.imagePaths = imagePaths;
    }
    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public String getDirectoryPath() {
        if (directory == null) {
            return "Unassigned"; // If the note is not assigned to a directory
        }
        return directory.getPath();
    }



    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", categories=" + categories +
                ", imagePaths=" + imagePaths +
                '}';
    }
}
