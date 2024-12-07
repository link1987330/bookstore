package com.linkun.api.inventory.service;

import java.util.List;

import com.linkun.api.inventory.exception.InventoryException;
import com.linkun.inventory.model.Inventory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 库存信息 服务类
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */
public interface IInventoryService extends IService<Inventory> {

    List<Inventory> listByBookIds(List<Long> bookIds);

    /**
     * 扣减库存
     * @param bookId
     * @param quantity
     * @return
     */
    int deductInventory(Long bookId, Integer quantity) throws InventoryException;
}
