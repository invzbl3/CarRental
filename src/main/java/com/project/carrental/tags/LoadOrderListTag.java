package com.project.carrental.tags;

import com.project.carrental.commands.ICommand;
import com.project.carrental.daofactory.DAOFactory;
import com.project.carrental.entities.Order;
import com.project.carrental.idao.IOrderDAO;

import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Defines custom tag that loads order list from database to JSP.
 *
 * @see TagSupport
 */
public class LoadOrderListTag extends TagSupport {

    /**
     * Loads order list from database to JSP.
     *
     * @return SKIP_BODY
     * @throws JspException
     */
    @Override
    public int doStartTag() throws JspException {
        IOrderDAO orderDAO = DAOFactory.getOrderDAO();
        List<Order> orders = orderDAO.findAll();
        pageContext.setAttribute(ICommand.REQ_PARAM_ORDER_LIST, orders);
        return SKIP_BODY;
    }
}