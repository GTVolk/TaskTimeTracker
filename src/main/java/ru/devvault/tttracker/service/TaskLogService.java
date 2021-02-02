package ru.devvault.tttracker.service;

import java.util.List;
import ru.devvault.tttracker.entity.TaskLog;
import ru.devvault.tttracker.util.Result;
import java.util.Date;

public interface TaskLogService {

    public Result<TaskLog> store(
            Integer idTaskLog,
            Integer idTask,
            String username,
            String taskDescription,
            Date taskLogDate,
            int taskMinutes,
            String actionUsername);

    public Result<TaskLog> remove(Integer idTaskLog, String actionUsername);
    public Result<TaskLog> find(Integer idTaskLog, String actionUsername);
    public Result<List<TaskLog>> findByUser(String username, Date startDate, Date endDate, String actionUsername);

}