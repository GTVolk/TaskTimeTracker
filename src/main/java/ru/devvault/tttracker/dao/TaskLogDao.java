package ru.devvault.tttracker.dao;

import ru.devvault.tttracker.entity.Task;
import ru.devvault.tttracker.entity.TaskLog;
import ru.devvault.tttracker.entity.User;
import java.util.Date;
import java.util.List;

public interface TaskLogDao extends GenericDao<TaskLog, Integer>{

    public List<TaskLog> findByUser(User user, Date startDate, Date endDate);
    
    public long findTaskLogCountByTask(Task task);
    
    public long findTaskLogCountByUser(User user);
}
