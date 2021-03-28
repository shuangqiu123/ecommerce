package com.sq.item;

import com.sq.dto.ItemDto;
import com.sq.item.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Test
    public void testItems() {
        ItemDto itemDto = new ItemDto();
        itemDto.setPageSize(50);
        itemDto.setPageNum(1);
        itemService.selectAllItemsByPage(itemDto);
    }
}
