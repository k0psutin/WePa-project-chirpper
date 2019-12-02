package projekti.model;

import javax.persistence.*;
import javax.validation.constraints.*;

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

    @Size(min = 3, max = 30)
    private String story;

    @OneToMany(mappedBy = "photo")
    private List<PhotoComment> comment = new ArrayList<>();

    @Lob
    @Basic(fetch = FetchType.LAZY)  
    //@Type(type = "org.hibernate.type.BinaryType")
    private byte[] content;
}
