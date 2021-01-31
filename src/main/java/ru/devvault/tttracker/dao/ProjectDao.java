package ru.devvault.tttracker.dao;

import ru.devvault.tttracker.domain.Project;
import java.util.List;

public interface ProjectDao extends GenericDao<Project, Integer>{

    public List<Project> findAll();

}
