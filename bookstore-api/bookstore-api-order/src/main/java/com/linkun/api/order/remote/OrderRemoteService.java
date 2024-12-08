package com.linkun.api.order.remote;

import com.linkun.api.inventory.dto.InventoryDto;
import com.linkun.api.inventory.exception.InventoryException;
import com.linkun.api.inventory.remote.IInventoryRemoteService;
import com.linkun.api.order.dto.OrderDto;
import com.linkun.api.order.dto.OrderItemDto;
import com.linkun.api.order.exception.OrderException;
import com.linkun.api.order.service.IOrderItemService;
import com.linkun.inventory.model.Inventory;
import com.linkun.order.model.Order;
import com.linkun.api.order.service.IOrderService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.linkun.order.model.OrderItem;
import com.linkun.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单信息 remote服务实现类
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */
@Service
public class OrderRemoteService implements IOrderRemoteService {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private IInventoryRemoteService inventoryRemoteService;

    @Override
    public Order getById(Long id) {
        if (NumberUtils.isInvalid(id)) {
            return null;
        }
        return orderService.getById(id);
    }

    @Override
    public void deleteById(Long operatorId,Long id) {
        Order order = new Order();
        order.setDefunct(true);
        order.setId(id);
        order.setUpdateTime(new Date());
        order.setUpdateUserId(operatorId);
        orderService.updateById(order);
    }

    @Override
    public Order create(Long operatorId, OrderDto orderDto) throws OrderException, InventoryException {
        if (operatorId == null || orderDto == null || !orderDto.checkCreateParams()) {
            throw new OrderException(OrderException.ILLEGAL_PARAMS);
        }

        List<Long> bookIdList = orderDto.getOrderItemDtos().stream().map(OrderItemDto::getId).collect(Collectors.toList());
        List<InventoryDto> inventoryDtoList = orderDto.getOrderItemDtos().stream()
                .map(orderItemDto -> new InventoryDto(orderItemDto.getId(), orderItemDto.getQuantity()))
                .collect(Collectors.toList());

//        // 检测库存
//        List<Long> lowStockBooks = inventoryRemoteService.checkLowStockBooks(inventoryDtoList);
//        if(CollectionUtils.isNotEmpty(lowStockBooks)) {
//
//        }
        // 扣减库存
        inventoryRemoteService.deductInventory(inventoryDtoList);

        // 创建订单
        Order order = orderService.createOrder(operatorId, orderDto);
        List<OrderItem> orderItems = orderItemService.createOrderItems(operatorId, orderDto);
        BigDecimal totalPrice = orderItems.stream()
                .map(book -> book.getUnitPrice().multiply(BigDecimal.valueOf(book.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalPrice);

        orderService.updateById(order);

        return order;
    }

    @Override
    public void cancelById(Long operatorId, Long id) {

    }


}

