package com.linkun.c.order.service;

import com.linkun.api.book.dto.BookDto;
import com.linkun.api.order.dto.OrderDto;
import com.linkun.api.order.exception.OrderException;
import com.linkun.c.order.view.OrderView;

public interface IOrderService {

    OrderView getOrderViewById(Long id);

    OrderView create(Long operatorId, OrderDto orderDto) throws OrderException;

    void cancelById(Long operatorId, Long id);

}
