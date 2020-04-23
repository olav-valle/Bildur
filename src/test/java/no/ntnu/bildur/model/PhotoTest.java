package no.ntnu.bildur.model;


import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.File;
import java.util.Iterator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhotoTest {

  File testFile;
  Photo testPhoto;

  @BeforeEach
  void setUp() {
    testFile = new File("/ccwn2c08.png");
    testPhoto = new Photo(testFile);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void photoConstructionFromNullObjectFail() {
    System.out.println("Testing construction of Photo object from null parameter.");
    try {
      Photo failPhoto = new Photo(null);
    } catch (Exception e) {
      assert(e instanceof IllegalArgumentException);
      System.out.println(e.toString());
    }
  }
  @Test
  void getURI() {
    System.out.println("Testing URI:");
    assertEquals(testPhoto.getURI(), testFile.toURI());
  }

  @Test
  void getFileName() {
    System.out.println("Testing file name.");
    assertEquals(testPhoto.getFileName(), testFile.getName());
  }

  @Test
  void getImageFile() {
    System.out.println("Testing File object equality.");
    assertEquals(testPhoto.getImageFile(), testFile);
  }

  @Test
  void addTag() {
    System.out.println("Testing addTag.");
    try{
      testPhoto.addTag("TEST_TAG");
    } catch (IllegalArgumentException e) {
      System.out.println(e.toString());
      Assertions.fail();
      // If ex was caught, tag adding failed unexpectedly.
    }
  }

  @Test
  void addNullTagFail() {
    System.out.println("Testing Null tag adding failure.");
    try {
      testPhoto.addTag(null);
      Assertions.fail();
      // If ex is not caught, test failed.
    } catch (Exception e) {
      assert(e instanceof IllegalArgumentException );
      System.out.println(e.toString());
    }
  }

  @Test
  void addEmptyStringTagFail() {
    System.out.println("Testing empty string tag adding failure.");
    try {
      testPhoto.addTag("");
      Assertions.fail();
    } catch (Exception e) {
      assert(e instanceof IllegalArgumentException);
      System.out.println(e.toString());
    }
  }

  @Test
  void getTagIterator() {
    System.out.println("Testing getTagIterator.");
    try{
      testPhoto.addTag("FIRST_TAG");
      testPhoto.addTag("NEXT_TAG");
      testPhoto.addTag("LAST_TAG");
    } catch (IllegalArgumentException e) {
      System.out.println(e.toString());
      Assertions.fail();
      // If ex was caught, tag adding failed unexpectedly.
    }
    Iterator tags = testPhoto.getTagIterator();
    assertEquals(tags.next(), "FIRST_TAG", "First tag was not: FIRST_TAG");
    assertEquals(tags.next(), "NEXT_TAG", "Second tag was not: NEXT_TAG");
    assertEquals(tags.next(), "LAST_TAG", "Last tag was not: LAST_TAG");
  }
}