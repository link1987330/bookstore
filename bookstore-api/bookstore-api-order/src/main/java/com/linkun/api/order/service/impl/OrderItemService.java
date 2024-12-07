package com.linkun.api.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.linkun.api.book.remote.IBookRemoteService;
import com.linkun.api.order.dto.OrderDto;
import com.linkun.api.order.dto.OrderItemDto;
import com.linkun.api.order.exception.OrderException;
import com.linkun.book.model.Book;
import com.linkun.order.model.OrderItem;
import com.linkun.api.order.mapper.OrderItemMapper;
import com.linkun.api.order.service.IOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 下单商品明细 服务实现类
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */
@Service
public class OrderItemService extends ServiceImpl<OrderItemMapper, OrderItem> implements IOrderItemService {

    @Autowired
    private IBookRemoteService bookRemoteService;

    @Override
    public List<OrderItem> createOrderItems(Long operatorId, OrderDto orderDto) throws OrderException {
        if (operatorId == null || orderDto == null || orderDto.getOrderId() == null || !orderDto.checkCreateParams()) {
            throw new OrderException(OrderException.ILLEGAL_PARAMS);
        }

        List<Long> bookIds = orderDto.getOrderItemDtos().stream().map(OrderItemDto::getId).collect(Collectors.toList());
        List<Book> books = bookRemoteService.listByIds(bookIds);
        Map<Long, Book> bookMap = books.stream().collect(Collectors.toMap(Book::getId, i -> i, (o1, o2) -> o1));


        if(CollectionUtils.isEmpty(books)) {
            throw new OrderException(OrderException.BOOK_NOT_EXIST);
        }

        final List<OrderItem> orderItems = new ArrayList<>(orderDto.getOrderItemDtos().size());

        orderDto.getOrderItemDtos().forEach(orderItemDto -> {
            Book book = bookMap.get(orderItemDto.getId());
            OrderItem orderItem = new OrderItem();
            orderItem.setBookId(orderItemDto.getId());
            orderItem.setCreateUserId(operatorId);
            orderItem.setDefunct(false);
            orderItem.setOrderId(orderDto.getOrderId());
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItem.setUnitPrice(book.getSellingPrice());
            orderItem.setUpdateUserId(operatorId);
            orderItems.add(orderItem);
        });
        this.saveBatch(orderItems);

        return orderItems;
    }
}
