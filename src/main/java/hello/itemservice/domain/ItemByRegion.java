package hello.itemservice.domain;

import javax.persistence.*;

@Entity
public class ItemByRegion {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "item_id")
    private Item item;

    public ItemByRegion() {
    }

    public ItemByRegion(Region region, Item item) {
        this.region = region;
        this.item = item;
    }

    public void deleteRegion(){
        this.item.getRegions().remove(this);
        this.region.getItems().remove(this);
    }

}
