package projekti.model;

import javax.persistence.Entity;
import lombok.*;
import javax.persistence.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account extends AbstractPersistable<Long> {

    @Column(unique = true)
    private String username;
    private String firstname;
    private String lastname;
    private String password;

    private Long profilePicId;
}
