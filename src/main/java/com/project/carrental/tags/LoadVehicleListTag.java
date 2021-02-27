package com.project.carrental.tags;

import com.project.carrental.commands.ICommand;
import com.project.carrental.daofactory.DAOFactory;
import com.project.carrental.entities.Vehicle;
import com.project.carrental.idao.IVehicleDAO;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Defines custom tag that loads vehicle list from database to JSP.
 *
 * @see TagSupport
 */
public class LoadVehicleListTag extends TagSupport {

    /**
     * Loads vehicle list from database to JSP.
     *
     * @return SKIP_BODY
     * @throws JspException
     */
    @Override
    public int doStartTag() throws JspException {
        IVehicleDAO vehicleDAO = DAOFactory.getVehicleDAO();
        List<Vehicle> vehicleList = vehicleDAO.findAll();
        pageContext.setAttribute(ICommand.REQ_PARAM_VEHICLE_LIST, vehicleList);
        return SKIP_BODY;
    }
}