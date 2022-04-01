package net.cyrillicsoftware.xmltopubmedconverter.services;

import net.cyrillicsoftware.xmltopubmedconverter.model.Doc;
import net.cyrillicsoftware.xmltopubmedconverter.reposotories.DocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class DocStorageService {

    @Autowired
    private DocRepository docRepository;

    public Doc saveFile(MultipartFile file){
        String docName = file.getOriginalFilename();
        try{
            Doc doc = new Doc(docName, file.getContentType(), file.getBytes());
            return docRepository.save(doc);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Optional<Doc> getFile(Long fileId){
        return docRepository.findById(fileId);
    }
    public List<Doc> getFiles(){
        return docRepository.findAll();
    }
}
