package hello.itemservice.repository;

import hello.itemservice.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, String> {

    Item findByitemName(String itemName);

}
