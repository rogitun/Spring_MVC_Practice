package hello.itemservice.repository;

import hello.itemservice.domain.ItemByRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IrRepository extends JpaRepository<ItemByRegion,Long> {

    @Query("select ir from ItemByRegion ir " +
            "where ir.region.id = :regionId " +
            "and ir.item.id = :itemId")
    Optional<ItemByRegion> findRelation(@Param("itemId") String itemId,@Param("regionId") Long regionId);

    @Modifying
    @Query(value = "delete from ItemByRegion ir " +
            "where ir.item.id = :itemId")
    void deleteByItems(@Param("itemId") String itemId);

    @Query("select ir from ItemByRegion ir " +
            "where ir.item.id = :itemId")
    List<ItemByRegion> findByItem(@Param("itemId") String itemId);
}
