package hello.itemservice.controller;

import hello.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final ItemRepository itemRepository;

    @GetMapping("/")
    public String main(){
        return "/index";
    }


}
