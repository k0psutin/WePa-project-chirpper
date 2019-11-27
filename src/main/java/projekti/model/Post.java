package projekti.model;

import java.util.*;
import javax.persistence.*;
import lombok.*;

import org.hibernate.annotations.BatchSize;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends AbstractPersistable<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    private String subject;
    private String content;
    private String date;
    private String time;

    @ManyToMany
    private List<Account> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostComment> comment = new ArrayList<>();

}
