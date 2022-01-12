package com.sq.controller.item;
import com.github.pagehelper.PageInfo;
import com.sq.dto.item.ItemBatchMetadata;
import com.sq.dto.item.ItemBatchPostDto;
import com.sq.dto.item.ItemDto;
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

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        ItemBatchPostDto itemBatchPostDto = new ItemBatchPostDto();
        itemBatchPostDto.setPageNum(1);
        itemBatchPostDto.setPageSize(50);
        ItemBatchMetadata itemBatchMetadata = new ItemBatchMetadata();
        itemBatchMetadata.setTotal(10);

        given(itemService.batchGetItems(itemBatchPostDto)).willReturn(itemBatchMetadata);

        mockMvc.perform(post("/item/getAllItems")
                .content("{\n" + "\"pageNum\":1,\n" + "\"pageSize\":50\n" + "}")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("get item by id should return the item by the id")
    public void getItemByIdShouldReturnItemById() throws Exception {
        Long id = 0l;
        ItemDto itemDto = new ItemDto();
        itemDto.setId(0l);
        given(itemService.getItemsById(List.of(id))).willReturn(List.of(itemDto));

        mockMvc.perform(get("/item/getItemsById?ids=0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.object[0].id").value("0"));
    }
}
