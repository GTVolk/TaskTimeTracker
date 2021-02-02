package ru.devvault.tttracker.dao;

import ru.devvault.tttracker.entity.Task;
import ru.devvault.tttracker.entity.TaskLog;
import ru.devvault.tttracker.entity.User;
import java.util.Date;
import java.util.List;

public interface TaskLogDao extends GenericDao<TaskLog, Integer>{

    List<TaskLog> findByUser(User user, Date startDate, Date endDate);
    long findTaskLogCountByTask(Task task);
    long findTaskLogCountByUser(User user);
}
