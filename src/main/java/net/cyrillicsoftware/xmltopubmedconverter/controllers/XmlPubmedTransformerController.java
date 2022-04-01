package net.cyrillicsoftware.xmltopubmedconverter.controllers;

import net.cyrillicsoftware.xmltopubmedconverter.exception.WrongFormatException;
import net.cyrillicsoftware.xmltopubmedconverter.model.Doc;
import net.cyrillicsoftware.xmltopubmedconverter.services.DocStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

@RestController
public class XmlPubmedTransformerController {

    private static String BASE_PATH = "C:\\Users\\Asus\\Desktop\\posao\\xml-to-pubmed-converter\\" +
            "xml-to-pubmed-converter\\src\\main\\java\\net\\cyrillicsoftware\\xmltopubmedconverter\\transformation\\";

    private static String INPUT_PATH = BASE_PATH + "input.xml";
    private static String XSL_PATH = BASE_PATH + "transformFile.xsl";
    private static String OUTPUT_PATH = BASE_PATH + "output.xml";

    @Autowired
    private DocStorageService docStorageService;

    @GetMapping("/transform/{fileId}")
    public ResponseEntity<Resource> transform(@PathVariable Long fileId){
        Optional<Doc> docOptional = docStorageService.getFile(fileId);

        if(docOptional.isEmpty()){
            return ResponseEntity.badRequest()
                    .body(new ByteArrayResource("Wrong file ID!".getBytes(StandardCharsets.UTF_8)));
        }

        Doc doc = docOptional.get();
        Doc.writeByte(doc.getData(), INPUT_PATH);

        File inputFile = new File(INPUT_PATH);
        File xslFile = new File(XSL_PATH);
        File outputFile = new File(OUTPUT_PATH);

        Source xmlInput = new StreamSource(inputFile);
        Source xsl = new StreamSource(xslFile);
        Result xmlOutput = new StreamResult(outputFile);

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
            transformer.transform(xmlInput, xmlOutput);
        } catch (TransformerException e) {
            return ResponseEntity.badRequest()
                    .body(new ByteArrayResource("Unsuccessful transformation".getBytes(StandardCharsets.UTF_8)));
        }
        return ResponseEntity.ok()
                .body(new ByteArrayResource("Transformation successful".getBytes(StandardCharsets.UTF_8)));

    }

    @GetMapping("/upload_transform_download")
    public ResponseEntity<Resource> all(@RequestParam("file") MultipartFile file) throws IOException, TransformerException {

        //upload file
        if (!file.getContentType().equals("application/xml")) {
            throw new WrongFormatException("", file.getContentType(), "application/xml");
        }
        Doc doc = new Doc(file.getOriginalFilename(), file.getContentType(), file.getBytes());

        //transform file
        Doc.writeByte(doc.getData(), INPUT_PATH);

        File inputFile = new File(INPUT_PATH);
        File xslFile = new File(XSL_PATH);
        File outputFile = new File(OUTPUT_PATH);

        Source xmlInput = new StreamSource(inputFile);
        Source xsl = new StreamSource(xslFile);
        Result xmlOutput = new StreamResult(outputFile);

        Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
        transformer.transform(xmlInput, xmlOutput);

        //download
        File output = new File(OUTPUT_PATH);
        Scanner inputScanner = new Scanner(output);
        inputScanner.useDelimiter("\\Z");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/xml"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + output.getName() + "\"")
                .body(new ByteArrayResource(inputScanner.next().getBytes(StandardCharsets.UTF_8)));
    }

}
