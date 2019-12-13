package projekti.model;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PostComment extends AbstractPersistable<Long> {

    @NotEmpty
    @Size(min = 3, max = 120)
    private String content;

    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Account user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
