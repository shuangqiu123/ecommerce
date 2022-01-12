package com.sq.controller.item;

import com.sq.dto.ResponseMessage;
import com.sq.dto.item.ItemBatchMetadata;
import com.sq.dto.item.ItemBatchPostDto;
import com.sq.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/getAllItems")
    public ResponseMessage getAllItems(@RequestBody @Valid ItemBatchPostDto itemBatchPostDto) {
        ItemBatchMetadata itemBatchMetadata = itemService.batchGetItems(itemBatchPostDto);
        return new ResponseMessage(200,"success", itemBatchMetadata);
    }

    @GetMapping("/getItemsById")
    public ResponseMessage getItemsById(@RequestParam List<Long> ids) {
        return new ResponseMessage(200,"success", itemService.getItemsById(ids));
    }

}
