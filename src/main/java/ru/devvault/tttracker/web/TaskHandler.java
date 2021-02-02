package ru.devvault.tttracker.web;

import org.springframework.web.bind.annotation.*;
import ru.devvault.tttracker.service.TaskService;
import ru.devvault.tttracker.util.Result;

import java.util.List;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import ru.devvault.tttracker.entity.Task;
import ru.devvault.tttracker.entity.User;

@Controller
@RequestMapping("/task")
public class TaskHandler extends AbstractHandler {

    @Autowired
    protected TaskService taskService;
    
    @GetMapping(value = "/find", produces = {"application/json"})
    @ResponseBody
    public String find(
            @RequestParam(value = "idTask") Integer idTask,
            HttpServletRequest request
    ) {

        User sessionUser = SecurityHelper.getSessionUser(request);
        Result<Task> ar = taskService.find(idTask, sessionUser.getUsername());

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
        Result<Task> ar = taskService.store(
                getIntegerValue(jsonObj.get("idTask")),
                getIntegerValue(jsonObj.get("idProject")),
                jsonObj.getString("taskName"),
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
            HttpServletRequest request
    ) {

        User sessionUser = SecurityHelper.getSessionUser(request);
        JsonObject jsonObj = parseJsonObject(jsonData);
        Result<Task> ar = taskService.remove(
                getIntegerValue(jsonObj.get("idTask")), 
                sessionUser.getUsername());

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
        Result<List<Task>> ar = taskService.findAll(sessionUser.getUsername());

        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }
}
