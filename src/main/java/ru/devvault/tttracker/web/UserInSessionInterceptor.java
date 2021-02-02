package ru.devvault.tttracker.web;

import ru.devvault.tttracker.entity.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class UserInSessionInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.info("calling preHandle with url=" + request.getRequestURI());

        User sessionUser = SecurityHelper.getSessionUser(request);

        if (sessionUser == null) {

            String json = "{\"success\":false,\"msg\":\"A valid user is not logged on!\"}";
            response.getOutputStream().write(json.getBytes());

            return false;
        } else {
            return true;
        }
    }
}
