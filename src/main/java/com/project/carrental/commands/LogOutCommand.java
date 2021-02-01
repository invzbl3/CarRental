package com.project.carrental.commands;

import com.project.carrental.config.ConfigManager;
import org.apache.log4j.Logger;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class that represents command to log out.
 *
 */
public class LogOutCommand implements ICommand {
    public static final Logger LOGGER = Logger.getLogger(LogOutCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res,
            HttpSession session) throws ServletException, IOException {
        LOGGER.info("Command called: " + this.getClass().getSimpleName());
        session.invalidate();
        return ConfigManager.getInstance()
                .getProperty(ConfigManager.INDEX_PAGE_PATH);
    }
}
