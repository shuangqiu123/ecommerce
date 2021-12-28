package com.sq.controller.item;
import com.github.pagehelper.PageInfo;
import com.sq.pojo.Item;
import com.sq.service.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    @DisplayName("get all items should return paginated item list")
    public void getAllItemsShouldReturnPaginatedItems() throws Exception {
        PageInfo<Item> paginatedItems = new PageInfo<>();

        given(itemService.selectAllItemsByPage(50, 1)).willReturn(paginatedItems);

        mockMvc.perform(get("/item/getAllItems?pageNum=50&pageSize=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("get item by id should return the item by the id")
    public void getItemByIdShouldReturnItemById() throws Exception {
        Long id = 0l;
        Item item = new Item();
        item.setId(0l);
        given(itemService.getItemById(id)).willReturn(item);

        mockMvc.perform(get("/item/getItemById/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.object.id").value("0"));
    }
}
