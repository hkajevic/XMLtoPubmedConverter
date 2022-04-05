package net.cyrillicsoftware.xmltopubmedconverter.controllers;

import net.cyrillicsoftware.xmltopubmedconverter.exception.ResourceNotFoundException;
import net.cyrillicsoftware.xmltopubmedconverter.exception.WrongFormatException;
import net.cyrillicsoftware.xmltopubmedconverter.model.Doc;
import net.cyrillicsoftware.xmltopubmedconverter.payload.Paths;
import net.cyrillicsoftware.xmltopubmedconverter.services.DocStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RestController
public class DocController {

    private final DocStorageService docStorageService;

    public DocController(DocStorageService docStorageService) {
        this.docStorageService = docStorageService;
    }

    @GetMapping("/req")
    public String checkReq() {
        return "good";
    }

    @PostMapping("/uploadFile")
    public String uploadFiles(@RequestParam("file") MultipartFile file) {

        if (!file.getContentType().equals("application/xml")) {
            throw new WrongFormatException("", file.getContentType(), "application/xml");
        }
        docStorageService.saveFile(file);

        return "File successfully uploaded!";
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        Optional<Doc> optDoc = docStorageService.getFile(fileId);

        if(optDoc.isEmpty()){
            return ResponseEntity.badRequest()
                    .body(new ByteArrayResource("Wrong file ID!".getBytes(StandardCharsets.UTF_8)));
        }

        Doc doc = optDoc.get();
        Doc.writeByte(doc.getData(), Paths.OUTPUT_PATH);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + doc.getDocName() + "\"")
                .body(new ByteArrayResource(doc.getData()));
    }

}
