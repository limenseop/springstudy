package hello.itemservice.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
//component scan의 대상이 된다! + db에 연동되는 경우 db예외를 처리해줌
//우리는 memory저장소 사용 -> component처리에 의의
public class ItemRepository {

    private static final Map<Long,Item> store = new HashMap<>();
    private static long sequence = 0L;

    //m

    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(),item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());
        //stores를 그대로 줘도 되긴 함
        //arrayList에 pack에서 준 이유 = 원본에 damage를 주지 않기 위해서
    }

    public void update(Long itemId,Item updateParam){
        Item findit = findById(itemId);
        findit.setItemName(updateParam.getItemName());
        findit.setPrice(updateParam.getPrice());
        findit.setQuantity(updateParam.getQuantity());
        //update의 방법 : update할 정보를 가지는 새로운 item객체를 생성
        //tip : 사실 여기서 update용 객체는 item을 그대로 쓰는게 아니라
        //itemDTO라는 update용 class를 따로 만드는게 이상적
        //이유 : update용 item은 id정보를 쓰지 않기에 해당 field가 비어버림
        //규모가 커지면 그냥 update용 class를 따로 만드는게 더 명확한 설계가 가능해진다
        //중복 vs 명확성 = 명확성을 따르는게 이득이다!
    }

    public void clearStore(){
        store.clear();
    }
}
