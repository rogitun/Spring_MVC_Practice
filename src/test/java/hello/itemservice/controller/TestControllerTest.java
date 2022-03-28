package hello.itemservice.controller;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.service.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class TestControllerTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;

    @Test
    public void save(){
        Item item = new Item("phone",50000,100);
        Item item2 = new Item("phone2",40000,100);
        Item item3 = new Item("phone3",30000,100);

        itemRepository.save(item);
        itemRepository.save(item2);
        itemRepository.save(item3);
        Item phone = itemRepository.findByitemName("phone");

        assertThat(phone.getItemName()).isEqualTo(item.getItemName());

        List<Item> all = itemRepository.findAll();

        for (Item item1 : all) {
            System.out.println("item1 = " + item1);
        }
    }

    @Test
    public void update(){
        Item item = new Item("phone",50000,100);
        itemRepository.save(item);

        Item upt = new Item("car",300000,5);

        Item phone = itemRepository.findByitemName("phone");
        //itemService.update(phone.getId(), upt);

        Item car = itemRepository.findByitemName("car");

        assertThat(upt.getItemName()).isEqualTo(car.getItemName());


    }

}