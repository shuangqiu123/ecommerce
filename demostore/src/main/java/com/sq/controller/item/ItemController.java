package com.sq.controller.item;

import com.github.pagehelper.PageInfo;
import com.sq.dto.ResponseMessage;
import com.sq.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/getAllItems")
    public ResponseMessage getAllItems(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        PageInfo pageInfo = itemService.selectAllItemsByPage(pageNum, pageSize);

        return new ResponseMessage(200,"success", pageInfo);
    }

    @GetMapping("/getItemById/{id}")
    public ResponseMessage getItemById(@PathVariable Long id) {
        return new ResponseMessage(200,"success", itemService.getItemById(id));
    }

}
