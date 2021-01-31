package ru.devvault.tttracker.web;

import ru.devvault.tttracker.domain.*;
import ru.devvault.tttracker.service.UserService;
import ru.devvault.tttracker.vo.Result;

import java.util.List;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.devvault.tttracker.domain.User;

@Controller
@RequestMapping("/user")
public class UserHandler extends AbstractHandler {

    @Autowired
    protected UserService userService;
    
    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String find(
            @RequestParam(value = "username", required = true) String username,
            HttpServletRequest request) {

        User sessionUser = SecurityHelper.getSessionUser(request);

        Result<User> ar = userService.find(username, sessionUser.getUsername());

        if (ar.isSuccess()) {

            return getJsonSuccessData(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String store(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request) {

        User sessionUser = SecurityHelper.getSessionUser(request);

        JsonObject jsonObj = parseJsonObject(jsonData);

        Result<User> ar = userService.store(
                jsonObj.getString("username"),
                jsonObj.getString("firstName"),
                jsonObj.getString("lastName"),
                jsonObj.getString("email"),
                jsonObj.getString("password"),
                jsonObj.getString("adminRole").charAt(0),
                sessionUser.getUsername());

        if (ar.isSuccess()) {

            return getJsonSuccessData(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String remove(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request) {

        User sessionUser = SecurityHelper.getSessionUser(request);

        JsonObject jsonObj = parseJsonObject(jsonData);

        Result<User> ar = userService.remove(jsonObj.getString("username"), sessionUser.getUsername());

        if (ar.isSuccess()) {

            return getJsonSuccessMsg(ar.getMsg());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAll(HttpServletRequest request) {

        User sessionUser = SecurityHelper.getSessionUser(request);

        Result<List<User>> ar = userService.findAll(sessionUser.getUsername());

        if (ar.isSuccess()) {

            return getJsonSuccessData(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }
}
