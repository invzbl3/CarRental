package com.bionic_university.carrental.util;

import static com.bionic_university.carrental.commands.ICommand.SESS_PARAM_USER_NAME;

import com.bionic_university.carrental.datasource.ConnectionPool;
import com.bionic_university.carrental.exceptions.SessionTimeoutException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;

/**
 * Class contains auxiliary methods for commands package
 *
 */
public class CommandHelper {
    public static final Logger LOGGER = Logger.getLogger(CommandHelper.class);

    /**
     * Method checks if session is still valid, or it has expired.
     *
     * @param session
     * @throws SessionTimeoutException
     */
    public static void validateSession(HttpSession session) throws SessionTimeoutException {
        try {
            LOGGER.info("Validate session called. Session id: " + session.getId());
            session.setAttribute(SESS_PARAM_USER_NAME,
                    session.getAttribute(SESS_PARAM_USER_NAME));
        } catch (NullPointerException e) {
            LOGGER.warn("Session expired");
            throw new SessionTimeoutException(e);
        }
    }
}