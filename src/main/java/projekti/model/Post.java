package projekti.model;

import java.time.LocalDateTime;
import java.util.*;
import javax.persistence.*;
import lombok.*;

import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends AbstractPersistable<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Size(min = 3, max = 120)
    private String content;

    private LocalDateTime dateTime;

    @ManyToMany
    private List<Account> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostComment> comment = new ArrayList<>();

    // Ehkä pagedlistholderia vois kutsua täällä vaihtamaan sivuja??
    // Esim PagedListHolder<Post> getComments(int page) ?
}
