package ru.devvault.tttracker.service;

import java.util.List;
import ru.devvault.tttracker.entity.Project;
import ru.devvault.tttracker.util.Result;

public interface ProjectService {

    Result<Project> store(
            Integer idProject,
            Integer idCompany,
            String projectName,
            String actionUsername);

    Result<Project> remove(Integer idProject, String actionUsername);
    Result<Project> find(Integer idProject, String actionUsername);
    Result<List<Project>> findAll(String actionUsername);
}