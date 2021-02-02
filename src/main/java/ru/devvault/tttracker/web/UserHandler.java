package ru.devvault.tttracker.web;

import org.springframework.web.bind.annotation.*;
import ru.devvault.tttracker.service.UserService;
import ru.devvault.tttracker.util.Result;

import java.util.List;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import ru.devvault.tttracker.entity.User;

@Controller
@RequestMapping("/user")
public class UserHandler extends AbstractHandler {

    @Autowired
    protected UserService userService;
    
    @GetMapping(value = "/find", produces = {"application/json"})
    @ResponseBody
    public String find(
            @RequestParam(value = "username") String username,
            HttpServletRequest request
    ) {

        User sessionUser = SecurityHelper.getSessionUser(request);
        Result<User> ar = userService.find(username, sessionUser.getUsername());

        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @PostMapping(value = "/store", produces = {"application/json"})
    @ResponseBody
    public String store(
            @RequestParam(value = "data") String jsonData,
            HttpServletRequest request
    ) {

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

    @PostMapping(value = "/remove", produces = {"application/json"})
    @ResponseBody
    public String remove(
            @RequestParam(value = "data") String jsonData,
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

    @GetMapping(value = "/findAll", produces = {"application/json"})
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
