package no.ntnu.bildur.ui.controllers;


import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;
import no.ntnu.bildur.model.*;

/**
 * The controller class for MainView.FXML.
 *
 *
 */
import java.io.IOException;
import java.util.stream.Collectors;

public class MainController {

    @FXML
    private BorderPane root; //Imported from the

    @FXML
    private Text titleText;

    private Archive photoArchive;
    private ObservableList<Photo> photoListWrapper;
    private ThumbnailViewController viewController;

    /**
     * Constructor, initialize the archive and the PhotoArch
     */
    public MainController() {
        photoArchive = new ArchiveDB();
        photoListWrapper = FXCollections.observableArrayList(photoArchive.getArchiveList());
    }

    /**
     * Used by the create new album button, opens the dialog for creating a new album.
     */
    public void createAlbum() {
        try {
            DirectoryChooser dc = new DirectoryChooser();
            String outputDir = dc.showDialog(null).toString();

            if (this.viewController == null) {
                throw new MalformedURLException("No URL");
            }
            ExportPDF exportPDF = new ExportPDF();
            List<String> images = this.viewController.getAddToAlbumList();
            exportPDF.exportListToPDF(images,outputDir);
        }
        catch (FileNotFoundException | MalformedURLException e){
            e.printStackTrace();
        }

    }

    /**
     * the logic for an addImage button.
     */
    public void addImage() {
        List<File> addedFiles = this.doShowImageFileChooser();
        // FIXME: 22/04/2020 catch exception if user cancels selection
        List<Photo> addedPhotos = addedFiles.stream()
                .map(Photo::new)
                .collect(Collectors.toList());

        for (Photo photo : addedPhotos) {
            this.photoArchive.importPhoto(photo);
            this.photoListWrapper.add(photo);
        }
    }

    /**
     * Display all the images in the main section
     */
    public void viewAllTags() {
        //todo: add
        this.setTitleText("All Tags");

        Text temp = new Text();
        temp.setText("Display a list of all the tags");
        root.setCenter(temp);
    }

    /**
     * Display all the images in the main section
     */
    public void viewAllCollections() {
        //todo: add
        this.setTitleText("All Collections");

        Text temp = new Text();
        temp.setText("Display a list of all the collections");
        root.setCenter(temp);
    }

    /**
     * This method wil display all the images that is registered in the photoArchive.
     */
    public void viewAllImages() {
        //todo: This is a placeholder for view all images
        this.setTitleText("All Albums");
        Node thumbs;
        try {
            FXMLLoader er = new FXMLLoader(getClass().getResource("ThumbnailView.fxml"));
            thumbs = er.load();
            viewController = er.getController();
            viewController.setPhotoArchive(photoArchive);
            photoListWrapper.addListener(viewController.getListener());
            root.setCenter(thumbs);
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

    /**
     * Display a list over all the albums the user have created.
     */
    public void viewAllAlbums(){
        // soon to be
    }

    /**
     * Displays a dialog for importing files to the user.
     *
     * @return a List of File.
     */
    public List<File> doShowImageFileChooser(){
        FileChooser fileChoser = new FileChooser();

        // filter to only show jpg, gif, png and bmp
        FileChooser.ExtensionFilter imageFileFilter =
                new FileChooser.ExtensionFilter(
                        "Image Files",
                        "*.png", "*.jpg", "*.gif", "*.bmp");
        fileChoser.getExtensionFilters().add(imageFileFilter);

        // TODO: 20/03/2020 Add checks and throws

        // show file chooser
        return fileChoser.showOpenMultipleDialog(null);
    }

    /**
     * Returns an observable version of the photo archive.
     *
     * @return Photo archive as observable list.
     */
    public ObservableList<Photo> getPhotoArchiveWrapper() {
        photoListWrapper = FXCollections.observableArrayList(photoArchive.getArchiveList());

        return photoListWrapper;
    }

    /**
     * Change the title to a new title.
     *
     * @param titleText The new text for the title
     */
    public void setTitleText(String titleText) {
        this.titleText.setText(titleText);
    }


    /**
     * Prints human readable metadata info for an image to system.out.
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
}
