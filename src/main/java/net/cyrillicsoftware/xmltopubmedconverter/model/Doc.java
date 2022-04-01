package net.cyrillicsoftware.xmltopubmedconverter.model;

import javax.persistence.*;
import java.io.*;

@Entity
@Table(name = "doc")
public class Doc {

    private static String INPUT_PATH = "C:\\Users\\Asus\\Desktop\\posao\\xml-to-pubmed-converter\\xml-to-pubmed-converter\\src\\main\\java\\net\\cyrillicsoftware\\xmltopubmedconverter\\transformation\\input.xml";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String docName;
    private String docType;

    @Lob
    private byte[] data;

    public Doc() {
    }

    public Doc(String docName, String docType, byte[] data) {
        this.docName = docName;
        this.docType = docType;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public static boolean writeByte(byte[] bytes, String filePath){
        File file = new File(filePath);
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(bytes);
            os.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
