package net.cyrillicsoftware.xmltopubmedconverter.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.nio.charset.StandardCharsets;

@SpringBootTest
public class DocTests {

    @Mock
    private Doc doc;

    @Test
    public void givenByteArrayAndFilePath_whenWritingToFile_thenByteInserted(){

        String OUTPUT_PATH = "C:\\Users\\Asus\\Desktop\\posao\\xml-to-pubmed-converter\\xml-to-pubmed-converter" +
                "\\src\\main\\java\\net\\cyrillicsoftware\\xmltopubmedconverter\\transformation\\output.xml";

        byte[] data = "Testing string".getBytes(StandardCharsets.UTF_8);

        boolean succeed = Doc.writeByte(data, OUTPUT_PATH);

        assertThat(succeed).isEqualTo(true);
    }

    @Test
    public void givenByteArrayAndBadPath_whenWritingToFile_thenExceptionThrown(){
        String OUTPUT_PATH = "Desktop\\wrong.xml";

        byte[] data = "Testing string".getBytes(StandardCharsets.UTF_8);

        boolean succeed = Doc.writeByte(data, OUTPUT_PATH);

        assertThat(succeed).isEqualTo(false);
    }

}
