package com.linkun.api.inventory.remote;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.List;

import com.linkun.api.inventory.dto.InventoryDto;
import com.linkun.api.inventory.exception.InventoryException;
import com.linkun.inventory.model.Inventory;


/**
 * <p>
 * 库存信息 remote类
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */

public interface IInventoryRemoteService{
     /**
     * 根据ID获取详情
     *
     * @param id 主键
     * @return
     */
    Inventory getById(Long id);

    /**
    * 根据ID删除
    *
    * @param id 主键
    * @param operatorId 操作者
    */
    void deleteById(@Min(1) @NotNull Long operatorId, @Min(1) @NotNull Long id);

    /**
     * 根据bookId批量查询库存
     * @param bookIds
     * @return
     */
    List<Inventory> listByBookIds(List<Long> bookIds);

    /**
     * 检测库存不足的商品
     * @param inventoryDtos 传入bookId和扣减数量
     * @return 返回库存不足的bookId
     */
    List<Long> checkLowStockBooks(List<InventoryDto> inventoryDtos) throws InventoryException;

    void deductInventory(List<InventoryDto> inventoryDtos) throws InventoryException;
}

