package com.project.carrental.commands;

import com.project.carrental.config.ConfigManager;
import com.project.carrental.daofactory.DAOFactory;
import com.project.carrental.entities.User;
import com.project.carrental.idao.IUserDAO;
import com.project.carrental.services.UserService;
import org.apache.log4j.Logger;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class that represents command to log in.
 *
 */
public class LogInCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(LogInCommand.class);

    /*private static final int LOGIN_ERROR = -1;
    private static final int ACC_TYPE_ADMIN = 1;
    static final int ACC_TYPE_CLIENT = 2;*/

    private final UserService userService;

    public LogInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res,
            HttpSession session) throws ServletException, IOException {
        LOGGER.info("Command called: " + this.getClass().getSimpleName());

        //get DAO and input data
        //IUserDAO userDAO = DAOFactory.getUserDAO();
        String login = req.getParameter(REQ_PARAM_LOGIN);
        String password = req.getParameter(REQ_PARAM_PASSWORD);

        /*//declare variables
        String page;
        User user;
        int userID;*/

        return userService.logInCommand(login, password, session);
    }
}