package com.cloudalibaba.service;

import com.cloudalibaba.domain.Order;

public interface OrderService {

    /**
     * 创建订单
     * @param order
     */
    void create(Order order);
}

