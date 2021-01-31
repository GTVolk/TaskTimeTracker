package ru.devvault.tttracker.web;

import ru.devvault.tttracker.domain.*;
import ru.devvault.tttracker.service.TaskService;
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
import ru.devvault.tttracker.domain.Task;
import ru.devvault.tttracker.domain.User;

@Controller
@RequestMapping("/task")
public class TaskHandler extends AbstractHandler {

    @Autowired
    protected TaskService taskService;
    
    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String find(
            @RequestParam(value = "idTask", required = true) Integer idTask,
            HttpServletRequest request) {

        User sessionUser = SecurityHelper.getSessionUser(request);

        Result<Task> ar = taskService.find(idTask, sessionUser.getUsername());

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

    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String remove(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request) {

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
   
    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
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