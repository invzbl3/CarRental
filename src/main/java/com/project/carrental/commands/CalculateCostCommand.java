package com.project.carrental.commands;

import com.project.carrental.services.VehicleService;
import com.project.carrental.util.CommandHelper;
import com.project.carrental.config.ConfigManager;
import com.project.carrental.exceptions.SessionTimeoutException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * Class that represents command to calculate the rent cost for specified
 * vehicle and dates.
 *
 */
public class CalculateCostCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(CalculateCostCommand.class);
    private final VehicleService vehicleService;

    public CalculateCostCommand(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res,
                          HttpSession session) throws ServletException, IOException {
        LOGGER.info("Command called: " + this.getClass().getSimpleName());
        String page;
        try {
            CommandHelper.validateSession(session);

            int vehicleID = Integer.parseInt(req.getParameter(REQ_PARAM_VEHICLE_CHOICE));
            req.setAttribute(REQ_PARAM_VEHICLE_ID, vehicleID);

            String tmpPick = req.getParameter(REQ_PARAM_PICK_UP_DATE);
            String tmpDrop = req.getParameter(REQ_PARAM_DROP_OFF_DATE);

            Timestamp pick = Timestamp.valueOf(convertDateFormat(tmpPick));
            Timestamp drop = Timestamp.valueOf(convertDateFormat(tmpDrop));
            req.setAttribute(REQ_PARAM_PICK_UP_DATE, tmpPick);
            req.setAttribute(REQ_PARAM_DROP_OFF_DATE, tmpDrop);

            BigDecimal rentCost = vehicleService.calculateCost(vehicleID, pick, drop);

            req.setAttribute(REQ_PARAM_RENT_COST, rentCost);

            page = ConfigManager.getInstance()
                    .getProperty(ConfigManager.ORDER_PAGE_PATH);
        } catch (SessionTimeoutException e) {
            LOGGER.error("session timed out: " + e);
            req.setAttribute(SESS_PARAM_ERROR_MESSAGE, SESSION_TIMEOUT_ERROR_MESSAGE);
            page = ConfigManager.getInstance()
                    .getProperty(ConfigManager.ERROR_PAGE_PATH);
        }
        return page;
    }

    //auxiliary method for converting date format from HTML to Java
    static String convertDateFormat(String htmlDate) {
        String[] separateDateTime = htmlDate.split("T");
        return separateDateTime[0] +
                " " +
                separateDateTime[1] +
                ":00";
    }
}