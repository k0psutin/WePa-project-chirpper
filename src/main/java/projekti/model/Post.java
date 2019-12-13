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

    @OneToMany(mappedBy = "post", cascade=CascadeType.ALL)
    private List<PostComment> comment = new ArrayList<>();

    public List<PostComment> getComment() {
        this.comment.sort(Comparator.comparing(PostComment::getDateTime).reversed());
        if (this.comment.size() > 10) {
            return this.comment.subList(0, 10);
        } else {
            return this.comment;
        }
    }
}
