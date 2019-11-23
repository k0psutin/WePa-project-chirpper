package projekti.model;

import javax.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
public class Comment extends AbstractPersistable<Long> {

    private String content;
    private String time;
    private String date;
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
