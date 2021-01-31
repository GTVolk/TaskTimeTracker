package ru.devvault.tttracker.web;

import ru.devvault.tttracker.domain.*;
import ru.devvault.tttracker.service.ProjectService;
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
import ru.devvault.tttracker.domain.Project;
import ru.devvault.tttracker.domain.User;

@Controller
@RequestMapping("/project")
public class ProjectHandler extends AbstractHandler {

    @Autowired
    protected ProjectService projectService;
    
    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String find(
            @RequestParam(value = "idProject", required = true) Integer idProject,
            HttpServletRequest request) {

        User sessionUser = SecurityHelper.getSessionUser(request);
        
        Result<Project> ar = projectService.find(idProject, sessionUser.getUsername());

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

    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String remove(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request) {

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

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAll(
            HttpServletRequest request) {

        User sessionUser = SecurityHelper.getSessionUser(request);
        
        Result<List<Project>> ar = projectService.findAll(sessionUser.getUsername());

        if (ar.isSuccess()) {

            return getJsonSuccessData(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }
}
