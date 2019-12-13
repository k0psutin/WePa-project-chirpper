package projekti.model;

import java.time.LocalDateTime;

import javax.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Follow extends AbstractPersistable<Long> {

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "follow_id")
    private Account follow;

    private Boolean blocked;

    private LocalDateTime dateTime;
}
