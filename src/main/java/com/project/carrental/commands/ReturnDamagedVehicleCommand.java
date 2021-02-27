package com.project.carrental.commands;

import com.project.carrental.services.OrderService;
import com.project.carrental.util.CommandHelper;
import com.project.carrental.config.ConfigManager;
import com.project.carrental.exceptions.SessionTimeoutException;
import org.apache.log4j.Logger;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class that represents command to make notes in database representing that
 * returned vehicle was damaged.
 *
 */
public class ReturnDamagedVehicleCommand implements ICommand {

    public static final Logger LOGGER = Logger.getLogger(ReturnDamagedVehicleCommand.class);
    private final OrderService orderService;

    public ReturnDamagedVehicleCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res,
            HttpSession session) throws ServletException, IOException {
        LOGGER.info("Command called: " + this.getClass().getSimpleName());
        String page;
        try {
            CommandHelper.validateSession(session);

            System.out.println(req.getParameter(REQ_PARAM_ORDER_ID));
            System.out.println(req.getParameter(REQ_PARAM_DAMAGE_DESC));
            System.out.println(req.getParameter(REQ_PARAM_DAMAGE_COST));

            int orderId = Integer.parseInt(req.getParameter(REQ_PARAM_ORDER_ID));
            double damageCost = Double.parseDouble(req.getParameter(REQ_PARAM_DAMAGE_COST));
            String damageDesc = req.getParameter(REQ_PARAM_DAMAGE_DESC);

            orderService.returnDamagedVehicle(orderId, damageCost, damageDesc);

            page = ConfigManager.getInstance()
                    .getProperty(ConfigManager.ADMIN_PAGE_PATH);
        } catch (SessionTimeoutException e) {
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
