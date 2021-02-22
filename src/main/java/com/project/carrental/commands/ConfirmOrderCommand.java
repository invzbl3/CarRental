package com.project.carrental.commands;

import com.project.carrental.services.OrderService;
import com.project.carrental.util.CommandHelper;
import com.project.carrental.config.ConfigManager;
import com.project.carrental.dao.DAOHelper;
import com.project.carrental.daofactory.DAOFactory;
import com.project.carrental.entities.Order;
import com.project.carrental.exceptions.SessionTimeoutException;
import com.project.carrental.idao.IOrderDAO;
import org.apache.log4j.Logger;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class that represents command to confirm the order by manager.
 *
 */
public class ConfirmOrderCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(ConfirmOrderCommand.class);
    private final OrderService orderService;

    public ConfirmOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res,
                          HttpSession session) throws ServletException, IOException {
        LOGGER.info("Command called: " + this.getClass().getSimpleName());
        String page;
        try {
            CommandHelper.validateSession(session);

            //IOrderDAO orderDAO = DAOFactory.getOrderDAO();
            /*Order order = orderDAO.findByID(Integer.parseInt(req.
                    getParameter(REQ_PARAM_ORDER_ID)));*/
            int orderId = Integer.parseInt(req.getParameter(REQ_PARAM_ORDER_ID));
            /*order.setProcessed(true);
            order.setRejected(false);
            order.setRejectDesc(null);
            int updateOrderCode = orderDAO.update(order);*/

            int updateOrderCode = orderService.update(orderId);

            if (updateOrderCode == DAOHelper.EXECUTE_UPDATE_ERROR_CODE) {
                throw new IllegalArgumentException("Order entry in DB was not updated");
            }

            page = ConfigManager.getInstance()
                    .getProperty(ConfigManager.ADMIN_PAGE_PATH);
        } catch (SessionTimeoutException e) {
            LOGGER.error("session timed out: " + e);
            req.setAttribute(SESS_PARAM_ERROR_MESSAGE, SESSION_TIMEOUT_ERROR_MESSAGE);
            page = ConfigManager.getInstance()
                    .getProperty(ConfigManager.ERROR_PAGE_PATH);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error while updating order " + e);
            req.setAttribute(SESS_PARAM_ERROR_MESSAGE, ORDER_NOT_UPDATED_ERROR_MESSAGE);
            page = ConfigManager.getInstance()
                    .getProperty(ConfigManager.ERROR_PAGE_PATH);
        }
        return page;
    }
}