package com.sq.service;
import com.github.pagehelper.PageInfo;
import com.sq.mapper.ItemMapper;
import com.sq.pojo.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemMapper itemMapper;

    @Test
    @DisplayName("select all items should return paginated items")
    public void selectAllItemsByPageShouldReturnPaginatedItems() {
        Item item1 = new Item();
        item1.setId(0l);
        Item item2 = new Item();
        item2.setId(0l);
        List<Item> items = List.of(item1, item2);
        given(itemMapper.selectAllItems()).willReturn(items);

        PageInfo<Item> result = itemService.selectAllItemsByPage(50, 1);
        assertEquals(items, result.getList());
    }

    @Test
    @DisplayName("get item should return item by id")
    public void getItemByIdShouldReturnItemById() {
        Item item1 = new Item();
        item1.setId(0l);
        given(itemMapper.selectByPrimaryKey(item1.getId())).willReturn(item1);

        Item result = itemService.getItemById(0l);
        assertEquals(item1, result);
    }

    @Test
    @DisplayName("insert item")
    public void insertItemShouldInsertItem() {
        Item item1 = new Item();
        item1.setId(0l);
        given(itemMapper.insert(item1)).willReturn(1);
        itemService.insertItem(item1);
    }

    @Test
    @DisplayName("delete item")
    public void deleteItemShouldDeleteItem() {
        given(itemMapper.deleteByPrimaryKey(0l)).willReturn(1);
        itemService.deleteItem(0l);
    }
}
