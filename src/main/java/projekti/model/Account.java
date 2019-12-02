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
    @Size(min = 3, max = 11)
    private String username;

    @Size(min = 3, max = 11)
    private String firstName;

    @Size(min = 3, max = 11)
    private String lastName;

    @Size(min = 5)
    private String password;

    private Long profilePicId;
}
