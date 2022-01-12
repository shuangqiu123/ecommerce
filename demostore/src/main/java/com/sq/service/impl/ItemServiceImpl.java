package com.sq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sq.constant.Category;
import com.sq.dto.item.ItemBatchDisplay;
import com.sq.dto.item.ItemBatchMetadata;
import com.sq.dto.item.ItemBatchPostDto;
import com.sq.dto.item.ItemDto;
import com.sq.dto.order.OrderItemDto;
import com.sq.mapper.ItemMapper;
import com.sq.mapper.condition.ItemBatchSearchCondition;
import com.sq.service.ItemService;
import com.sq.pojo.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemMapper itemMapper;

    @Override
    public ItemBatchMetadata batchGetItems(ItemBatchPostDto itemBatchPostDto) {
        int pageNum = itemBatchPostDto.getPageNum();
        int pageSize = itemBatchPostDto.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        ItemBatchSearchCondition itemBatchSearchCondition = new ItemBatchSearchCondition();
        BeanUtils.copyProperties(itemBatchPostDto, itemBatchSearchCondition);
        if (itemBatchPostDto.getSort() != null) {
            itemBatchSearchCondition.setSort(itemBatchPostDto.getSort().getValue());
        }

        List<Item> items = itemMapper.selectItemsByCondition(itemBatchSearchCondition);
        List<ItemBatchDisplay> itemBatchDisplayList = items.stream()
                .filter(item -> {
                    if (itemBatchPostDto.getCategory() == Category.NEW_IN) {
                        Date created = item.getCreated();
                        Date now = new Date();
                        if (!isNewIn(created, now)) {
                            return false;
                        }
                    }
                    else if (itemBatchPostDto.getCategory() == Category.POPULAR) {
                        int i = new Random().nextInt(4);
                        return i != 0;
                    }
                    return true;
                })
                .map(item -> {
                    ItemBatchDisplay itemBatchDisplay = new ItemBatchDisplay();
                    BeanUtils.copyProperties(item, itemBatchDisplay);
                    Date created = item.getCreated();
                    Date now = new Date();
                    itemBatchDisplay.setIsNewIn(isNewIn(created, now));
                    return itemBatchDisplay;
                })
                .collect(Collectors.toList());

        ItemBatchMetadata itemBatchMetadata = new ItemBatchMetadata();
        itemBatchMetadata.setItemBatchDisplayList(itemBatchDisplayList);
        itemBatchMetadata.setTotal(itemBatchDisplayList.size());
        return itemBatchMetadata;
    }

    @Override
    public List<ItemDto> getItemsById(List<Long> ids) {
        List<ItemDto> itemDtos = ids.stream().map(id -> {
            Item item = itemMapper.selectByPrimaryKey(id);
            return item;
        }).filter(item -> item != null)
                .map(item -> {
                    ItemDto itemDto = new ItemDto();
                    Date created = item.getCreated();
                    Date now = new Date();
                    BeanUtils.copyProperties(item, itemDto);
                    itemDto.setIsNewIn(isNewIn(created, now));
                    return itemDto;
                })
                .collect(Collectors.toList());
        return itemDtos;
    }

    @Override
    public Item getItemById(Long id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    @Override
    public void insertItem(Item item) {
        item.setCreated(new Date());
        item.setUpdated(new Date());
        itemMapper.insert(item);
    }

    @Override
    public void deleteItem(Long id) {
        itemMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public OrderItemDto checkItemAvailability(Long id, Integer quantity) {
        Item item = itemMapper.selectByPrimaryKey(id);
        if (item == null || item.getNum() == 0) {
            return null;
        }
        OrderItemDto orderItemDto = new OrderItemDto(id, quantity);
        if (quantity > item.getNum()) {
            orderItemDto.setQuantity(item.getNum());
        }
        return orderItemDto;
    }

    private boolean isNewIn(Date created, Date now) {
        if (now.getTime() - created.getTime() <= 15780000L * 1000) {
            return true;
        }
        return false;
    }
}
