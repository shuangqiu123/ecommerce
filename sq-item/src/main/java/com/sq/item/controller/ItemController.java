package com.sq.item.controller;

import com.github.pagehelper.PageInfo;
import com.sq.dto.ItemDto;
import com.sq.dto.ResponseMessage;
import com.sq.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/getAllItems")
    public ResponseMessage getAllItems(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        ItemDto itemDto = new ItemDto();
        itemDto.setPageNum(pageNum);
        itemDto.setPageSize(pageSize);
        PageInfo pageInfo = itemService.selectAllItemsByPage(itemDto);

        return new ResponseMessage(200,"success", pageInfo);
    }

    @GetMapping("/getItemById/{id}")
    public ResponseMessage getItemById(@PathVariable Long id) {
        return new ResponseMessage(200,"success", itemService.getItemById(id));
    }

}
