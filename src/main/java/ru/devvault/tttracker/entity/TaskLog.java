package ru.devvault.tttracker.entity;

import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import javax.json.JsonObjectBuilder;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ttt_task_log", schema = "task_time_tracker")
@NamedQueries({
    @NamedQuery(name = "TaskLog.findByUser", query = "SELECT tl FROM TaskLog tl WHERE tl.user = :user AND tl.taskLogDate BETWEEN :startDate AND :endDate order by tl.taskLogDate ASC"),
    @NamedQuery(name = "TaskLog.findTaskLogCountByTask", query = "SELECT count(tl) FROM TaskLog tl WHERE tl.task = :task "),
    @NamedQuery(name = "TaskLog.findTaskLogCountByUser", query = "SELECT count(tl) FROM TaskLog tl WHERE tl.user = :user ")
})
public class TaskLog extends AbstractEntity implements EntityItem<Integer> {

    static final SimpleDateFormat DATE_FORMAT_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_task_log")
    private Integer idTaskLog;

    @NonNull
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2000)
    @Column(name = "task_description")
    private String taskDescription;

    @NonNull
    @Basic(optional = false)
    @NotNull
    @Column(name = "task_log_date")
    @Temporal(TemporalType.DATE)
    private Date taskLogDate;

    @NonNull
    @Basic(optional = false)
    @NotNull
    @Column(name = "task_minutes")
    private Integer taskMinutes;

    @JoinColumn(name = "username", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private User user;

    @JoinColumn(name = "id_task", referencedColumnName = "id_task")
    @ManyToOne(optional = false)
    private Task task;

    @Override
    public int hashCode() {

        int hash = 0;
        hash += (idTaskLog != null ? idTaskLog.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final TaskLog other = (TaskLog) obj;

        return Objects.equals(this.idTaskLog, other.idTaskLog);
    }

    @Override
    public String toString() {
        return "ru.devvault.tttracker.entity.TaskLog[ idTaskLog=" + idTaskLog + " ]";
    }

    @Override
    public Integer getId() {
        return idTaskLog;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {

        builder.add("idTaskLog", idTaskLog)
                .add("taskDescription", taskDescription)
                .add("taskLogDate", taskLogDate == null ? "" : DATE_FORMAT_yyyyMMdd.format(taskLogDate))
                .add("taskMinutes", taskMinutes);

        if (user != null) {
            user.addJson(builder);
        }

        if (task != null) {
            task.addJson(builder);            
        }
    }

}
