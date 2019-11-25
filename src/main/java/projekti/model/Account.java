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

    // Mieti järkevät taulukonnimet jne...

    // @OneToMany
    // private List<Follow> follow = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<Post> post = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<Photo> photo = new ArrayList<>();
}
