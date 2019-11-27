package projekti.model;

import javax.persistence.Entity;
import lombok.*;
import javax.persistence.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chirpper_accs")
public class Account extends AbstractPersistable<Long> {

    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private String password;

    private Long profilePicId;
}
