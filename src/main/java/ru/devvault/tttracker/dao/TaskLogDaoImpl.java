package ru.devvault.tttracker.dao;

import ru.devvault.tttracker.entity.Task;
import ru.devvault.tttracker.entity.TaskLog;
import ru.devvault.tttracker.entity.User;
import java.util.Date;
import java.util.List;
import javax.persistence.TemporalType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("taskLogDao")
@Transactional
public class TaskLogDaoImpl extends GenericDaoImpl<TaskLog, Integer> implements TaskLogDao {

    public TaskLogDaoImpl() {
        super(TaskLog.class);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<TaskLog> findByUser(User user, Date startDate, Date endDate) {
        return em.createNamedQuery("TaskLog.findByUser")
                .setParameter("user", user)
                .setParameter("startDate", startDate, TemporalType.DATE)
                .setParameter("endDate", endDate, TemporalType.DATE)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public long findTaskLogCountByTask(Task task) {
        return (Long) em.createNamedQuery("TaskLog.findTaskLogCountByTask")
                .setParameter("task", task)
                .getSingleResult();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public long findTaskLogCountByUser(User user) {
        return (Long) em.createNamedQuery("TaskLog.findTaskLogCountByUser")
                .setParameter("user", user)
                .getSingleResult();
    }
}
