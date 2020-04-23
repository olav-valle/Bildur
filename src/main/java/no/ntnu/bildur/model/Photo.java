package no.ntnu.bildur.model;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Represents a photo image file. Holds information about file path and user created tags.
 */

@Entity
public class Photo implements Serializable {
  @Id
  @GeneratedValue
  private String fileName;
  private File imageFile;
  private String tags;

  /**
   * Constructs a Photo object from the File parameter.
   *
   * @param file The File object that this object will represent.
   */
  public Photo(File file) {

    if (file != null) {
      this.imageFile = file;
      fileName = imageFile.getName();
      tags = "";
    } else {
      throw new IllegalArgumentException("Photo cannot be created from null object.");
    }
  }

  public Photo() {

  }

  /**
   * Returns the URI of this photo.
   *
   * @return The URI of this photo.
   */
  public URI getURI() {
    return imageFile.toURI();
  }

  /**
   * Returns the filename of this photo.
   *
   * @return The file name of this photo.
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * Returns the File object that this photo represents.
   *
   * @return The File object that this photo represents.
   */
  public File getImageFile() {
    return this.imageFile;
  }

  /**
   * Adds a tag to the photo.
   * Throws IllegalArgumentException if the input is Null of an empty string.
   *
   * @param tag The tag this photo is being marked with.
   */
  public void addTag(String tag) {
    if (tag == null) throw new IllegalArgumentException("Tag object was null.");
    tag = tag.trim();

    if (!tag.equals("")){
      List<String> tagList = new ArrayList<>(this.tagStringToList(this.tags));
      tagList.add(tag);
      this.tags = this.tagListToString(tagList);
    } else {
      throw new IllegalArgumentException("Input was empty string.");
    }
  }

  /**
   * Returns an iterator of the tags associated with this photo.
   */
  public Iterator<String> getTagIterator( ) {
    return this.tagStringToList(tags).iterator();
  }

  /**
   * Joins a List<String> to a single string, separating the elements with " , ".
   * Used as a part of a workaround for saving tags to the database.
   */
  private String tagListToString(List<String> test){
    return test.stream().reduce((x, y) -> {
      if (x.equals("")) {
        x = y;
      } else {
        x = x + " , " + y;
      }
      return x;
    }).get();
  }

  /**
   * Splits a String by " , " and returns it as a List<String>
   * Used as a part of a workaround for saving tags to the database.
   */
  private List<String> tagStringToList(String test){
    return Arrays.asList(test.split(" , "));
  }

  /**
   * Returns string of path to this photo.
   * @return string of path to this photo.
   */
  public String getPathString() {
    return this.imageFile.toString();
  }
}

