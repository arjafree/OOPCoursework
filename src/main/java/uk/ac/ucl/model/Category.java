package uk.ac.ucl.model;

import java.util.ArrayList;

public class Category {
  private String name;
  private ArrayList<Integer> noteIDs;

  public Category(String name) {
    this.name = name;
    this.noteIDs = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public ArrayList<Integer> getNoteIDs() {
    return noteIDs;
  }

  public void addNote(Note note) {
    noteIDs.add(note.getId());
  }

  public void removeNote(Note note) {
    noteIDs.remove(Integer.valueOf(note.getId()));
  }

  public void setName(String name) {
    this.name = name;
  }
}
