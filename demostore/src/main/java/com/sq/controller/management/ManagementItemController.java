package com.sq.controller.management;

import com.sq.dto.ResponseMessage;
import com.sq.dto.item.ItemDto;
import com.sq.pojo.Item;
import com.sq.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management/item")
public class ManagementItemController {

    private final ItemService itemService;

    @GetMapping("/deleteItem")
    public ResponseMessage deleteItem(@RequestParam("itemId") Long id, HttpServletRequest request) {
        ResponseMessage responseMessage = new ResponseMessage();

        if (getUid(request) != 5) {
            responseMessage.setCode(401);
            return responseMessage;
        }
        itemService.deleteItem(id);
        return responseMessage;
    }

    @PostMapping("/insertItem")
    public ResponseMessage insertItem(@RequestBody @Valid ItemDto itemDto, HttpServletRequest request) {
        ResponseMessage responseMessage = new ResponseMessage();
        if (getUid(request) != 5) {
            responseMessage.setCode(401);
            return responseMessage;
        }
        Item item = new Item();
        BeanUtils.copyProperties(itemDto, item);
        itemService.insertItem(item);

        responseMessage.setMessage("success");
        return responseMessage;
    }

    private Long getUid(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return Long.parseLong(userId);
    }
}
