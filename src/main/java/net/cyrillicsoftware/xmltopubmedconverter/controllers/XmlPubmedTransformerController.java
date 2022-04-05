package net.cyrillicsoftware.xmltopubmedconverter.controllers;

import net.cyrillicsoftware.xmltopubmedconverter.exception.WrongFormatException;
import net.cyrillicsoftware.xmltopubmedconverter.model.Doc;
import net.cyrillicsoftware.xmltopubmedconverter.payload.Paths;
import net.cyrillicsoftware.xmltopubmedconverter.services.DocStorageService;
import net.cyrillicsoftware.xmltopubmedconverter.services.XmlToPubMedConverterService;
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

    private final DocStorageService docStorageService;

    private final XmlToPubMedConverterService xmlToPubMedConverterService;

    public XmlPubmedTransformerController(DocStorageService docStorageService,
                                          XmlToPubMedConverterService xmlToPubMedConverterService) {
        this.docStorageService = docStorageService;
        this.xmlToPubMedConverterService = xmlToPubMedConverterService;
    }

    @GetMapping("/transform/{fileId}")
    public ResponseEntity transform(@PathVariable Long fileId){

        if(xmlToPubMedConverterService.transform(fileId)){
            return ResponseEntity.ok()
                    .body("Transformation successful!");
        }
        else return ResponseEntity.badRequest()
                .body("Unsuccessful transformation!");

    }

    @GetMapping("/upload_transform_download")
    public ResponseEntity<Resource> all(@RequestParam("file") MultipartFile file){

        String fileName = null;
        Scanner transformedFile = null;
        try {
            fileName = xmlToPubMedConverterService.upload_transform_download(file);
            transformedFile = xmlToPubMedConverterService.getTransformedFile();
        } catch (IOException e) {
            ResponseEntity.badRequest().body(new ByteArrayResource("Wrong file ID!".getBytes(StandardCharsets.UTF_8)));
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/xml"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + fileName + "\"")
                .body(new ByteArrayResource(transformedFile.next().getBytes(StandardCharsets.UTF_8)));
    }

}
