package com.linkun.api.inventory.mapper;

import com.linkun.inventory.model.Inventory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 库存信息 Mapper 接口
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */
 @Mapper
public interface InventoryMapper extends BaseMapper<Inventory> {

    int updateInvontoryWhenEnough(@Param("bookId") Long bookId, @Param("quantity") Integer quantity);
}
