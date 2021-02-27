package com.project.carrental.commands;

import com.project.carrental.config.ConfigManager;
import com.project.carrental.entities.User;
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

    private final UserService userService;

    public LogInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res,
            HttpSession session) throws ServletException, IOException {
        LOGGER.info("Command called: " + this.getClass().getSimpleName());

        String login = req.getParameter(REQ_PARAM_LOGIN);
        String password = req.getParameter(REQ_PARAM_PASSWORD);

        String page;
        User user;

        user = userService.checkLogin(login, password);
        if (user != null) {
            session.setAttribute(SESS_PARAM_USER_NAME, user.getLogin());
            session.setAttribute(SESS_PARAM_USERTYPE_ID, user.getUserTypeID());
            session.setAttribute(SESS_PARAM_USER_ID, user.getUserID());

            page = ConfigManager.getInstance()
                    .getProperty(ConfigManager.INDEX_PAGE_PATH);
            LOGGER.info(user.getLogin() + " logged in");
        } else {
            req.setAttribute(SESS_PARAM_ERROR_MESSAGE, LOGIN_ERROR_MESSAGE);
            page = ConfigManager.getInstance()
                    .getProperty(ConfigManager.ERROR_PAGE_PATH);
            LOGGER.error(login + " login tryout failed");
        }
        return page;
    }
}