package com.project.carrental.idao;

import com.project.carrental.entities.Order;

import java.util.List;

/**
 * An interface for Order DAO
 *
 */
public interface IOrderDAO {

    public int insert(Order order);

    public int update(Order order);

    public int delete(Order order);

    public List<Order> findAll();

    public Order findByID(int orderIDParam);
}