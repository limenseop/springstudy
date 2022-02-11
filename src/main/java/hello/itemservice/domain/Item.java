package hello.itemservice.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
//@Data로 처리하는건 powerful하지만 매우 위험하다!
//따로따로 하자
public class Item {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity = 0;
    //integer쓴 이유 : null 에 대비하는 것


    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
