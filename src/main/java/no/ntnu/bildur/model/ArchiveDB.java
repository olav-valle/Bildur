package no.ntnu.bildur.model;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Photo archive implementing a database and entity management.
 */
public class ArchiveDB implements Archive {

  private final EntityManagerFactory emf;
  private final EntityManager em;

  public ArchiveDB(){
    // switch PU name to "imageDB" for MySQL DB.
    emf = Persistence.createEntityManagerFactory("localDB");
    em = this.emf.createEntityManager();
  }

  /**
   * Imports an image file into the database.
   * @param imageFile A File object representing an image file.
   */
  @Override
  public void importPhoto(Photo imageFile) {
    em.getTransaction().begin();
    em.persist(imageFile);
    em.getTransaction().commit();
  }

  /**
   * Returns a collection of the Photo objects held in DB.
   * @return a collection of the Photo objects held in DB.
   */
  @Override
  @SuppressWarnings("unchecked")
  public Collection<Photo> getArchiveList() {
    return this.em.createQuery("select p from Photo p").getResultList();
  }

  /**
   * Adds a tag to a photo.
   * @param tag String representing the tag.
   * @param photo The photo which is being tagged.
   */
  @Override
  public void addTagToPhoto(String tag, Photo photo) {
    photo.addTag(new PhotoTag(tag));
  }


  @Override
  /**
   * Closes this entity.
   */
  public void close( ) {
      this.em.close();
      this.emf.close();
  }
}
