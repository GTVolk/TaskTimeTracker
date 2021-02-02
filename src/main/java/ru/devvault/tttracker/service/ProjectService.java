package ru.devvault.tttracker.service;

import java.util.List;
import ru.devvault.tttracker.entity.Project;
import ru.devvault.tttracker.util.Result;

public interface ProjectService {

    public Result<Project> store(
            Integer idProject,
            Integer idCompany,
            String projectName,
            String actionUsername);

    public Result<Project> remove(Integer idProject, String actionUsername);
    public Result<Project> find(Integer idProject, String actionUsername);
    public Result<List<Project>> findAll(String actionUsername);

}