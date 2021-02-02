package ru.devvault.tttracker.service;

import java.util.List;
import ru.devvault.tttracker.entity.Task;
import ru.devvault.tttracker.util.Result;

public interface TaskService {

    public Result<Task> store(
            Integer idTask,
            Integer idProject,
            String taskName,
            String actionUsername);

    public Result<Task> remove(Integer idTask, String actionUsername);
    public Result<Task> find(Integer idTask, String actionUsername);
    public Result<List<Task>> findAll(String actionUsername);

}