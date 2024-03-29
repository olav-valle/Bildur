package no.ntnu.bildur.ui.controllers;

import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Iterator;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import no.ntnu.bildur.model.Archive;
import no.ntnu.bildur.model.MetadataController;
import no.ntnu.bildur.model.Photo;


/**
 * The controller class for ThumbnailViewController.XML
 * ThumbnailView will display photos as thumbnails.
 */
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
    private List<String> addToAlbumList;
    private Photo focusedPhoto = null;


    public ThumbnailViewController() {

    }

    /**
     * Adds an ImageView to the thumbnails and returns the created ImageView object as a thumbnail.
     *
     * @param photo the Photo to add.
     * @return and ImageView of the Photo object as a thumbnail.
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
     * Ths method is a workaround for not being able to use a normal constructor <br>
     * as a result of using FXML. Sets this class archive so Search functions ans such can accesses it.
     *
     * @param archive the archive to display
     */
    public void setPhotoArchive(Archive archive) {
        this.photoArchive = archive;
        photoArchive.getArchiveList().forEach(photo -> {
            this.addThumbnail(photo);
        });

    }

    /**
     * Return a list with all the URLs for all the photos added to the <code>addToAlbumList</code>.
     *
     * @return list with all the URLs for all the photos added to the <code>addToAlbumList</code>.
     */
    public List<String> getAddToAlbumList(){
        //todo: make a better code for this
        if (this.addToAlbumList == null) return null;
        List<String> returnThis = new ArrayList<>(this.addToAlbumList);

        return returnThis;
    }

    /**
     * Clears the <code>addToAlbumList</code>.
     */
    public void clearAddToAlbumList(){
        this.addToAlbumList.clear();
    }

    /**
     * It does searching, for pictures, based on user input.<br>
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
     * Takes in a photo object and displays it's information to<br>
     * the user on the right side in the app, and sets the focusedPhoto to that photo.
     *
     * @param photo Photo object to display.
     */
    public void displayPhotoInfo(Photo photo) {
        //Todo: split this in smaller methods.
        Image image = new Image(String.valueOf(photo.getURI()));
        Metadata meta = MetadataController.readMetadata(photo.getImageFile());
        Iterable<Directory> dirs = meta.getDirectories();

        // PhotoThumbnail
        this.focusedPhoto = photo;
        this.photoName.setText(photo.getFileName());
        this.previewImage.setImage(image);
        this.previewImage.maxWidth(200);

        // Metadata
        int i = 0;
        this.metadataDisplay.getChildren().clear();
        for(Directory dir : dirs) {
            TextFlow metadata = new TextFlow(new Text(dir.getName()));
            metadata.setStyle("-fx-background-color: #BDBFBE;");

            // metadata group
            this.metadataDisplay.add(metadata,0,i,2,1);
            Collection<Tag> tags = dir.getTags();
            i++;
            for(Tag t : tags) {

                // Tag Name
                TextFlow tagName = new TextFlow(new Text(t.getTagName() + ": "));
                tagName.setTextAlignment(TextAlignment.RIGHT);
                this.metadataDisplay.add(tagName,0,i);

                // Tag description
                String tagDescription = t.getDescription();
                if (tagDescription.length() > 300){
                    tagDescription = tagDescription.substring(0,350) + "...";
                }
                this.metadataDisplay.add(new TextFlow(new Text(tagDescription)),1,i);
                i++;
            }
        }

        // tags
        this.tagsFlowPane.getChildren().clear(); // Empty the tags field.
        Iterator<String> photos = photo.getTagIterator();
        while (photos.hasNext()){
            String tag = photos.next();
            if (!tag.equals("")){
                Label tagLable = new Label(tag);
                this.tagsFlowPane.getChildren().add(tagLable);
            }
        }
    }

    /**
     * Adds teh focused Picture to the <code>addToAlbumList</code>.
     *
     * @return a boolean that tells if the operation was a success.
     */
    public boolean addToAlbum(){
        if (this.focusedPhoto == null) return false; // cant adda  photo if no photo is selected :V
        if (this.addToAlbumList == null){
            this.addToAlbumList = new ArrayList<>();
        }

        String photoURI = this.focusedPhoto.getPathString();
        this.addToAlbumList.add(photoURI);
        return true;
    }

    /**
     * For testing.
     */
    private void doPrintMetadata(Photo photo) {
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
     * Returns a ListChangeListener so newly add pictures are added as thumbnails in the thumbnail view
     * @return ListChangeListener
     */
    public ListChangeListener<Photo> getListener(){

        //I have no idea what i am doing... but it's working :)
        //todo: refactor this with a more relevant observer pattern.
        return new ListChangeListener<Photo>() {
            @Override
            public void onChanged(Change<? extends Photo> c) {
                while (c.next()){
                    if (c.wasPermutated()){
                        for (int i = c.getFrom(); i < c.getTo(); ++i) {
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
