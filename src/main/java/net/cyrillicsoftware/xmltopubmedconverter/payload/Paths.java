package net.cyrillicsoftware.xmltopubmedconverter.payload;

public class Paths {

    private static final String BASE_PATH = "C:\\Users\\Asus\\Desktop\\posao\\xml-to-pubmed-converter\\" +
            "xml-to-pubmed-converter\\src\\main\\java\\net\\cyrillicsoftware\\xmltopubmedconverter\\transformation\\";

    private static final String TEST_PATH = "C:\\Users\\Asus\\Desktop\\posao\\xml-to-pubmed-converter\\" +
            "xml-to-pubmed-converter\\src\\test\\resources\\";

    public static final String INPUT_PATH = BASE_PATH + "input.xml";
    public static final String XSL_PATH = BASE_PATH + "transformFile.xsl";
    public static final String OUTPUT_PATH = BASE_PATH + "output.xml";

    public static final String EXP_INPUT = TEST_PATH + "expectedInput.xml";
    public static final String EXP_OUTPUT = TEST_PATH + "expectedOutput.xml";


}
