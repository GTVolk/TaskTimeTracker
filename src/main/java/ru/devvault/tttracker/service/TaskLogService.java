package ru.devvault.tttracker.service;

import java.util.List;
import ru.devvault.tttracker.entity.TaskLog;
import ru.devvault.tttracker.util.Result;
import java.util.Date;

public interface TaskLogService {

    Result<TaskLog> store(
            Integer idTaskLog,
            Integer idTask,
            String username,
            String taskDescription,
            Date taskLogDate,
            int taskMinutes,
            String actionUsername);

    Result<TaskLog> remove(Integer idTaskLog, String actionUsername);
    Result<TaskLog> find(Integer idTaskLog, String actionUsername);
    Result<List<TaskLog>> findByUser(String username, Date startDate, Date endDate, String actionUsername);

}