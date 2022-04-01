package net.cyrillicsoftware.xmltopubmedconverter.services;

import lombok.SneakyThrows;
import net.cyrillicsoftware.xmltopubmedconverter.model.Doc;
import net.cyrillicsoftware.xmltopubmedconverter.reposotories.DocRepository;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DocStorageServiceTests {

    @Autowired
    DocStorageService docStorageService;

    @Mock
    private MultipartFile file;

    @SneakyThrows
    @Test
    public void givenFile_whenSavedFile_thenReturnDoc(){

        given(file.getOriginalFilename()).willReturn("input1.xml");
        given(file.getBytes()).willReturn("<article>Bytes for test</article>".getBytes(StandardCharsets.UTF_8));
        given(file.getContentType()).willReturn("application/xml");

        Doc doc = docStorageService.saveFile(file);

        assertThatNoException();
        assertThat(doc).isNotNull();
        assertThat(doc.getDocName()).isNotEmpty();
        assertThat(doc.getData()).isNotEmpty();

    }

    //File.getBytes checks internaly if access rights are good
    //I can not change them
//    @SneakyThrows
//    @Test
//    public void givenFile_when_SavedFile_thenReturnedNull() {
//        given(file.getOriginalFilename()).willReturn("");
//        given(file.getBytes()).willReturn("".getBytes(StandardCharsets.UTF_8));
//        given(file.getContentType()).willReturn("");
//
//        Doc doc = docStorageService.saveFile(file);
//
//        assertThat(doc).isNull();
//
//    }


}
