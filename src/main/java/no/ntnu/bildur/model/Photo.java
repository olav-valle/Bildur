package no.ntnu.bildur.model;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Represents a photo image. Holds path to the image file, and the metadata details.
 */


//  We can ditch the BufferedImage inheritance since this class is meant to
//  represent only the information stored about an image,
//  e.g. its file system path and metadata details, and not to be a renderable Image file.

//  To display the image, we pass this path to an ImageViewer UI class,
//  which parses the path and loads the image.
//  This path can be stored as a String, or as a URI.

//  A File object is also serializable, so it is possible we could use this type instead
//  of a String path or a URI object, and not have to re-create the File object
//  on each program start.
//  https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/File.html

//  The second part of the image is the storage of metadata.
//  Here we will probably look for a way to read metadata from the image file,
//  and create Detail objects for each "field" of metadata.
//  These Detail objects can be stored as a collection(List, Map).
//  The Detail class must implement Serializable, but must not be an @Entity.

@Entity
public class Photo implements Serializable {
  @Id
  @GeneratedValue
  private String fileName;
  private File imageFile;
  @ElementCollection
  private List<PhotoTag> tags;

  /**
   * Constructs a Photo object from the File parameter.
   *
   * @param file The File object that this object will represent.
   */
  public Photo(File file) {
    // TODO: 19/03/2020 throw ex if file is not of a valid file type (.jpg etc..)?

    this.imageFile = file;
    fileName = imageFile.getName();
    tags = new ArrayList<>();
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
   * Retuns stringpath of this photo.
   *
   * @return Stringpath of this photo.
   */
  public String getPathString() {
    return this.imageFile.toString();
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
   *
   * @param tag The tag this photo is being marked with.
   */
  public void addTag(PhotoTag tag) {
    if (tag != null) {
      this.tags.add(tag);
    } else {
      throw new IllegalArgumentException("Tag object was null.");
    }
  }

  /**
   * Returns an iterator of the tags associated with this photo.
   */
  public Iterator<PhotoTag> getTagIterator( ) {
      return this.tags.iterator();
  }

}

