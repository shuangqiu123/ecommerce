package com.sq.order.feign;

import com.sq.dto.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sq-item")
public interface ItemService {
    @GetMapping("/item/getItemById/{id}")
    ResponseMessage getItemById(@PathVariable Long id);

}
