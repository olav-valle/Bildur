package no.ntnu.bildur.model;


import java.io.File;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.util.Collection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArchiveDBTest {
  ArchiveDB archive;
  File testFile;
  Photo testPhoto;

  @BeforeEach
  void setUp() {
    archive = new ArchiveDB("test");
    testFile = new File("/ccwn2c08.png");
    testPhoto = new Photo(testFile);


  }

  @AfterEach
  void tearDown() {
    System.out.println("Closing connection. ");
    try {
      DriverManager.getConnection("jdbc:derby:memory:testdb;drop=true");
    } catch (SQLNonTransientConnectionException e) {
      System.out.println("08006: Database 'memory:testdb' dropped.");
      /*
      According to the Apache.org Darby documentation "A clean shutdown
      always throws SQL exception XJ015, which can be ignored."
       */
    } catch ( SQLException e) {
      e.printStackTrace();
    }
    archive.close();
  }

  @Test
  void importPhotoSuccess() {
    System.out.println("Importing testPhoto Photo object to db.");
    try {
      archive.importPhoto(testPhoto);
    } catch (Exception e) {
      System.out.println(e.toString());
      Assertions.fail();
    }
  }

  @Test
  void importPhotoFail() {
    System.out.println("Testing failure of importPhoto with null parameter.");
    try {
      archive.importPhoto(null);
      Assertions.fail();
      // Fails if no ex is thrown when adding null.
    } catch (Exception e ) {
      System.out.println(e.toString());
      assert(e instanceof IllegalArgumentException);
    }
  }

  @Test
  void getArchiveList() {
    try {
      archive.importPhoto(testPhoto);
    } catch (Exception e) {
      System.out.println(e.toString());
      Assertions.fail();
    }
    Collection<Photo> photos = archive.getArchiveList();
    assert(photos.size() == 1);
  }

  @Test
  void addTagToPhoto() {
  }

  @Test
  void close() {
  }
}