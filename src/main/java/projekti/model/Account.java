package projekti.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account extends AbstractPersistable<Long> {
    //Lisää custom virheviestit näihin
    
    @Column(unique = true)
    @Size(min = 3, max = 11, message="Username should be between 3 to 11 chars and unique.")
    private String username;

    @Size(min = 3, max = 11, message="Firstname should be between 3 to 11 chars.")
    private String firstName;

    @Size(min = 3, max = 11, message="Lastname should be between 3 to 11 chars.")
    private String lastName;

    @Size(min = 5, message="Password minimum length is 5.")
    private String password;

    private Long profilePicId;
}
