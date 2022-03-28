package hello.itemservice.dto;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemType;
import hello.itemservice.domain.Region;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemDto {

    private String id;
    private String itemName;
    private int price;
    private int quantity;

    private boolean open; //판매여부
    private ItemType itemType; //상품 종류
    private String deliveryCode; //배송방식
    private List<String> regions = new ArrayList<>();

    public ItemDto() {
    }

    public ItemDto(Item item) {
        this.id = item.getId();
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.quantity = item.getQuantity();
    }

    public void addRegion(List<Region> regions){
        for(int i=0;i<regions.size();i++){
            this.regions.add(regions.get(i).getName());
        }
    }

//    public Item toEntity(){
//        Item item = new Item(this.itemName,this.price,this.quantity);
//        return item;
//    }
}
