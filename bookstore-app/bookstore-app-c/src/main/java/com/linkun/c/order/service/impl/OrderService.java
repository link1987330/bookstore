package com.linkun.c.order.service.impl;

import com.linkun.api.book.dto.BookDto;
import com.linkun.api.book.exception.BookException;
import com.linkun.api.order.dto.OrderDto;
import com.linkun.api.order.exception.OrderException;
import com.linkun.api.order.remote.IOrderRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkun.api.user.remote.IUserRemoteService;
import com.linkun.c.order.service.IOrderService;
import com.linkun.c.order.view.OrderView;
import com.linkun.user.model.User;


@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRemoteService orderRemoteService;


    @Override
    public OrderView getOrderViewById(Long id) {
        return null;
    }

    @Override
    public OrderView create(Long operatorId, OrderDto orderDto) throws OrderException {
        return null;
    }

    @Override
    public void cancelById(Long operatorId, Long id) {

    }
}
