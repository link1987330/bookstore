package com.linkun.api.inventory.remote;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.linkun.api.inventory.dto.InventoryDto;
import com.linkun.api.inventory.exception.InventoryException;
import com.linkun.api.order.dto.OrderItemDto;
import com.linkun.inventory.model.Inventory;
import com.linkun.api.inventory.service.IInventoryService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.linkun.utils.NumberUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 库存信息 remote服务实现类
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */
@Service
public class InventoryRemoteService implements IInventoryRemoteService {

    @Autowired
    private IInventoryService inventoryService;



    @Override
    public Inventory getById(Long id) {
        if (NumberUtils.isInvalid(id)) {
            return null;
        }
        return inventoryService.getById(id);
    }


    @Override
    public void deleteById(Long operatorId,Long id) {
        Inventory inventory = new Inventory();
        inventory.setDefunct(true);
        inventory.setId(id);
        inventory.setUpdateTime(new Date());
        inventory.setUpdateUserId(operatorId);
        inventoryService.updateById(inventory);
    }

    @Override
    public List<Inventory> listByBookIds(List<Long> bookIds) {
        if (CollectionUtils.isEmpty(bookIds)) {
            return Collections.emptyList();
        }

        return inventoryService.listByBookIds(bookIds);
    }

    @Override
    public List<Long> checkLowStockBooks(List<InventoryDto> inventoryDtos) throws InventoryException {
        if(CollectionUtils.isEmpty(inventoryDtos)) {
            throw new InventoryException(InventoryException.ILLEGAL_PARAMS);
        }
        List<Long> bookIdList = inventoryDtos.stream().map(InventoryDto::getBookId).collect(Collectors.toList());
        Map<Long, Integer> bookIdQuaantityMap = inventoryDtos.stream().collect(Collectors.toMap(InventoryDto::getBookId, InventoryDto::getQuantity));
        // 查出数据库中库存
        List<Inventory> inventories = inventoryService.listByBookIds(bookIdList);
        if(CollectionUtils.isEmpty(inventories)) {
            return bookIdList;
        }
        List<Long> lowStockBookIds = new ArrayList<>();
        inventories.forEach(inventory -> {
            // 数据库中库存比下单库存量小，说明该图书库存不足
            if(inventory.getQuantity() < bookIdQuaantityMap.get(inventory.getBookId())) {
                lowStockBookIds.add(inventory.getBookId());
            }
        });
        return lowStockBookIds;
    }

    @Override
    @Transactional
    public void deductInventory(List<InventoryDto> inventoryDtos) throws InventoryException {
        if(CollectionUtils.isEmpty(inventoryDtos)) {
            throw new InventoryException(InventoryException.ILLEGAL_PARAMS);
        }
        for (InventoryDto inventoryDto : inventoryDtos) {
            // 库存充足则扣减，否则抛异常事务回滚
            if(inventoryService.deductInventory(inventoryDto.getBookId(), inventoryDto.getQuantity()) == 0) {
                throw new InventoryException(InventoryException.LOW_INVENTORY);
            }
        }
    }


}

