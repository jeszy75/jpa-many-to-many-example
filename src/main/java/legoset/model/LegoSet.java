package legoset.model;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.*;

import jpa.YearConverter;

@NoArgsConstructor
@Data
@Entity
public class LegoSet {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(nullable=false, unique=true)
    private String number;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    @Convert(converter=YearConverter.class)
    private Year year;

    @Column(nullable=false)
    private int pieces;

    @ManyToMany(cascade=CascadeType.PERSIST)
    @JoinTable(joinColumns=@JoinColumn(name="legoset_id"), inverseJoinColumns=@JoinColumn(name="tag_id"))
    @EqualsAndHashCode.Exclude
    private Set<Tag> tags = new HashSet<>();

    public LegoSet addTag(Tag tag) {
        tags.add(tag);
        tag.getLegoSets().add(this);
        return this;
    }

    public LegoSet removeTag(Tag tag) {
        tags.remove(tag);
        tag.getLegoSets().remove(this);
        return this;
    }

    public LegoSet(String number, String name, Year year, int pieces) {
        this.number = number;
        this.name = name;
        this.year = year;
        this.pieces = pieces;
    }

}
