package net.cyrillicsoftware.xmltopubmedconverter.controllers;

import net.cyrillicsoftware.xmltopubmedconverter.model.Doc;
import net.cyrillicsoftware.xmltopubmedconverter.services.DocStorageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
public class XmlPubmedTransformerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DocStorageService docStorageService;

    @Test
    @DisplayName("Test 1 - Unsuccessful transformation: Bad file ID")
    public void givenWrongFileId_whenTransformedFile_thenError() throws Exception {
        long fileId = 1;
        byte[] data = "Wrong file ID!".getBytes(StandardCharsets.UTF_8);

        Doc doc = new Doc(
                "input1.xml",
                "application/xml",
                data);

        given(docStorageService.getFile(1L)).willReturn(Optional.empty());

        mvc.perform(get("/transform/{fileId}", fileId))
                .andExpect(status().isBadRequest())
                .andExpect(content().bytes(data));

    }
    @Test
    @DisplayName("Test 2 - Successful transformation")
    public void given_when_then() throws Exception {
        long fileId = 1;
        File inputFile = new File("C:\\Users\\Asus\\Desktop\\posao\\xml-to-pubmed-converter\\" +
                "xml-to-pubmed-converter\\src\\test\\java\\net\\cyrillicsoftware\\xmltopubmedconverter\\" +
                "controllers\\files\\expectedInput.xml");
        File outputFile = new File("C:\\Users\\Asus\\Desktop\\posao\\xml-to-pubmed-converter\\" +
                "xml-to-pubmed-converter\\src\\test\\java\\net\\cyrillicsoftware\\xmltopubmedconverter\\" +
                "controllers\\files\\expectedOutput.xml");

        Scanner inputScanner = new Scanner(inputFile);
        inputScanner.useDelimiter("\\Z");

        Scanner outputScanner = new Scanner(outputFile);
        outputScanner.useDelimiter("\\Z");

        Doc doc = new Doc(
                "input1.xml",
                "application/xml",
                inputScanner.next().getBytes(StandardCharsets.UTF_8));

        given(docStorageService.getFile(1L)).willReturn(Optional.of(doc));

        mvc.perform(get("/transform/{fileId}", fileId))
                .andExpect(status().isOk())
                .andExpect(content().bytes("Transformation successful".getBytes(StandardCharsets.UTF_8)));

        File originalOutputFile = new File("C:\\Users\\Asus\\Desktop\\posao\\xml-to-pubmed-converter\\" +
                "xml-to-pubmed-converter\\src\\main\\java\\net\\cyrillicsoftware\\" +
                "xmltopubmedconverter\\transformation\\output.xml");

        Scanner originalScanner = new Scanner(originalOutputFile);
        originalScanner.useDelimiter("\\Z");

        Assertions.assertThat(originalScanner.next().toString().trim().equals("\n"))
                .isEqualTo(outputScanner.next().toString().trim().equals("\n"));

    }

}
