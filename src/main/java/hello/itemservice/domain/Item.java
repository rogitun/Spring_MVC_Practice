package hello.itemservice.domain;

import hello.itemservice.dto.ItemDto;
import hello.itemservice.repository.ItemRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.PipedReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@Getter
public class Item {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2",strategy = "uuid2")
    @Column(name = "item_id")
    private String id;

    private String itemName;
    private int price;
    private int quantity;

    private boolean open; //판매여부

    @OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
    private List<ItemByRegion> regions = new ArrayList<>(); //등록지역
    private ItemType itemType; //상품 종류
    private String deliveryCode; //배송방식

    public Item() {
    }

    public Item(String itemName,int price,int quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public void itemUpdate(ItemDto itemParam){
        this.itemName = itemParam.getItemName();
        price = itemParam.getPrice();
        quantity = itemParam.getQuantity();
        open = itemParam.isOpen();
        itemType = itemParam.getItemType();
        deliveryCode = itemParam.getDeliveryCode();
    }

    public void relationalSet(Region region){
        log.info("test RS = {} ",region);
        ItemByRegion itemByRegion = new ItemByRegion(region, this);
        log.info("test RS = {} ",itemByRegion);
        region.getItems().add(itemByRegion);
        this.regions.add(itemByRegion);
    }
}
