package hello.itemservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();
    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
        //afterEach : 각각의 test의 종료시마다 처리해야 할 작업
    }

    @Test
    void save(){
        //given
        Item item = new Item("itemA",10000,10);
        //when
        itemRepository.save(item);
        //then
        assertThat(itemRepository.findById(item.getId())).isEqualTo(item);
    }

    @Test
    void updateitem(){
        //given
        Item item1 = new Item("itemA",20000,10);
        //when
        Item saved = itemRepository.save(item1);
        Long itemid = saved.getId();
        //then
        Item item2 = new Item("itemB",40000,1000);
        itemRepository.update(itemid,item2);
        Item finditem = itemRepository.findById(itemid);
        assertThat(finditem.getItemName()).isEqualTo(item2.getItemName());
        assertThat(finditem.getPrice()).isEqualTo(item2.getPrice());
    }

    @Test
    void findall(){
        //given
        Item item = new Item("itemA",20000, 10);
        Item item2 = new Item("item2",30000,30);
        //when
        itemRepository.save(item);
        itemRepository.save(item2);
        //then
        assertThat(itemRepository.findAll().size()).isEqualTo(2);
        assertThat(itemRepository.findAll()).contains(item,item2);
    }
}