package projekti.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import projekti.model.*;
import projekti.repository.*;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private AccountService accountService;

    public List<Photo> getPhotos(String username) {
        return photoRepository.findAllByAccountUsername(username);
    }

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

    public boolean uploadPhoto(String username, String story, MultipartFile file) throws IOException {
        Account acc = accountService.getAccount(username);
        List<Photo> photos = photoRepository.findAllByAccountUsername(username);
        if (!file.getContentType().contains("image") || file.getSize() == 0 || photos.size() <= 10) {
            return false;
        }
        Photo photo = new Photo();
        photo.setStory(story);
        photo.setContent(file.getBytes());
        photo.setAccount(acc);
        photoRepository.save(photo);
        return true;
    }
}
