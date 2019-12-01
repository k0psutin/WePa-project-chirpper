package projekti.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import org.springframework.web.multipart.MultipartFile;
import projekti.model.*;
import projekti.repository.*;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private AccountService accountService;

    @Transactional
    public List<Photo> getPhotos(String username) {
        return photoRepository.findAllByAccountUsername(username);
    }

    @Transactional
    public Photo getPhoto(Long id) {
        return photoRepository.getOne(id);
    }

    public void likeAImg(Long id) {
        Photo photo = photoRepository.getOne(id);
        Account acc = accountService.getCurrentUser();
        if (!photo.getLikes().contains(acc)) {
            photo.getLikes().add(acc);
            photoRepository.save(photo);
        }
    }

    // ei toimi herokussa, lisäsin Transactional jos toimis...
    @Transactional
    public boolean uploadPhoto(String username, String story, MultipartFile file) throws IOException {
        Account acc = accountService.getCurrentUser();
        long count = photoRepository.count();

        if (!username.equals(acc.getUsername()) | !file.getContentType().contains("image") || file.getSize() == 0
                || count > 9 || file.getSize() > 50000) {
            System.out.println("Ei lisätä");
            return false;
        }
        
        if(story.length() == 0 | story.length() < 3 | story.length() > 120) {
            return false;
        }

        Photo photo = new Photo();
        photo.setStory(story);
        photo.setContent(file.getBytes());
        photo.setAccount(acc);
        photoRepository.save(photo);
        System.out.println("Tallennettu");
        return true;
    }
}
