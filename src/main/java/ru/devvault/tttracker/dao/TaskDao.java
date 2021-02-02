package ru.devvault.tttracker.dao;

import ru.devvault.tttracker.entity.Task;
import java.util.List;

public interface TaskDao extends GenericDao<Task, Integer>{
   
    List<Task> findAll();
}
