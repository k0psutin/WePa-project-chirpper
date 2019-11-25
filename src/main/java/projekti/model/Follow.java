package projekti.model;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;
import java.util.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Follow extends AbstractPersistable<Long> {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Account user;
    @ManyToOne
    @JoinColumn(name = "follow_user_id")
    private Account following;

    private Boolean blocked;

    private String date;
    private String time;
}
