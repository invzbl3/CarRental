package com.project.carrental.commands;

import com.project.carrental.services.PaymentService;
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
 * Class that represents command to confirm that payment for certain order has
 * been received.
 *
 */
public class ConfirmPaymentCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(ConfirmPaymentCommand.class);

    private final PaymentService paymentService;

    public ConfirmPaymentCommand(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res,
                          HttpSession session) throws ServletException, IOException {
        LOGGER.info("Command called: " + this.getClass().getSimpleName());
        String page;
        try {
            CommandHelper.validateSession(session);

            int orderId = Integer.parseInt(req.getParameter(REQ_PARAM_ORDER_ID));
            paymentService.confirmPayment(orderId);

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