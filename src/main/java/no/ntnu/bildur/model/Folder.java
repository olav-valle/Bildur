package no.ntnu.bildur.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a folder to which the user can add photos.
 */
public class Folder {
    private ArrayList<Photo> photoFolder;
    private String folderName;

    /**
     * Creates an instance of a Folder.
     *
     * @param folderName the name of this folder.
     */
    public Folder(String folderName) {
        if (folderName.trim().equals("")) {
            throw new IllegalArgumentException("The folder name cannot be empty.");
        }
        this.folderName = folderName.trim();
    }

    /**
     * Allows the user to change the folder name whenever.
     *
     * @param newFolderName the new name of this folder.
     */
    public void setFolderName(String newFolderName) {
        if (!newFolderName.trim().equals("")) {
            this.folderName = newFolderName;
        }
    }

    /**
     * Adds a collection of photos to this folder.
     *
     * @param selectedPhotos the collection of photos to add to this folder.
     */
    public void addPhotos(ArrayList<Photo> selectedPhotos) {
        if (selectedPhotos != null) {
            photoFolder.addAll(selectedPhotos);
        }
    }

    /**
     * Deletes all selected photos from this folder.
     *
     * @param selectedPhotos the photos to delete from this folder.
     */
    public void deletePhotos(ArrayList<Photo> selectedPhotos)
    {
        if (selectedPhotos != null) {
            photoFolder.removeAll(selectedPhotos);
        }
    }

    /**
     * Returns this photo folder.
     *
     * @return this photo folder.
     */
    public List<Photo> getPhotoFolder() {
        return this.photoFolder;
    }

//    TODO: Add a method to cut out and paste photos from one folder to another maybe.

//    Code below might be redundant.
//    The following methods add one single photo and deletes one single photo.
//    Might be enough implementing only the code above for adding or deleting several.
//    As long as we handle one single photo object as a collection.
//    TODO: Decide whether we should use separate methods for adding/deleting one vs several photos.

    /**
     * Add a single photo to this folder.
     *
     * @param selectedPhoto the photo to add to this folder.
     */
    public void addOnePhoto(Photo selectedPhoto) {
        if (selectedPhoto != null) {
            photoFolder.add(selectedPhoto);
        }
    }

    /**
     * Deletes the one selected photo from this folder.
     *
     * @param selectedPhoto the photo to delete from this folder.
     */
    public void deleteOnePhoto(Photo selectedPhoto) {
        if (selectedPhoto != null) {
            photoFolder.remove(selectedPhoto);
        }
    }
}
