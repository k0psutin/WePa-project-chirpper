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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                cmt.setTime(LocalTime.now().format(formatter));
                cmt.setDate(LocalDate.now().format(formatter2));
                cmt.setUser(acc);
                cmt.setPhoto(photo);
                photo.getComment().add(cmt);

                photoCommentRepository.save(cmt);
                photoRepository.save(photo);
        }
}
