package no.ntnu.bildur.model;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;

public class ExportPDF {
    private static final String TEST_IMG = "C:/Users/NekoFlaa/IdeaProjects/bildur_maven/images/Cat_1.jpg";
    private static final String OUTPUT_FOLDER = "C:/Users/NekoFlaa/IdeaProjects/bildur_maven/images/";

    public static void main(String[] args){
        new ExportPDF();
    }

    public ExportPDF(){
    }

    /**
     * Takes in a list of image URL and adds the images the URL is pointing at to a pdf document.
     *
     * @param imageURL List of URLs to add.
     *
     * @throws FileNotFoundException Cant find the file of the URL.
     * @throws MalformedURLException A malformed URL has occurred.
     */
    public void exportListToPDF(List<String> imageURL, String outputDir)
            throws FileNotFoundException, MalformedURLException{
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputDir + "/MyAlbum.pdf"));
        Document document = new Document(pdfDocument);

        for (String pictureURL : imageURL){
            ImageData imageData = ImageDataFactory.create(pictureURL);
            Image image = new Image(imageData);
            image.setWidth(pdfDocument.getDefaultPageSize().getWidth() - 50);
            image.setAutoScaleHeight(true);
            document.add(image);
        }
        pdfDocument.close();
    }


}