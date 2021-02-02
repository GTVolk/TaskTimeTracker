package ru.devvault.tttracker.web;

import org.springframework.web.bind.annotation.*;
import ru.devvault.tttracker.entity.User;
import ru.devvault.tttracker.service.UserService;
import ru.devvault.tttracker.util.Result;

import static ru.devvault.tttracker.web.SecurityHelper.SESSION_ATTRIB_USER;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/security")
public class SecurityHandler extends AbstractHandler {

    @Autowired
    protected UserService userService;

    @PostMapping(value = "/logon", produces = {"application/json"})
    @ResponseBody
    public String logon(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            HttpServletRequest request
    ) {

        Result<User> ar = userService.findByUsernamePassword(username, password);

        if (ar.isSuccess()) {

            User user = ar.getData();
            HttpSession session = request.getSession(true);
            session.setAttribute(SESSION_ATTRIB_USER, user);

            return getJsonSuccessData(user);
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value = "/logout", produces = {"application/json"})
    @ResponseBody
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        session.removeAttribute(SESSION_ATTRIB_USER);

        return getJsonSuccessMsg("User logged out...");
    }
}
