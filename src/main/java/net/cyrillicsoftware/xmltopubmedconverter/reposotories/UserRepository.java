package net.cyrillicsoftware.xmltopubmedconverter.reposotories;

import net.cyrillicsoftware.xmltopubmedconverter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
