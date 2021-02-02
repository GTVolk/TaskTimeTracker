package ru.devvault.tttracker.web;

import org.springframework.web.bind.annotation.*;
import ru.devvault.tttracker.service.ProjectService;
import ru.devvault.tttracker.util.Result;

import java.util.List;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import ru.devvault.tttracker.entity.Project;
import ru.devvault.tttracker.entity.User;

@Controller
@RequestMapping("/project")
public class ProjectHandler extends AbstractHandler {

    @Autowired
    protected ProjectService projectService;
    
    @GetMapping(value = "/find", produces = {"application/json"})
    @ResponseBody
    public String find(
            @RequestParam(value = "idProject") Integer idProject,
            HttpServletRequest request
    ) {

        User sessionUser = SecurityHelper.getSessionUser(request);
        Result<Project> ar = projectService.find(idProject, sessionUser.getUsername());

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
        Result<Project> ar = projectService.store(
                getIntegerValue(jsonObj.get("idProject")),
                getIntegerValue(jsonObj.get("idCompany")),
                jsonObj.getString("projectName"),
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
        Result<Project> ar = projectService.remove(
                getIntegerValue(jsonObj.get("idProject")), 
                sessionUser.getUsername());

        if (ar.isSuccess()) {
            return getJsonSuccessMsg(ar.getMsg());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @GetMapping(value = "/findAll", produces = {"application/json"})
    @ResponseBody
    public String findAll(
            HttpServletRequest request
    ) {

        User sessionUser = SecurityHelper.getSessionUser(request);
        Result<List<Project>> ar = projectService.findAll(sessionUser.getUsername());

        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }
}
