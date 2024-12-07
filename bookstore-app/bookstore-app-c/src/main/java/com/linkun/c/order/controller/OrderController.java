package com.linkun.c.order.controller;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

import com.linkun.api.book.dto.BookDto;
import com.linkun.api.book.exception.BookException;
import com.linkun.api.inventory.dto.InventoryDto;
import com.linkun.api.inventory.exception.InventoryException;
import com.linkun.api.inventory.remote.IInventoryRemoteService;
import com.linkun.api.order.dto.OrderDto;
import com.linkun.api.order.exception.OrderException;
import com.linkun.book.model.Book;
import com.linkun.order.model.Order;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.linkun.c.core.controller.BaseController;
import com.linkun.c.core.exception.NeedLoginException;
import com.linkun.c.order.service.IOrderService;
import com.linkun.c.order.view.OrderView;
import com.linkun.response.JsonResult;


@RestController
@RequestMapping("order")
public class OrderController extends BaseController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IInventoryRemoteService inventoryRemoteService;

    @PostMapping
    public JsonResult createOrder(HttpServletRequest request, @RequestBody OrderDto orderDto)
            throws NeedLoginException, OrderException, InventoryException {
        Long userId = checkLogin(request);

        List<InventoryDto> inventoryDtoList = orderDto.getOrderItemDtos().stream()
                .map(orderItemDto -> new InventoryDto(orderItemDto.getId(), orderItemDto.getQuantity()))
                .collect(Collectors.toList());
        List<Long> bookIds = inventoryRemoteService.checkLowStockBooks(inventoryDtoList);
        if(CollectionUtils.isNotEmpty(bookIds)) {
            // 存在库存不足商品，抛异常并将对应商品ID返回
            JsonResult result = JsonResult.error(InventoryException.LOW_INVENTORY, messageSource);
            result.setData(bookIds);
            return result;
        }

        return new JsonResult<OrderView>().success(orderService.create(userId, orderDto));
    }

    @PostMapping("/{id}")
    public JsonResult cancelOrder(HttpServletRequest request, @PathVariable Long id)
            throws NeedLoginException, BookException {
        Long userId = checkLogin(request);

        orderService.cancelById(userId, id);

        return JsonResult.success();
    }
}
