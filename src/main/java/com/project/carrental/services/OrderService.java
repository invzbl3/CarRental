package com.project.carrental.services;

import com.project.carrental.daofactory.DAOFactory;
import com.project.carrental.entities.Order;
import com.project.carrental.idao.IOrderDAO;

public class OrderService {
    IOrderDAO orderDAO = DAOFactory.getOrderDAO();

    public int confirmOrder(int orderId) {
        Order order = orderDAO.findByID(orderId);
        order.setProcessed(true);
        order.setRejected(false);
        order.setRejectDesc(null);
        return orderDAO.update(order);
    }

    public int giveVehicle(int orderId) {
        Order order = orderDAO.findByID(orderId);
        order.setPicked(true);
        return orderDAO.update(order);
    }

    public int rejectOrder(int orderId, String rejectDesc) {
        Order order = orderDAO.findByID(orderId);
        order.setProcessed(true);
        order.setRejected(true);
        order.setRejectDesc(rejectDesc);
        return orderDAO.update(order);
    }

    public int resetOrder(int orderId) {
        Order order = orderDAO.findByID(orderId);
        order.setProcessed(false);
        order.setRejected(false);
        order.setRejectDesc(null);
        order.setPicked(false);
        order.setReturned(false);
        order.setDamaged(false);
        order.setDamageDesc(null);
        order.setDamageCost(null);
        order.setPaid(false);
        return orderDAO.update(order);
    }

    public int returnVehicle(int orderId) {
        Order order = orderDAO.findByID(orderId);
        order.setReturned(true);
        return orderDAO.update(order);
    }
}
