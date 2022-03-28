package hello.itemservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.naming.Name;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Region {

    @Id @GeneratedValue()
    @Column(name = "region_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "region",cascade = CascadeType.ALL)
    List<ItemByRegion> items = new ArrayList<>();

    public Region(String name) {
        this.name = name;
    }
}
