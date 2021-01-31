package ru.devvault.tttracker.dao;

import ru.devvault.tttracker.domain.Task;
import java.util.List;

public interface TaskDao extends GenericDao<Task, Integer>{
   
    public List<Task> findAll();    
}
