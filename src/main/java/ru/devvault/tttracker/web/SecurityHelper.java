package ru.devvault.tttracker.web;

import ru.devvault.tttracker.entity.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SecurityHelper {

    static final String SESSION_ATTRIB_USER = "sessionuser";

    private SecurityHelper() {
    }

    public static User getSessionUser(HttpServletRequest request) {

        User user = null;

        HttpSession session = request.getSession(true);
        Object obj = session.getAttribute(SESSION_ATTRIB_USER);

        if (obj instanceof User) {
            user = (User) obj;
        }

        return user;
    }
}
