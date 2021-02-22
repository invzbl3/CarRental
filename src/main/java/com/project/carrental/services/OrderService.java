package com.project.carrental.services;

import com.project.carrental.entities.Order;
import com.project.carrental.idao.IOrderDAO;

public class OrderService {

    public int update(IOrderDAO orderDAO, Order order) {
            order.setProcessed(true);
            order.setRejected(false);
            order.setRejectDesc(null);
            return orderDAO.update(order);
    }
}
