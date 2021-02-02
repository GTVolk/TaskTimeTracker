package ru.devvault.tttracker.entity;

import lombok.*;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ttt_task", schema = "task_time_tracker")
@NamedQueries({
    @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t ORDER BY t.taskName"),
    @NamedQuery(name = "Task.findByIdTask", query = "SELECT t FROM Task t WHERE t.idTask = :idTask"),
    @NamedQuery(name = "Task.findByTaskName", query = "SELECT t FROM Task t WHERE t.taskName = :taskName")})
public class Task extends AbstractEntity implements EntityItem<Integer> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_task")
    private Integer idTask;

    @NonNull
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "task_name")
    private String taskName;

    @JoinColumn(name = "id_project", referencedColumnName = "id_project")
    @ManyToOne(optional = false)
    private Project project;

    @Override
    public int hashCode() {

        int hash = 0;
        hash += (idTask != null ? idTask.hashCode() : 0);

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

        final Task other = (Task) obj;

        return Objects.equals(this.idTask, other.idTask);
    }

    @Override
    public String toString() {
        return "ru.devvault.tttracker.entity.Task[ idTask=" + idTask + " ]";
    }    

    @Override
    public Integer getId() {
        return idTask;
    }
    
    @Override
    public void addJson(JsonObjectBuilder builder) {

        builder .add("idTask", idTask)
           .add("taskName", taskName);
         
        if (project != null){

           project.addJson(builder);
           
           Company company = project.getCompany();
           company.addJson(builder);
        }        
    }
}
