package legoset.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
public class Tag {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(nullable=false, unique=false)
    private String text;

    @ManyToMany(mappedBy="tags", cascade=CascadeType.PERSIST)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<LegoSet> legoSets = new HashSet<>();

    public Tag(String text) {
        this.text = text;
    }

}
