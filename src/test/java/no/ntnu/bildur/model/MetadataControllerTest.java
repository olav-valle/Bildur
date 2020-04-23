package no.ntnu.bildur.model;

import static org.junit.jupiter.api.Assertions.*;


import com.drew.metadata.Metadata;
import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MetadataControllerTest {

  File testFile;
  Photo testPhoto;

  @BeforeEach
  void setUp() {
    testFile = new File("ccwn2c08.png");
    testPhoto = new Photo(testFile);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void readMetadataSuccess() {
    System.out.println("Testing successfull reading of metadata from image file.");
    try {
      assert(MetadataController.readMetadata(testFile) != null);
    } catch (IllegalArgumentException e) {
      Assertions.fail();
    }
  }

  @Test
  void readMetadataFail() {
    System.out.println("Testing failed reading of metadata from null parameter.");
    try {
      assert(MetadataController.readMetadata(null) == null);
    } catch (Exception e) {
      System.out.println(e.toString());
      assert(e instanceof IllegalArgumentException);
    }
  }
}