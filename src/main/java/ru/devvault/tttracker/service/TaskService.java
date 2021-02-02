package ru.devvault.tttracker.service;

import java.util.List;
import ru.devvault.tttracker.entity.Task;
import ru.devvault.tttracker.util.Result;

public interface TaskService {

    Result<Task> store(
            Integer idTask,
            Integer idProject,
            String taskName,
            String actionUsername);

    Result<Task> remove(Integer idTask, String actionUsername);
    Result<Task> find(Integer idTask, String actionUsername);
    Result<List<Task>> findAll(String actionUsername);
}