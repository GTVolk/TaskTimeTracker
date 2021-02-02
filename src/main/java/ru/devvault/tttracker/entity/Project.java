package ru.devvault.tttracker.entity;

import lombok.*;

import java.util.List;
import java.util.Objects;
import javax.json.JsonObjectBuilder;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ttt_project", schema = "task_time_tracker")
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p ORDER BY p.projectName"),
    @NamedQuery(name = "Project.findByIdProject", query = "SELECT p FROM Project p WHERE p.idProject = :idProject"),
    @NamedQuery(name = "Project.findByProjectName", query = "SELECT p FROM Project p WHERE p.projectName = :projectName")})
public class Project extends AbstractEntity implements EntityItem<Integer> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_project")
    private Integer idProject;

    @NonNull
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "project_name")
    private String projectName;

    @JoinColumn(name = "id_company", referencedColumnName = "id_company")
    @ManyToOne(optional = false)
    private Company company;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<Task> tasks;

    @Override
    public int hashCode() {

        int hash = 0;
        hash += (idProject != null ? idProject.hashCode() : 0);

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

        final Project other = (Project) obj;

        return Objects.equals(this.idProject, other.idProject);
    }

    @Override
    public String toString() {
        return "ru.devvault.tttracker.entity.Project[ idProject=" + idProject + " ]";
    }

    @Override
    public Integer getId() {
        return idProject;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        
        builder.add("idProject", idProject)
           .add("projectName", projectName);
                
        if (company != null){
           company.addJson(builder);
        }
    }   
 }
