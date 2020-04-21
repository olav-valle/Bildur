package no.ntnu.bildur.ui.controllers;

import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import no.ntnu.bildur.model.Archive;
import no.ntnu.bildur.model.MetadataController;
import no.ntnu.bildur.model.Photo;
import no.ntnu.bildur.model.PhotoTag;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Iterator;

public class ThumbnailViewController {

    @FXML
    private Pane thumbnailsContainer;

    @FXML
    private TextField searchBar;

    @FXML
    private FlowPane tagsFlowPane;

    @FXML
    private GridPane metadataDisplay;

    @FXML
    private ImageView previewImage;

    @FXML
    private TextField addTagInput;

    @FXML
    private Text photoName;

    private Archive photoArchive;
    private Photo focusedPhoto = null;

    public ThumbnailViewController() {

    }

    /**
     * Adds an ImageView to the thumbnails.
     *
     * @param photo the Photo to add.
     */
    public ImageView addThumbnail(Photo photo) {

        ImageView thumb = new ImageView();
        thumb.setImage(new Image(String.valueOf(photo.getURI())));

        thumb.setFitHeight(200);
        thumb.setFitWidth(200);
        thumb.setPreserveRatio(true);
        thumb.setPickOnBounds(true);

        thumb.setOnMouseClicked(mouseEvent -> displayPhotoInfo(photo));

        this.thumbnailsContainer.getChildren().add(thumb);
        return thumb;
    }

    /**
     * Ths method is a workaround for not being able to use a normal constructor
     * as a result of using FXML. It sets the Photoarcive to be the same as
     * @param archive
     */
    public void setPhotoArchive(Archive archive) {
        this.photoArchive = archive;
        photoArchive.getArchiveList().forEach(photo -> {
            this.addThumbnail(photo);
        });

    }

    /**
     * It does searching, for pictures, based on user input.
     * Reads the text in the search bar and displays the images that match the search.
     */
    public void doSearch() {
        if (this.photoArchive == null){
            throw new InvalidParameterException("The photoArchive is not set.");
        }
        String searchArgument = this.searchBar.getText();
        this.thumbnailsContainer.getChildren().clear();

        photoArchive.getArchiveList().stream()
                .filter(photo -> (photo.getFileName()).contains(searchArgument))
                .forEach(this::addThumbnail);
    }

    /**
     * Takes in a photo object and displays it's information to
     * the user on the right side in the app, and sets the focusedPhoto to that photo.
     *
     * @param photo Photo object to display.
     */
    public void displayPhotoInfo(Photo photo) {
        Image image = new Image(String.valueOf(photo.getURI()));
        Metadata meta = MetadataController.readMetadata(photo.getImageFile());
        Iterable<Directory> dirs = meta.getDirectories();

        // Photo
        this.focusedPhoto = photo;
        this.photoName.setText(photo.getFileName());
        this.previewImage.setImage(image);
        this.previewImage.maxWidth(200);

        // Metadata
        int i = 0;
        this.metadataDisplay.getChildren().clear();
        for(Directory dir : dirs) {
            //System.out.println(dir.getName());
            this.metadataDisplay.add(new Text(dir.getName()),0,i,2,1);
            Collection<Tag> tags = dir.getTags();
            i++;
            for(Tag t : tags) {
                //System.out.println("   " + t.getTagName() + ": " + t.getDescription());
                this.metadataDisplay.add(new Text(t.getTagName() + ": "),0,i);
                this.metadataDisplay.add(new Text(t.getDescription()),1,i);
                i++;
            }
        }

        // tags
        this.tagsFlowPane.getChildren().clear();
        Iterator<PhotoTag> photos = photo.getTagIterator();
        while (photos.hasNext()){
            Label tag = new Label(photos.next().getValue());
            this.tagsFlowPane.getChildren().add(tag);
        }
    }


    /**
     *
     */
    public void doPrintMetadata(Photo photo) {
        Metadata meta = MetadataController.readMetadata(photo.getImageFile());
        Iterable<Directory> dirs = meta.getDirectories();
        for(Directory dir : dirs) {
            System.out.println(dir.getName());
            Collection<Tag> tags = dir.getTags();
            for(Tag t : tags) {
                System.out.println("   " + t.getTagName() + ": " + t.getDescription());
            }
        }
    }

    /**
     * Get a tag from the user and adds it to the relevant Photo
     */
    public void getTagFromUser() {
        String addedTag = addTagInput.getText();
        addTagInput.clear();
        tagsFlowPane.getChildren().add(new Label(addedTag));
        this.photoArchive.addTagToPhoto(addedTag, focusedPhoto);
    }


    /**
     * Returns a ListChangeListener.
     * @return
     */
    public ListChangeListener<Photo> getListener(){
        //I have no idea what i am doing... but it's working so im fine with it :)
        return new ListChangeListener<Photo>() {
            @Override
            public void onChanged(Change<? extends Photo> c) {
                while (c.next()){
                    if (c.wasPermutated()){
                        for (int i = c.getFrom(); i < c.getTo(); ++i) {
                            //permutate
                        }
                    }
                    else if(c.wasUpdated()){

                    }
                    else {
                        for (Photo photo : c.getAddedSubList()){
                            ImageView thumb = addThumbnail(photo);
                            thumb.setOnMouseClicked(mouseEvent -> displayPhotoInfo(photo));
                        }
                    }
                }
            }
        };
    }
}
