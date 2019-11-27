package projekti.model;

import javax.persistence.*;
import lombok.*;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Photo extends AbstractPersistable<Long> {

    @ManyToMany
    private List<Account> likes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    private String story;

    @OneToMany(mappedBy = "photo")
    private List<PhotoComment> comment = new ArrayList<>();

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;
}
