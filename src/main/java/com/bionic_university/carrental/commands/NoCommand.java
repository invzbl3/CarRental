package com.bionic_university.carrental.commands;

import com.bionic_university.carrental.config.ConfigManager;
import org.apache.log4j.Logger;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Class that represents a command with no parameter that forwards user to index
 * page.
 *
 */
public class NoCommand implements ICommand {

    public static final Logger LOGGER = Logger.getLogger(NoCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res,
            HttpSession session) throws ServletException, IOException {
        LOGGER.info("Command called: " + this.getClass().getSimpleName());
        return ConfigManager.getInstance()
                .getProperty(ConfigManager.INDEX_PAGE_PATH);
    }
}
