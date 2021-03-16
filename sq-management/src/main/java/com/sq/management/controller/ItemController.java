package com.sq.management.controller;

import com.sq.dto.ResponseMessage;
import com.sq.management.service.ItemService;
import com.sq.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/management/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

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
    public ResponseMessage insertItem(@RequestBody Item item, HttpServletRequest request) {
        ResponseMessage responseMessage = new ResponseMessage();
        if (getUid(request) != 5) {
            responseMessage.setCode(401);
            return responseMessage;
        }

        itemService.insertItem(item);

        responseMessage.setMessage("success");
        return responseMessage;
    }

    private Long getUid(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return Long.parseLong(userId);
    }
}
