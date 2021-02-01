package com.project.carrental.commands;

import com.project.carrental.util.CommandHelper;
import com.project.carrental.config.ConfigManager;
import com.project.carrental.dao.DAOHelper;
import com.project.carrental.daofactory.DAOFactory;
import com.project.carrental.entities.Order;
import com.project.carrental.entities.Passport;
import com.project.carrental.entities.User;
import com.project.carrental.entities.Vehicle;
import com.project.carrental.exceptions.SessionTimeoutException;
import com.project.carrental.idao.IOrderDAO;
import com.project.carrental.idao.IPassportDAO;
import com.project.carrental.idao.IUserDAO;
import com.project.carrental.idao.IVehicleDAO;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class that represents command to add information about new order to database.
 *
 */
public class CreateOrderCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(CreateOrderCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res,
            HttpSession session) throws ServletException, IOException {
        LOGGER.info("Command called: " + this.getClass().getSimpleName());
        String page;
        try {
            CommandHelper.validateSession(session);

            //get DAOs
            IPassportDAO passportDAO = DAOFactory.getPassportDAO();
            IOrderDAO orderDAO = DAOFactory.getOrderDAO();
            IVehicleDAO vehicleDAO = DAOFactory.getVehicleDAO();
            IUserDAO userDAO = DAOFactory.getUserDAO();

            //create and insert new passport
            Passport passport = new Passport();
            passport.setLastName(req.getParameter(REQ_PARAM_LAST_NAME));
            passport.setFirstName(req.getParameter(REQ_PARAM_FIRST_NAME));
            passport.setPatronymic(req.getParameter(REQ_PARAM_PATRONYMIC));
            passport.setBirthday(Date.valueOf(req.getParameter(REQ_PARAM_BIRTHDAY)));
            passport.setPassportSeries(req.getParameter(REQ_PARAM_P_SERIES));
            passport.setPassportNumber(req.getParameter(REQ_PARAM_P_NUMBER));
            passport.setWhoIssued(req.getParameter(REQ_PARAM_WHO_ISSUED));
            passport.setWhenIssued(Date.valueOf(req.getParameter(REQ_PARAM_WHEN_ISSUED)));
            int passportID = passportDAO.insert(passport);
            if (passportID == DAOHelper.EXECUTE_UPDATE_ERROR_CODE) {
                throw new IllegalArgumentException("Passport entry in DB was not created");
            } else {
                passport.setPassportID(passportID);
            }

            //create and insert new order
            Order order = new Order();
            int vehicleID = Integer.parseInt(req.getParameter(REQ_PARAM_VEHICLE_ID));
            Vehicle vehicle = vehicleDAO.findByID(vehicleID);
            order.setVehicle(vehicle);
            int userID = (Integer) session.getAttribute(SESS_PARAM_USER_ID);
            User user = userDAO.findByID(userID);
            order.setUser(user);
            order.setPassport(passport);
            order.setPickUpDate(Timestamp.valueOf(CalculateCostCommand
                    .convertDateFormat(req
                            .getParameter(REQ_PARAM_PICK_UP_DATE))));
            order.setDropOffDate(Timestamp.valueOf(CalculateCostCommand
                    .convertDateFormat(req
                            .getParameter(REQ_PARAM_DROP_OFF_DATE))));
            order.setRentCost(BigDecimal.valueOf((Double.parseDouble(req.
                    getParameter(REQ_PARAM_RENT_COST)))));
            int insertOrderCode = orderDAO.insert(order);
            if (insertOrderCode == DAOHelper.EXECUTE_UPDATE_ERROR_CODE) {
                throw new IllegalArgumentException("Order entry in DB was not created");
            }

            page = ConfigManager.getInstance()
                    .getProperty(ConfigManager.INFO_ORDER_PAGE_PATH);
        } catch (SessionTimeoutException e) {
            LOGGER.error("session timed out: " + e);
            req.setAttribute(SESS_PARAM_ERROR_MESSAGE, SESSION_TIMEOUT_ERROR_MESSAGE);
            page = ConfigManager.getInstance()
                    .getProperty(ConfigManager.ERROR_PAGE_PATH);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error while creating order " + e);
            req.setAttribute(SESS_PARAM_ERROR_MESSAGE, ORDER_NOT_CREATED_ERROR_MESSAGE);
            page = ConfigManager.getInstance()
                    .getProperty(ConfigManager.ERROR_PAGE_PATH);
        }
        return page;
    }
}