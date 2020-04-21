package no.ntnu.bildur.model;

import java.util.Collection;
import java.util.List;

public interface Archive {
  void importPhoto(Photo imageFile);

  /**
   * Returns a collection of the Photo objects held in DB.
   * @return
   */
  Collection<Photo> getArchiveList();

  /**
   * Adds a tag to a photo.
   * @param tag String representing the tag.
   * @param photo The photo which is being tagged.
   */
  void addTagToPhoto(String tag, Photo photo);

  /**
   * Closes this entity.
   */
  void close();
}
