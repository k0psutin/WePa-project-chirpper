package projekti.model;

import javax.persistence.Entity;
import lombok.*;
import java.util.*;
import javax.persistence.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account extends AbstractPersistable<Long> {

    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private String password;

    private Long profilePicId;
}
