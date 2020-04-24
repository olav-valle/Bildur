package no.ntnu.bildur.model;

import java.util.Collection;

/**
 * Interface for the photo archive classes.
 */
public interface Archive {

  /**
   * Imports an image file into the database.
   *
   * @param imageFile A File object representing an image file.
   */
  void importPhoto(Photo imageFile);

  /**
   * Returns a collection of the Photo objects held in DB.
   *
   * @return a Collections of Photo objects.
   */
  Collection<Photo> getArchiveList();

  /**
   * Adds a tag to a photo.
   *
   * @param tag String representing the tag.
   * @param photo The photo which is being tagged.
   */
  void addTagToPhoto(String tag, Photo photo);

  /**
   * Closes this entity.
   */
  void close();
}
