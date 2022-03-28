package hello.itemservice.controller;

import hello.itemservice.domain.DeliveryCode;
import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemType;
import hello.itemservice.domain.Region;
import hello.itemservice.dto.ItemDto;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.RegionRepository;
import hello.itemservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.templateparser.ITemplateParser;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final RegionRepository regionRepository;
    private final ItemService itemService;

    @ModelAttribute("regions")
    public Map<String, String> regions() {
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes(){
        return ItemType.values(); // => 이넘 값을 배열로 넘겨줌 (BOOK,FOOD,ETC)
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes(){
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST","빠른"));
        deliveryCodes.add(new DeliveryCode("Normal","일반"));
        deliveryCodes.add(new DeliveryCode("Slow","느린"));
        return deliveryCodes;
    }

    @GetMapping
    public String items(Model model) {
        List<Item> all = itemRepository.findAll();
        model.addAttribute("items", all);

        return "form/items";
    }

    @GetMapping("/{itemId}")
    public String item(Model model, @PathVariable String itemId){
        Item item = itemRepository.findById(itemId).get();

        List<Region> byIdRegion = regionRepository.findByIdRegion(itemId);


        model.addAttribute("myRegion",byIdRegion);
        model.addAttribute("item",item);

        return "form/item";
    }

    @GetMapping("/add")
    public String ItemForm(Model model){
        model.addAttribute("item",new ItemDto());
        return "/form/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute("item") ItemDto itemDto, Model model,
                          RedirectAttributes redirectAttributes) {

        Item item = new Item();
        item.itemUpdate(itemDto);
        if(itemDto.getRegions().size()>0){ //지역 데이터 있으면
            itemService.regionSet(itemDto.getRegions(),item);
        }
        itemRepository.save(item);
        model.addAttribute("item",itemDto);
        redirectAttributes.addAttribute("itemId",item.getId());
        redirectAttributes.addAttribute("status",true);

        return "redirect:/form/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable String itemId,Model model){
        Item item = itemRepository.findById(itemId).get();
        List<Region> byIdRegion = regionRepository.findByIdRegion(itemId);
        ItemDto itemDto = new ItemDto(item);
        itemDto.addRegion(byIdRegion);

        model.addAttribute("item",itemDto);
        return "/form/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String updateItem(@ModelAttribute("item") ItemDto item,Model model){
        itemService.update(item.getId(), item);
        //지역 설정 삭제
        itemService.regionDelete(item.getId());
        List<Region> byIdRegion = regionRepository.findByIdRegion(item.getId());
        Optional<Item> itemEntity = itemRepository.findById(item.getId());
        itemService.regionSet(item.getRegions(),itemEntity.get());

        model.addAttribute("item",item);
        return "redirect:/form/items/{itemId}";
    }


    @PostConstruct
    public void init() {
        itemRepository.save(new Item("A", 50000, 10));
        itemRepository.save(new Item("B", 40000, 10));
        itemRepository.save(new Item("C", 60000, 10));
    }

}
