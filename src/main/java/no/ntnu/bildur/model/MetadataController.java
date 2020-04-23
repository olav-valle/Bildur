package no.ntnu.bildur.model;


import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Controls all image metadata manipulation.
 * Implements classes from Drew Noakes https://drewnoakes.com.
 * @author Olav Valle
 */
public class MetadataController {

  /**
   * MetadataController constructor.
   */
  public MetadataController() {
  }

  /**
   * Reads and returns the Metadata contained in the parameter File image.
   * The Metadata object that is returned is implemented by Drew Noakes https://drewnoakes.com,
   * com.drew.metadata.Metadata. Returns null if metadata extraction fails.
   * @param image The image File to read metadata from.
   * @return A Metadata object containing directories of tags with values and any processing errors.
   */
  public static Metadata readMetadata(File image) {
    if (image == null) {
      throw new IllegalArgumentException("File was null");
      // TODO: 22/04/2020 test ex throw
    }
    Metadata meta = null;
    try {
      meta = ImageMetadataReader.readMetadata(image);
    } catch (ImageProcessingException | IOException ioe) {
      ioe.printStackTrace();
      // TODO: 22/04/2020 throw?
    }
    return meta;
    // TODO: 23/04/2020 test for null return on failed image read?
  }
  
  /**
   * Returns an iterable containing the metadata directories for the parameter image File.
   * @param image A File object representing the image to retrieve metadata from.
   * @return Iterable of Directory objects.
   */
  public static Iterable<Directory> getMetaDirectoryIterator(File image ) {
    if (image == null) {
      throw new IllegalArgumentException("File was null");
      // TODO: 22/04/2020 test ex throw
    }
    return readMetadata(image).getDirectories();
  }

}

