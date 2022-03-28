package hello.itemservice.service;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemByRegion;
import hello.itemservice.domain.Region;
import hello.itemservice.dto.ItemDto;
import hello.itemservice.repository.IrRepository;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final RegionRepository regionRepository;
    private final IrRepository irRepository;

    @Transactional
    public void update(String itemId, ItemDto itemParam){
        Item item = itemRepository.findById(itemId).get();
        item.itemUpdate(itemParam);
    }

    @Transactional
    public void regionDelete(String itemId){
        List<ItemByRegion> byItem = irRepository.findByItem(itemId);
        if(byItem.size()>0){
            for(int i=0;i<byItem.size();i++){
                byItem.get(i).deleteRegion();
            }
        }
        irRepository.deleteByItems(itemId);
    }

    @Transactional
    public void regionSet(List<String> regions,Item item){
        log.info("size = {}",regions.size());
        for(int i=0;i<regions.size();i++){
            String region = regions.get(i);
            Optional<Region> byName = regionRepository.findByName(region);

            //이미 연결이 되어 있는지 체크
            if(byName.isPresent()){ //이미 등록 되어있으면 => item과 연관 관계 맺고 끝
                Region r = byName.get();
                Optional<ItemByRegion> relation = irRepository.findRelation(item.getId(), r.getId());
                if(!relation.isPresent())
                    item.relationalSet(r);
            }
            else{ //없는 지역이면 => 생성하고 item이랑 연관 관계 맺고 끝
                Region newRegion = new Region(region);
                regionRepository.save(newRegion);
                item.relationalSet(newRegion);
            }
        }
    }



    public void clear(){
        itemRepository.deleteAll();
    }

}
