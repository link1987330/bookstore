package com.linkun.api.inventory.service.impl;

import java.util.Collections;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.linkun.api.inventory.exception.InventoryException;
import com.linkun.book.model.Book;
import com.linkun.inventory.model.Inventory;
import com.linkun.api.inventory.mapper.InventoryMapper;
import com.linkun.api.inventory.service.IInventoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 库存信息 服务实现类
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */
@Service
public class InventoryService extends ServiceImpl<InventoryMapper, Inventory> implements IInventoryService {

    @Autowired
    private InventoryMapper inventoryMapper;

    @Override
    public List<Inventory> listByBookIds(List<Long> bookIds) {
        if (CollectionUtils.isEmpty(bookIds)) {
            return Collections.emptyList();
        }
        final LambdaQueryWrapper<Inventory> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Inventory::getBookId, bookIds).eq(Inventory::getDefunct, false);

        return this.list(wrapper);
    }

    @Override
    public int deductInventory(Long bookId, Integer quantity) throws InventoryException {
        if ( bookId == null || quantity == null) {
            throw new InventoryException(InventoryException.ILLEGAL_PARAMS);
        }

        return inventoryMapper.updateInvontoryWhenEnough(bookId, quantity);
    }
}
