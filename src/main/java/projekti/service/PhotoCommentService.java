package projekti.service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projekti.model.*;
import projekti.repository.*;

@Service
public class PhotoCommentService {

        @Autowired
        private PhotoCommentRepository photoCommentRepository;

        @Autowired
        private PhotoRepository photoRepository;

        @Autowired
        private AccountService accountService;

        @Transactional
        public void createComment(Long id, String comment, String user) {
                Account acc = accountService.getCurrentUser();
                PhotoComment cmt = new PhotoComment();
                Photo photo = photoRepository.getOne(id);

                cmt.setContent(comment);
                cmt.setDateTime(LocalDateTime.now());
                cmt.setUser(acc);
                cmt.setPhoto(photo);
                photo.getComment().add(cmt);

                photoCommentRepository.save(cmt);
                photoRepository.save(photo);
        }
}
