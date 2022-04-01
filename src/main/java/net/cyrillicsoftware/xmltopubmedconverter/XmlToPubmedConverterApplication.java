package net.cyrillicsoftware.xmltopubmedconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class XmlToPubmedConverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(XmlToPubmedConverterApplication.class, args);
	}


}
