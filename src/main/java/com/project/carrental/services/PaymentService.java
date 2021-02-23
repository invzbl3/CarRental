package com.project.carrental.services;

import com.project.carrental.dao.DAOHelper;
import com.project.carrental.daofactory.DAOFactory;
import com.project.carrental.entities.Order;
import com.project.carrental.idao.IOrderDAO;

public class PaymentService {
    IOrderDAO orderDAO = DAOFactory.getOrderDAO();

    public void confirmPayment(int orderId) {
        Order order = orderDAO.findByID(orderId);
        order.setPaid(true);

        int updateOrderCode = orderDAO.update(order);
        if (updateOrderCode == DAOHelper.EXECUTE_UPDATE_ERROR_CODE) {
            throw new IllegalArgumentException("Order entry in DB was not updated");
        }
    }
}
