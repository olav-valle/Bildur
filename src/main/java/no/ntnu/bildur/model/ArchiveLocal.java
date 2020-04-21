package no.ntnu.bildur.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The photo details archive. Stores Image objects in a collection.
 */
// TODO: 19/03/2020 Do we make the Archive itself serializable,
//  or the objects held in the archive collection?
public class ArchiveLocal implements Archive {

  private ArrayList<Photo> photos;

  /**
   * Photo archive constructor.
   */
  public ArchiveLocal(){
    photos = new ArrayList<>();
  }

  /**
   * Imports an image file, and stores it as a Photo-class object in the collection.
   */
  @Override
  public void importPhoto(Photo imageFile){

    // TODO: 20/03/2020 extract image metadata and store in Photo object.
    // TODO: 20/03/2020 Add checks and throws.
    if(imageFile != null) photos.add(imageFile);
  }

  /**
   * Returns the archive collection a List Object.
   * @return The Archive as a list.
   */
  @Override
  public Collection<Photo> getArchiveList(){
    return photos;
  }

  @Override
  public void addTagToPhoto(String tag, Photo photo) {

  }

  @Override
  public void close() {
  }



}
