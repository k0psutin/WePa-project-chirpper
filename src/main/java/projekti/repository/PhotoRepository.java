package projekti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.model.Photo;
import java.util.*;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByAccountUsername(String username);
}
