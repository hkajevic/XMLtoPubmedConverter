package net.cyrillicsoftware.xmltopubmedconverter.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.cyrillicsoftware.xmltopubmedconverter.model.Doc;
import net.cyrillicsoftware.xmltopubmedconverter.services.DocStorageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
public class DocControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DocStorageService docStorageService;

    @Test
    @DisplayName("Test 0 - Successful request")
    public void check() throws Exception {
        mvc.perform(get("/req"))
                .andExpect(content().bytes("good".getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    @DisplayName("Test 1 - File uploaded successfully")
    public void givenSmallMultipartFile_whenUploadedFile_thenFileSuccessfullyUploaded() throws Exception {

        Doc doc = new Doc("input1.xml", "application/xml", "<article>Article Name</article>".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "input1.xml",
                "application/xml",
                "<article>Article Name</article>".getBytes(StandardCharsets.UTF_8));
        given(docStorageService.saveFile(multipartFile)).willReturn(doc);

        mvc.perform(multipart("/uploadFile").file(multipartFile))
                .andExpect(status().isOk())
                .andExpect(content().string("File successfully uploaded!"));

        then(docStorageService).should().saveFile(multipartFile);
    }
    @Test
    @DisplayName("Test 2 - File uploaded successfully")
    public void givenBigMultipartFile_whenUploadedFile_thenFileSuccessfullyUploaded() throws Exception {

        String data =
                "<note>\n" +
                "  <to>Luka</to>\n" +
                "  <from>Janko</from>\n" +
                "  <heading>Reminder</heading>\n" +
                "  <body>Don't forget me this weekend!</body>\n" +
                "  <date>30-Mar-22</date>\n" +
                "  <time>4:16</time>\n" +
                "</note>";

        Doc doc = new Doc("input1.xml", "application/xml", data.getBytes(StandardCharsets.UTF_8));
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "input1.xml",
                "application/xml",
                data.getBytes(StandardCharsets.UTF_8));
        given(docStorageService.saveFile(multipartFile)).willReturn(doc);

        mvc.perform(multipart("/uploadFile").file(multipartFile))
                .andExpect(status().isOk())
                .andExpect(content().string("File successfully uploaded!"));

        then(docStorageService).should().saveFile(multipartFile);
    }
    @Test
    @DisplayName("Test 3 - File not application/xml type")
    public void givenTextMultipartFile_whenUploadedFile_thenFileUnsuccessfullyUploaded() throws Exception {

        Doc doc = new Doc("input1.xml", "text/plain", "<article>Article Name</article>".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "input1.xml",
                "text/plain",
                "<article>Article Name</article>".getBytes(StandardCharsets.UTF_8));
        given(docStorageService.saveFile(multipartFile)).willReturn(doc);

        mvc.perform(multipart("/uploadFile").file(multipartFile));

        //exception is handled in global exception handler
        //org.junit.jupiter.api.Assertions.assertThrows(WrongFormatException.class, () -> {
        //    mvc.perform(multipart("/uploadFile").file(multipartFile));
        //});
        verify(docStorageService, never()).saveFile(any(MultipartFile.class));
    }
    @Test
    @DisplayName("Test 4 - File not application/xml type")
    public void givenMultipartFile_whenUploadedFile_thenFileUnsuccessfullyUploaded() throws Exception {

        Doc doc = new Doc("input1.xml", "application/pdf", "<article>Article Name</article>".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "input1.xml",
                "application/pdf",
                "<article>Article Name</article>".getBytes(StandardCharsets.UTF_8));
        given(docStorageService.saveFile(multipartFile)).willReturn(doc);

        mvc.perform(multipart("/uploadFile").file(multipartFile));

        verify(docStorageService, never()).saveFile(any(MultipartFile.class));
    }

    @Test
    @DisplayName("Test 5 - Download file successfully")
    public void givenFileId_whenDownloadingFile_thenReturnFile() throws Exception {
        long fileId = 1;
        byte[] data = "<article>Article Name</article>".getBytes(StandardCharsets.UTF_8);

        Doc doc = new Doc(
            "input1.xml",
            "application/xml",
             data);

        given(docStorageService.getFile(1L)).willReturn(Optional.of(doc));

        mvc.perform(get("/downloadFile/{fileId}", fileId))
                .andExpect(status().isOk())
                .andExpect(content().bytes(data))
                .andExpect(content().contentType("application/xml"));

    }
    @Test
    @DisplayName("Test 6 - Download file unsuccessfully")
    public void givenWrongFileId_whenDownloadingFile_thenError() throws Exception {
        long fileId = 2;
        byte[] data = "Wrong file ID!".getBytes(StandardCharsets.UTF_8);

        given(docStorageService.getFile(2L)).willReturn(Optional.empty());

        mvc.perform(get("/downloadFile/{fileId}", fileId))
                .andExpect(status().isBadRequest())
                .andExpect(content().bytes(data));

    }
    @Test
    @DisplayName("Test 7 - Download file unsuccessfully")
    public void givenWrongFileId_whenDownloadingFile_thenError_2() throws Exception {
        long fileId = 1;
        byte[] data = "Wrong file ID!".getBytes(StandardCharsets.UTF_8);

        given(docStorageService.getFile(1L)).willReturn(Optional.empty());

        mvc.perform(get("/downloadFile/{fileId}", fileId))
                .andExpect(status().isBadRequest())
                .andExpect(content().bytes(data));

    }


}
