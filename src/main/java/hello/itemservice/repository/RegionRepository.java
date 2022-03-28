package hello.itemservice.repository;

import hello.itemservice.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region,Long> {
    Optional<Region> findByName(String name);

    @Query("select r from Region r " +
            "join ItemByRegion ir on r.id = ir.region.id " +
            "where ir.item.id = :ItemId")
    public List<Region> findByIdRegion(@Param("ItemId") String ItemId);
}
