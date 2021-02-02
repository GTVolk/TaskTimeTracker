package ru.devvault.tttracker.web;

import org.springframework.web.bind.annotation.*;
import ru.devvault.tttracker.service.TaskLogService;
import ru.devvault.tttracker.util.Result;
import static ru.devvault.tttracker.web.SecurityHelper.getSessionUser;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import ru.devvault.tttracker.entity.TaskLog;
import ru.devvault.tttracker.entity.User;

@Controller
@RequestMapping("/taskLog")
public class TaskLogHandler extends AbstractHandler {

    static final SimpleDateFormat DATE_FORMAT_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    protected TaskLogService taskLogService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(DATE_FORMAT_yyyyMMdd, true));
    }

    @GetMapping(value="/find", produces = {"application/json"})
    @ResponseBody
    public String find(
            @RequestParam(value = "idTaskLog") Integer idTaskLog,
            HttpServletRequest request
    ) {

        User sessionUser = getSessionUser(request);
        Result<TaskLog> ar = taskLogService.find(idTaskLog, sessionUser.getUsername());

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
    ) throws ParseException {

        User sessionUser = getSessionUser(request);
        JsonObject jsonObj = parseJsonObject(jsonData);
        String dateVal = jsonObj.getString("taskLogDate");
        Result<TaskLog> ar = taskLogService.store(
                getIntegerValue(jsonObj.get("idTaskLog")),
                getIntegerValue(jsonObj.get("idTask")),
                jsonObj.getString("username"),
                jsonObj.getString("taskDescription"),
                DATE_FORMAT_yyyyMMdd.parse(dateVal),
                jsonObj.getInt("taskMinutes"),
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

        User sessionUser = getSessionUser(request);
        JsonObject jsonObj = parseJsonObject(jsonData);
        Result<TaskLog> ar = taskLogService.remove(
                getIntegerValue(jsonObj.get("idTaskLog")), 
                sessionUser.getUsername());

        if (ar.isSuccess()) {
            return getJsonSuccessMsg(ar.getMsg());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @GetMapping(value = "/findByUser", produces = {"application/json"})
    @ResponseBody
    public String findByUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "startDate") Date startDate,
            @RequestParam(value = "endDate") Date endDate,
            HttpServletRequest request
    ) {

        User sessionUser = getSessionUser(request);
        Result<List<TaskLog>> ar = taskLogService.findByUser(
                username,
                startDate,
                endDate,
                sessionUser.getUsername());

        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }
 }
