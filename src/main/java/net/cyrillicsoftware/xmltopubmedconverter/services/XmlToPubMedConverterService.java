package net.cyrillicsoftware.xmltopubmedconverter.services;

import net.cyrillicsoftware.xmltopubmedconverter.exception.WrongFormatException;
import net.cyrillicsoftware.xmltopubmedconverter.model.Doc;
import net.cyrillicsoftware.xmltopubmedconverter.payload.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

@Service
public class XmlToPubMedConverterService {

    @Autowired
    private DocStorageService docStorageService;

    public boolean transform(Long fileId) {
        Optional<Doc> docOptional = docStorageService.getFile(fileId);
        if (docOptional.isEmpty()) return false;

        Doc doc = docOptional.get();
        Doc.writeByte(doc.getData(), Paths.INPUT_PATH);

        return transformationProcess();
    }

    public String upload_transform_download(MultipartFile file) throws IOException {

        if (!file.getContentType().equals("application/xml")) {
            throw new WrongFormatException("", file.getContentType(), "application/xml");
        }
        Doc doc = new Doc(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        Doc.writeByte(doc.getData(), Paths.INPUT_PATH);

        return transformationProcess() ? "output.xml" : null;
    }

    private Source generateSourceFromFile(String path) {
        File file = new File(path);
        return new StreamSource(file);
    }

    private Result generateResultFromFile(String path) {
        File file = new File(path);
        return new StreamResult(file);
    }

    private boolean transformationProcess() {
        Source xmlInput = generateSourceFromFile(Paths.INPUT_PATH);
        Source xsl = generateSourceFromFile(Paths.XSL_PATH);
        Result xmlOutput = generateResultFromFile(Paths.OUTPUT_PATH);
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
            transformer.transform(xmlInput, xmlOutput);
        } catch (TransformerException e) {
            return false;
        }
        return true;
    }

    public Scanner getTransformedFile() throws FileNotFoundException {
        File output = new File(Paths.OUTPUT_PATH);
        Scanner inputScanner = new Scanner(output);
        inputScanner.useDelimiter("\\Z");

        return inputScanner;
    }
}
