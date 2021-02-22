package com.project.carrental.services;

import com.project.carrental.daofactory.DAOFactory;
import com.project.carrental.entities.Order;
import com.project.carrental.idao.IOrderDAO;

public class PaymentService {
    IOrderDAO orderDAO = DAOFactory.getOrderDAO();

    public int confirmPayment(int orderId) {
        Order order = orderDAO.findByID(orderId);
        order.setPaid(true);
        return orderDAO.update(order);
    }
}
