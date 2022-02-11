package hello.itemservice.web.basic;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }


    @GetMapping("/add")
    //애는 등록 폼을 보여주는 역할의 controller
    //이 controller는 등록의 처리(post)는 아직 처리
    //그전에 등록의 html을 보내주는 controller
    public String addForm() {
        return "basic/addForm";
    }

    //@PostMapping("/add")
    //이게 정보를 보내서 등록하는 url request
    //addForm(form을 보내는것)과 url은 같음
    //메서드에 따라 둘중 하나가 mapping
    public String savev1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){
        Item item = new Item(itemName,price,quantity);
        itemRepository.save(item);
        model.addAttribute("item",item);
        return "basic/item";

        //version 1 : @RequestParam으로 넘어온 데이터를 일일히 처리

    }
    //이 2개가 같은 url에서 메서드로 기능을 분리하는 pattenr
    //매우 자주쓰이는 pattern

    @PostMapping("/add")
    //이게 정보를 보내서 등록하는 url request
    //addForm(form을 보내는것)과 url은 같음
    //메서드에 따라 둘중 하나가 mapping
    public String additem(@ModelAttribute("item")Item item, RedirectAttributes redirectAttributes){
        //model attribute가 자동으로 item을 만들어 준다
        Item saved = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",saved.getId());
        //redirectAttribute라는 객체를 adapter로부터 제공받은후
        //redirect시 필요한 동적정보들을 그 객체에 일단 담는다(key,value)

        redirectAttributes.addAttribute("status",true);

        return "redirect:/basic/items/{itemId}";
        //redirect의 call시 -> redirectAttribute의 객체에 있는 데이터를 먼저 참조함
        //redirect정보에 들어가지 못한 나머지 data들?(status) -> query parameter에 들어가게 된다

    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId,Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "/basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable long itemId,Item item){
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}";
        //redirection을 요청(3xx)
        //이렇게 하는 이유 : return해서 서버 내에서 view를 직접 호출해도 url이 남음
        //redirection으로 깔끔하게 다시 url 설정하여 접근하게 함

    }
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,320));
        //실험용 initializer
    }

}
