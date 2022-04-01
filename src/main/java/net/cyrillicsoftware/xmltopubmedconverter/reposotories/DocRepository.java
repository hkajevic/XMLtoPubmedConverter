package net.cyrillicsoftware.xmltopubmedconverter.reposotories;

import net.cyrillicsoftware.xmltopubmedconverter.model.Doc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocRepository extends JpaRepository<Doc, Long> {
}
